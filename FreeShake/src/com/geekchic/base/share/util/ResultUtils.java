/**
 * @Title: ResultUtils.java
 * @Package com.geekchic.base.share.util
 * @Description: 结果解析工具类
 * @author: evil
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @ClassName: ResultUtils
 * @Descritpion: 结果解析工具类
 * @author evil
 * @date 2014-7-2
 */
public class ResultUtils
{
   public HashMap decodeRes(String res){
       try
    {
        if(res.startsWith("[")&& res.endsWith("]")){
            res=(new StringBuilder()).append("{\"fakelist\":").append(res).append("}").toString();
        }
        JSONObject jsonObject=new JSONObject(res);
        return decodeJson(jsonObject);
    }
    catch (Exception e)
    {
    }
       return new HashMap();
   }
   public HashMap decodeJson(JSONObject jsonObject){
       HashMap hashMap=new HashMap();
       Iterator iterator =jsonObject.keys();
       do
       {
           if(!iterator.hasNext())
               break;
           String s = (String)iterator.next();
           Object obj = jsonObject.opt(s);
           if(JSONObject.NULL.equals(obj))
               obj = null;
           if(obj != null)
           {
               if(obj instanceof JSONObject)
                   obj = decodeJson((JSONObject)obj);
               else
               if(obj instanceof JSONArray)
                   obj = decodeJsonArray((JSONArray)obj);
               hashMap.put(s, obj);
           }
       } while(true);
       return hashMap;
   }
   private ArrayList decodeJsonArray(JSONArray jsonArray){

       ArrayList arraylist = new ArrayList();
       int i = 0;
       for(int j = jsonArray.length(); i < j; i++)
       {
           Object obj = jsonArray.opt(i);
           if(obj instanceof JSONObject)
               obj = decodeJson((JSONObject)obj);
           else
           if(obj instanceof JSONArray)
               obj = decodeJsonArray((JSONArray)obj);
           arraylist.add(obj);
       }

       return arraylist;
   
   }
}
