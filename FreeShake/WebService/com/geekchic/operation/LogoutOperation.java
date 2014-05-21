/**
 * @Title: LogoutOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 14, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.network.HttpConnector;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.URLs;

/**
 * @ClassName: LogoutOperation
 * @Descritpion: 登出
 * @author evil
 * @date May 14, 2014
 */
public class LogoutOperation extends BaseOperation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		HttpRequestBean httpRequestBean = new HttpRequestBean(context,
				URLs.LOGIN_VALIDATE_HTTP);
		httpRequestBean.setMethod(Method.GET);
		ConnectionResult result = HttpConnector.execute(httpRequestBean);
		JSONObject jsonObject=JSON.parseObject(result.body);
		Bundle bundle=new Bundle();
		int code = jsonObject.getIntValue("code");
		if (0 == code) {
		bundle.putInt("code", 0);
		}else {
			handelReturnCode(code);
		}
		return bundle;
	}

}
