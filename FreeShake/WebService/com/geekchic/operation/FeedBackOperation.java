/**
 * @Title: FeedBackOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: 反馈Operation
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.constant.AppConstants.RequestCode;
import com.geekchic.constant.AppConstants.ReturnCode;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.URLs;

/**
 * @ClassName: FeedBackOperation
 * @Descritpion: 反馈Operation
 * @author evil
 * @date May 17, 2014
 */
public class FeedBackOperation extends BaseOperation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		String uuid=request.getString("uuid");
		String message=request.getString("message");
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("uuid", uuid);
		params.put("message", message);
		ConnectionResult result=handleHttp(context, Method.POST, URLs.FEEDBACK, params);
		Bundle bundle = new Bundle();
		JSONObject json = JSON.parseObject(result.body);
		int code = json.getIntValue("code");
		if (ReturnCode.CODE_SUCCESS == code) {
			bundle.putInt(RequestCode.REQUEST_CODE, 0);
		} else {
			throw new DataException();
		}
		return bundle;
	}

}
