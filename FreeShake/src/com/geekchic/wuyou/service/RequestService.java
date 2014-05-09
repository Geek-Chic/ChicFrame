/**
 * @Title: RequestService.java
 * @Package com.geekchic.wuyou.service
 * @Description: 网络请求服务
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.service;

import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.PreferencesUtil;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.Common;
import com.geekchic.constant.AppConstants.REQUESTCODE;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.HttpConnector;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.framework.service.core.BaseRequestService;
import com.geekchic.framework.service.core.Operation;
import com.geekchic.wuyou.bean.URLs;
import com.geekchic.wuyou.service.operation.ContactOperation;
import com.geekchic.wuyou.service.operation.DecodeOperation;

/**
 * @ClassName: RequestService
 * @Descritpion: 网络请求服务
 * @author evil
 * @date May 2, 2014
 */
public class RequestService extends BaseRequestService {

	@Override
	public Operation getOperationForType(int requestType) {
		Operation operation = null;
		switch (requestType) {
		case SERVICEWORK.WORKER_LOGIN:
			// operation=new LoginOperation();
			operation = new LoginOperation();
			break;
		case SERVICEWORK.WORKER_REGISTER:
			operation = new NetLoginOperation();
			break;
		case SERVICEWORK.WORKER_CONTACTS_FROM_PROVIDER:
			operation=new ContactOperation();
			break;
		default:
			break;
		}
		return operation;
	}

	public class LoginOperation extends BaseOperation {
		@Override
		public Bundle execute(Context context, Request request)
				throws ConnectionException, DataException,
				CustomRequestException {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("phone", request.getString(REQUESTCODE.REQUEST_PHONE));
			params.put("password",
					request.getString(REQUESTCODE.REQUEST_PASSWORD));

			HttpRequestBean httpRequestBean = new HttpRequestBean(context,
					URLs.LOGIN_VALIDATE_HTTP);
			httpRequestBean.setMethod(Method.POST);
			httpRequestBean.setParameters(params);
			ConnectionResult result = HttpConnector.execute(httpRequestBean);
			Bundle bundle = new Bundle();
			JSONObject json = JSON.parseObject(result.body);
			int code = json.getIntValue("code");
			if (0 == code) {
				String sessionid = json.getString("sessionid");
				PreferencesUtil.setAttr(Common.KEY_SESSION_ID, sessionid);
				AppConfig.getInstance().setSessionId(sessionid);
				bundle.putInt("code", 0);
				bundle.putString(REQUESTCODE.REQUEST_RESULT, "登录成功");
			} else {
				bundle.putInt("code", -1);
				bundle.putString(REQUESTCODE.REQUEST_RESULT, "登录失败");
			}
			return bundle;
		}

	}

	public class NetLoginOperation extends BaseOperation {
		@Override
		public Bundle execute(Context context, Request request)
				throws ConnectionException, DataException,
				CustomRequestException {
			String s = "";
			String serviceUrl = URLs.NET_ACCESS_URL;
			String methodName = "HelloWorld";
			SoapObject re = new SoapObject("http://tempuri.org/", methodName);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(re);
			HttpTransportSE ht = new HttpTransportSE(serviceUrl);
			try {
				ht.call(null, envelope);

				if (envelope.getResponse() != null) {
					// SoapObject soapObject = (SoapObject)
					// envelope.getResponse();
					Object object = (Object) envelope.getResponse();
					s = object.toString();
					Logger.d("evil", s);
				} else {
					s = "error";
				}
			} catch (Exception e) {

				s = e.toString();

			}
			Bundle bundle = new Bundle();
			bundle.putString("result", s);
			return bundle;
		}
	}

}
