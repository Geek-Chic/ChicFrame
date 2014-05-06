/**
 * @Title: URLs.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

/**
 * @ClassName: URLs
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date May 2, 2014
 */
public final class URLs {
	private URLs() {
    }
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	private final static String URL_SPLITTER = "/";
	private final static String URL_UNDERLINE = "_";
	/**
	 * 主机位置
	 */
	public static final String LOCAL_HOST = "192.168.1.105/wuyou";
	public static final String VPS_HOST="evilester.hk.7encc.com/wuyou";
	public static final String HOST=VPS_HOST;
	/**
	 * 登录验证
	 */
	public final static String REGISTER_DECLARATION=HTTP+HOST+URL_SPLITTER+"declaration.html";
	public final static String LOGIN_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "site/remotelogin";
}
