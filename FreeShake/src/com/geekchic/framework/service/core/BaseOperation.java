/**
 * @Title: BaseOperation.java
 * @Package com.geekchic.framework.service.core
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service.core;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.geekchic.constant.AppConstants;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.network.HttpConnector;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.wuyou.bean.URLs;

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
	/**
	 *  执行HTTP操作
	 * @param context
	 * @param method
	 * @param url
	 * @param params
	 * @return
	 * @throws ConnectionException
	 */
	protected ConnectionResult handleHttp(Context context,Method method,String url,HashMap<String, String> params) throws ConnectionException{
		HttpRequestBean httpRequestBean = new HttpRequestBean(context,
				 url);
		httpRequestBean.setMethod(method);
		httpRequestBean.setParameters(params);
		ConnectionResult result = HttpConnector.execute(httpRequestBean);
		return result;
	}
	/**
	 * 普通错误码处理
	 * @param returnCode
	 * @throws DataException
	 */
    protected void handelReturnCode(int returnCode) throws DataException{
    	String errorMessage="";
       switch (returnCode) {
	case AppConstants.ReturnCode.CODE_DB_ERROR:
		errorMessage="Server_Db_Error";
		break;
	case AppConstants.ReturnCode.CODE_DATA_ERROR:
		errorMessage="Request_Data_Error";
		break;
	case AppConstants.ReturnCode.CODE_USER_ERROR:
		errorMessage="User_Invalidate";
	case AppConstants.ReturnCode.CODE_UNKNOW_ERROR:
		errorMessage="Unknow error";
		break;
	default:
		break;
	}
       throw new DataException(errorMessage);
    }
    
}
