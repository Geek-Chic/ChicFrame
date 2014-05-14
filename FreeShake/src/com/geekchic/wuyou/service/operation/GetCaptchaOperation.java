/**
 * @Title: GetCaptchaOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 12, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.service.operation;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.constant.AppConfig;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.HttpConnector;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.URLs;

/**
 * @ClassName: GetCaptchaOperation
 * @Descritpion: 获取验证码
 * @author evil
 * @date May 12, 2014
 */
public class GetCaptchaOperation extends BaseOperation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("phone", request.getString("phone"));
		params.put("pushid", AppConfig.getInstance().getPushUid());
		HttpRequestBean httpRequestBean = new HttpRequestBean(context,
				URLs.REGISTER_AUTH_CODE);
		httpRequestBean.setMethod(Method.POST);
		httpRequestBean.setParameters(params);
		ConnectionResult result = HttpConnector.execute(httpRequestBean);
		Bundle bundle = new Bundle();
		JSONObject json = JSON.parseObject(result.body);
		int code = json.getIntValue("code");
		if (0 == code) {
			String captcha = json.getString("captcha");
			bundle.putInt("code", 0);
			bundle.putString("captcha", captcha);
		} else {
			bundle.putInt("code", -1);
		}
		return bundle;
	}

}
