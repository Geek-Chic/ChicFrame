/**
 * @Title: NetJsonParser.java
 * @Package com.geekchic.base.share
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-4-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName: NetJsonParser
 * @Descritpion: [用一句话描述作用] 
 * @author Administrator
 * @date 2014-4-14
 */
public class NetJsonParser
{
    public NetJsonParser()
    {
    }
    
    public HashMap parserJson(String json)
    {
        try
        {
            if (json.startsWith("[") && json.endsWith("]"))
            {
                json = (new StringBuilder()).append("{\"fakelist\":")
                        .append(json)
                        .append("}")
                        .toString();
            }
            JSONObject jsonObject = new JSONObject(json);
            return parserJson(jsonObject);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return new HashMap();
    }
    
    private HashMap parserJson(JSONObject jsonObject)
    {
        HashMap hashmap = new HashMap();
        Iterator iterator = jsonObject.keys();
        do
        {
            if (!iterator.hasNext())
                break;
            String s = (String) iterator.next();
            Object obj = jsonObject.opt(s);
            if (JSONObject.NULL.equals(obj))
                obj = null;
            if (obj != null)
            {
                if (obj instanceof JSONObject)
                    obj = parserJson((JSONObject) obj);
                else if (obj instanceof JSONArray)
                    obj = parserJson((JSONArray) obj);
                hashmap.put(s, obj);
            }
        } while (true);
        return hashmap;
    }
    
    public ArrayList parserJson(JSONArray jsonArray)
    {
        ArrayList arraylist = new ArrayList();
        int i = 0;
        for (int j = jsonArray.length(); i < j; i++)
        {
            Object obj = jsonArray.opt(i);
            if (obj instanceof JSONObject)
                obj = parserJson((JSONObject) obj);
            else if (obj instanceof JSONArray)
                obj = parserJson((JSONArray) obj);
            arraylist.add(obj);
        }
        
        return arraylist;
    }
    
    public String packJsonStr(HashMap hashMap)
    {
        try
        {
            return packJson(hashMap).toString();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        return "";
    }
    
    private JSONObject packJson(HashMap hashMap)
    {
        
        JSONObject jsonobject = new JSONObject();
        java.util.Map.Entry entry;
        Object obj;
        try
        {
            for (Iterator iterator = hashMap.entrySet().iterator(); iterator.hasNext(); jsonobject.put((String) entry.getKey(),
                    obj))
            {
                entry = (java.util.Map.Entry) iterator.next();
                obj = entry.getValue();
                if (obj instanceof HashMap)
                {
                    obj = packJson((HashMap) obj);
                    continue;
                }
                if (obj instanceof ArrayList)
                    obj = packJson((ArrayList) obj);
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return jsonobject;
    }
    
    private JSONArray packJson(ArrayList arrayList)
    {
        
        JSONArray jsonarray = new JSONArray();
        Object obj;
        for (Iterator iterator = arrayList.iterator(); iterator.hasNext(); jsonarray.put(obj))
        {
            obj = iterator.next();
            if (obj instanceof HashMap)
            {
                obj = packJson((HashMap) obj);
                continue;
            }
            if (obj instanceof ArrayList)
                obj = packJson((ArrayList) obj);
        }
        
        return jsonarray;
        
    }
}
