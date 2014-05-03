/**
 * @Title: BaseOperation.java
 * @Package com.geekchic.framework.service.core
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service.core;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @ClassName: BaseOperation
 * @Descritpion: 网络操作基类
 * @author evil
 * @date May 2, 2014
 */
public abstract class BaseOperation implements Parser,Operation {
   
	@Override
	public Object parserJsonToBean(String json, Class<?> cls) {
		return JSON.parseObject(json,cls);
	}

	@Override
	public Object parserJsonToListString(String json) {
		return JSON.parseObject(json,TypeReference.LIST_STRING); 
	}

	@Override
	public Object parserJsonToListBean(String json,Class<?> cls) {
		return JSON.parseArray(json, cls);
	}

	@Override
	public String packBeanToJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public String packListBeanToJson(List<?> list) {
		return JSON.toJSONString(list);
	}

	@Override
	public String packListStringToJson(List<String> list) {
		return JSON.toJSONString(list);
	}
    
    
}
