/**
 * @Title: AvatorOperation.java
 * @Package com.geekchic.operation
 * @Description: 上传头像
 * @author: evil
 * @date: May 31, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.constant.AppConstants.ReturnCode;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.HttpUpload;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.URLs;

/**
 * @ClassName: AvatorOperation
 * @Descritpion: 上传头像
 * @author evil
 * @date May 31, 2014
 */
public class AvatorOperation extends BaseOperation{

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		String avatorPath=request.getString("avators");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("avators", avatorPath);
		HttpRequestBean httpRequestBean=new HttpRequestBean(context, URLs.PROFILE_UPAVATOR_UPL);
		httpRequestBean.setParameters(params);
		httpRequestBean.setMethod(Method.POST);
		ConnectionResult result=HttpUpload.upLoadFile(httpRequestBean);
		Bundle bundle=new Bundle();
		JSONObject json = JSON.parseObject(result.body);
		int code = json.getIntValue("code");
		if (code==ReturnCode.CODE_SUCCESS) {
			String avatorid=json.getString("avatorid");
			bundle.putString("avatorid", avatorid);
		}
		return bundle;
		
	}

}
