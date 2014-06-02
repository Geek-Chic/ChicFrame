/**
 * @Title: URLs.java
 * @Package com.geekchic.wuyou.bean
 * @Description: 接口URL集合
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

/**
 * @ClassName: URLs
 * @Descritpion: 接口URL集合
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
	public static final String LOCAL_HOST = "192.168.1.104/wuyou";
	public static final String VPS_HOST="evilester.hk.7encc.com/wuyou";
	public static final String HOST=LOCAL_HOST;
	/**
	 * 登录验证
	 */
	public final static String LOGIN_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "user/login/ApiLogin";
	public final static String LOGOUT=HTTP+HOST+URL_SPLITTER+"/usr/logout/ApiLogout";
	/**
	 * 注册
	 */
	public final static String REGISTER_DECLARATION=HTTP+HOST+URL_SPLITTER+"declaration.html";
	public final static String REGISTER_AUTH_CODE=HTTP+HOST+URL_SPLITTER+"user/registration/ApiGetCaptcha";
	public final static String REGISTER_REGISTERATION=HTTP+HOST+URL_SPLITTER+"user/registration/ApiRegistration";
	
	public final static String CONTACT_SYNC=HTTP+HOST+URL_SPLITTER+"contact/sync/ApiSync";
	/**
	 * 个人资料
	 */
	public final static String PROFILE_UPAVATOR_UPL=HTTP+HOST+URL_SPLITTER+"user/profile/apiavator";
	public final static String PROFILE_SET_AVATOR=HTTP+HOST+URL_SPLITTER+"user/profile/apiupdateavator";
	/**
	 * 反馈
	 */
	public final static String FEEDBACK=HTTP+HOST+URL_SPLITTER+"user/feedback/ApiFeedback";
	/**
	 * 图片头像
	 */
	public final static String AICON=HTTP+HOST+URL_SPLITTER+"uploads/avators/";
	/**
	 * 项目
	 */
	public final static String PROJECT_CREATE=HTTP+HOST+URL_SPLITTER+"";
	/**
	 * .Net接口
	 */
	public final static String NET_ACCESS_URL="http://192.168.56.118:9001/WebServiceForAndroid.asmx?wsdl";
	/**
	 * Test接口
	 */
	public final static String TEST_LOGIN_STRING="http://192.168.1.105/basic_developer/user/login/Llogin";
}
