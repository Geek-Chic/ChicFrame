/**
 * @Title: Parser.java
 * @Package com.geekchic.framework.service.core
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service.core;

import java.util.List;

/**
 * @ClassName: Parser
 * @Descritpion: 解析接口
 * @author evil
 * @date May 2, 2014
 */
public interface Parser {
	/**
	 * 转化Json为JavaBean
	 * @param json
	 */
   public Object parserJsonToBean(String json,Class<?> cls);
   /**
    * 转化Json为List-String
    * @param json
    */
   public Object parserJsonToListString(String json);
   /**
    * 转化Json为List-JavaBean
    * @param json
    */
   public Object parserJsonToListBean(String json,Class<?> cls);
   /**
    * 转化JavaBean为Json
    */
   public String packBeanToJson(Object obj);
   /**
    * 转化List-JavaBean为Json
    */
   public String packListBeanToJson(List<?> list); 
   /**
    * 转化List-String为Json
    * @param list
    */
   public String packListStringToJson(List<String> list);

	   
}
