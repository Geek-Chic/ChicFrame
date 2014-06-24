/**
 * @Title: HttpBean.java
 * @Package com.geekchic.framework.bean
 * @Description: 网络连接实体类
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.geekchic.common.log.Logger;

/**
 * @ClassName: HttpBean
 * @Descritpion: 网络连接实体类
 * @author evil
 * @date May 2, 2014
 */
public class HttpRequestBean {
	/**
	 * TAG
	 */
	private static final String TAG = "HttpBean";

	/**
	 * @ClassName: Method
	 * @Descritpion: HTTP Method
	 * @author evil
	 * @date May 2, 2014
	 */
	public static enum Method {
		GET, POST, PUT, DELETE
	}

	/**
	 * 上下文
	 */
	private final Context mContext;
	/**
	 * 连接URL
	 */
	private final String mUrl;
	/**
	 * HTTP 方法
	 */
	private Method mMethod = Method.GET;
	/**
	 * POST 参数
	 */
	private ArrayList<BasicNameValuePair> mParameterList = null;
	/**
	 * HTTP Header 参数
	 */
	private HashMap<String, String> mHeaderMap = null;
	/**
	 * 是否Gzip压缩
	 */
	private boolean mIsGzipEnabled = true;
	/**
	 * 用户代理-保存操作系统及个人偏好
	 */
	private String mUserAgent = null;
	/**
	 * POST数据
	 */
	private String mPostText = null;
	/**
	 * 用户验证
	 */
	private UsernamePasswordCredentials mCredentials = null;
	/**
	 * 是否支持SSL
	 */
	private boolean mIsSslValidationEnabled = true;
	/**
	 * 服务期端最后被修改的时间
	 */
	   private String lastModified;
	   /**
	    *  ETag是一个可以与Web资源关联的记号（token）,Etag主要在断点下载时比较有用。
	    */
	   private String etag;
	   /**
	    * 本地数据
	    */
	   private String localData;

	/**
	 * HttpBean构造函数
	 * 
	 * @param context
	 * @param url
	 */
	public HttpRequestBean(Context context, String url) {
		if (url == null) {
			Logger.d(TAG, "HttpBean-request URL is null");
			throw new NullPointerException("request URL is null");
		}
		this.mContext = context;
		this.mUrl = url;
	}

	/**
	 * 设置HTTP 方法
	 * 
	 * @param method
	 * @return
	 */
	public void setMethod(Method method) {
		mMethod = method;
		if (method != Method.POST) {
			mPostText = null;
		}
	}

	/**
	 * 设置POST参数
	 * 
	 * @param parameterMap
	 * @return
	 */
	public void setParameters(HashMap<String, String> parameterMap) {
		ArrayList<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			parameterList.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		setParameters(parameterList);
	}

	/**
	 * 设置POST参数,有ParameterList就不能有PostText
	 * 
	 * @param parameterList
	 * @return
	 */
	public void setParameters(ArrayList<BasicNameValuePair> parameterList) {
		mParameterList = parameterList;
		mPostText = null;
	}

	/**
	 * 设置用户代理
	 * 
	 * @param userAgent
	 * @return
	 */
	public void setUserAgent(String userAgent) {
		mUserAgent = userAgent;
	}

	/**
	 * 设置POST参数,有PostText就不能有ParameterList
	 * 
	 * @param postText
	 * @param method
	 * @param parameterMap
	 */
	public void setPostText(String postText, Method method) {
		if (method != Method.POST && method != Method.PUT) {
			throw new IllegalArgumentException("Method must be POST or PUT");
		}
		mPostText = postText;
		mMethod = method;
		mParameterList = null;
	}

	/**
	 * 获取HTTP 方法
	 * 
	 * @return
	 */
	public Method getMethod() {
		return mMethod;
	}

	/**
	 * 获取POST参数
	 * 
	 * @return
	 */
	public ArrayList<BasicNameValuePair> getParameterList() {
		return mParameterList;
	}

	/**
	 * 获取HTTP Header
	 * 
	 * @return
	 */
	public HashMap<String, String> getHeaderMap() {
		return mHeaderMap;
	}

	/**
	 * 设置HTTP Header
	 * 
	 * @param mHeaderMap
	 */
	public void setHeaderMap(HashMap<String, String> mHeaderMap) {
		this.mHeaderMap = mHeaderMap;
	}

	/**
	 * 是否压缩
	 * 
	 * @return
	 */
	public boolean isGzipEnabled() {
		return mIsGzipEnabled;
	}

	/**
	 * 设置压缩
	 * 
	 * @param mIsGzipEnabled
	 */
	public void setIsGzipEnabled(boolean mIsGzipEnabled) {
		this.mIsGzipEnabled = mIsGzipEnabled;
	}

	/**
	 * 获取用户文本
	 * 
	 * @return
	 */
	public String getUserAgent() {
		return mUserAgent;
	}

	/**
	 * 获取Post文本
	 * 
	 * @return
	 */
	public String getPostText() {
		return mPostText;
	}

	/**
	 * 是否压缩
	 * 
	 * @return
	 */
	public boolean isSslValidationEnabled() {
		return mIsSslValidationEnabled;
	}
    
	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getLocalData() {
		return localData;
	}

	public void setLocalData(String localData) {
		this.localData = localData;
	}

	/**
	 * 设置压缩
	 * 
	 * @param mIsSslValidationEnabled
	 */
	public void setIsSslValidationEnabled(boolean mIsSslValidationEnabled) {
		this.mIsSslValidationEnabled = mIsSslValidationEnabled;
	}

	/**
	 * 获取URL
	 * 
	 * @return
	 */
	public String getUrl() {
		return mUrl;
	}

	public Context getContext() {
		return mContext;
	}

	public UsernamePasswordCredentials getCredentials() {
		return mCredentials;
	}

	public void setCredentials(UsernamePasswordCredentials mCredentials) {
		this.mCredentials = mCredentials;
	}

}