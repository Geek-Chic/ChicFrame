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

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.PreferencesUtil;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.Common;
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
import com.geekchic.wuyou.bean.UserInfo;
import com.geekchic.wuyou.bean.UserInfo.UserField;

/**
 * @ClassName: RequestService
 * @Descritpion: 网络请求服务
 * @author evil
 * @date May 2, 2014
 */
public class RequestService extends BaseRequestService{
   public static final int LOGIN=1;
	@Override
	public Operation getOperationForType(int requestType) {
		Operation operation=null;
		switch (requestType) {
		case LOGIN:
			operation=new LoginOperation();
          break;
		default:
			break;
		}
		return operation;
	}
	public class LoginOperation extends BaseOperation{
		@Override
		public Bundle execute(Context context, Request request)
				throws ConnectionException, DataException,
				CustomRequestException {
			   HashMap<String, String> params = new HashMap<String, String>();
			   params.put("phone",request.getString(UserField.TYPE_PHONE));
			   params.put("password", request.getString(UserField.TYPE_PASSWORD));
			   
			HttpRequestBean httpRequestBean=new HttpRequestBean(context, URLs.LOGIN_VALIDATE_HTTP);
			httpRequestBean.setMethod(Method.POST);
			httpRequestBean.setParameters(params);
			ConnectionResult result=HttpConnector.execute(httpRequestBean);
			Bundle bundle=new Bundle();
			JSONObject json=JSON.parseObject(result.body);
			int code=json.getIntValue("code");
			if(0==code){
              String sessionid=json.getString("sessionid");
              PreferencesUtil.setAttr(Common.KEY_SESSION_ID, sessionid);
              AppConfig.getInstance().setSessionId(sessionid);
              bundle.putInt("code", 0);
              bundle.putString("result", "登录成功");
			}else {
				bundle.putInt("code", -1);
			  bundle.putString("result", "登录失败");
			}
			return bundle;
		}
		
	}

}
