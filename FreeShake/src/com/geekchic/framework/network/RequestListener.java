/**
 * @Title: RequestListener.java
 * @Package com.geekchic.framework.network
 * @Description: 网络请求监听器
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.network;

import android.os.Bundle;

import com.geekchic.framework.bean.Request;

/**
 * @ClassName: RequestListener
 * @Descritpion: 网络请求监听器
 * @author evil
 * @date May 2, 2014
 */
public interface RequestListener {

	/**
	 * 请求成功
	 * 
	 * @param request
	 * @param resultData
	 */
	public void onRequestFinished(Request request, Bundle resultData);

	/**
	 * 连接错误
	 * 
	 * @param request
	 * @param statusCode
	 */
	public void onRequestConnectionError(Request request, int statusCode);

	/**
	 * 数据错误
	 * 
	 * @param request
	 */
	public void onRequestDataError(Request request);

	/**
	 * 自定义错误
	 * 
	 * @param request
	 * @param resultData
	 */
	public void onRequestCustomError(Request request, Bundle resultData);

}
