/**
 * @Title: AbstractParser.java
 * @Package com.geekchic.base.update
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 13, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.update;

/**
 * @ClassName: AbstractParser
 * @Descritpion: 解析基类
 * @author jp
 * @date Apr 13, 2014
 */
public interface  AbstractParser {
	public final static String TAG_UPDATE_INFO = "updateInfo";
	public final static String TAG_APP_NAME = "appName";
	public final static String TAG_APP_DESCRIPTION = "appDescription";
	public final static String TAG_PACKAGE_NAME = "packageName";
	public final static String TAG_VERSION_CODE = "versionCode";
	public final static String TAG_VERSION_NAME = "versionName";
	public final static String TAG_FORCE_UPDATE = "forceUpdate";
	public final static String TAG_AUTO_UPDATE = "autoUpdate";
	public final static String TAG_APK_URL = "apkUrl";
	public final static String TAG_UPDATE_TIPS = "updateTips";

	public  UpdateInfo parse(String content);
}
