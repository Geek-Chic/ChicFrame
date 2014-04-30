/**
 * @Title: JsonUtils.java
 * @Package com.geekchic.common.utils
 * @Description: Json工具类
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: JsonUtils
 * @Descritpion: Json工具类
 * @author evil
 * @date Apr 30, 2014
 */
public class JsonUtils {
	/**
	 * 将对象转为String
	 * @param from
	 * @return
	 * @throws JSONException
	 */
	public static String jsonObjectToString(Object from) throws JSONException
    {
        if (from == null)
        {
            return null;
        }
        
        if (from instanceof String)
        {
            return (String) from;
        }
        
        if (from instanceof Number || from instanceof JSONObject)
        {
            return from.toString();
        }
        
        if (from instanceof JSONArray)
        {
            JSONArray arr = (JSONArray) from;
            if (arr.size() < 1)
            {
                return null;
            }
            return jsonObjectToString(arr.get(0));
        }
        return null;
    }

}
