/**
 * @Title: JsonUtils.java
 * @Package com.geekchic.common.utils
 * @Description: Json工具类
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @ClassName: JsonUtils
 * @Descritpion: Json工具类
 * @author evil
 * @date Apr 30, 2014
 */
public class JsonUtils {
	/**
	 * 将对象转为String
	 * 
	 * @param from
	 * @return
	 * @throws JSONException
	 */
	public static String jsonObjectToString(Object from) throws JSONException {
		if (from == null) {
			return null;
		}

		if (from instanceof String) {
			return (String) from;
		}

		if (from instanceof Number || from instanceof JSONObject) {
			return from.toString();
		}

		if (from instanceof JSONArray) {
			JSONArray arr = (JSONArray) from;
			if (arr.size() < 1) {
				return null;
			}
			return jsonObjectToString(arr.get(0));
		}
		return null;
	}

	public static Object parserJsonToBean(String json, Class<?> cls) {
		return JSON.parseObject(json, cls);
	}

	public static Object parserJsonToListString(String json) {
		return JSON.parseObject(json, TypeReference.LIST_STRING);
	}

	public static Object parserJsonToListBean(String json, Class<?> cls) {
		return JSON.parseArray(json, cls);
	}

	public static String packBeanToJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static String packListBeanToJson(List<?> list) {
		return JSON.toJSONString(list);
	}

	public static String packListStringToJson(List<String> list) {
		return JSON.toJSONString(list);
	}

}
