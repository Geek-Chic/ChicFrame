/**
 * @Title: RequestService.java
 * @Package com.geekchic.wuyou.service
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.service;

import org.apache.http.HttpConnection;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.base.download.client.HttpRequest;
import com.geekchic.framework.bean.HttpRequestBean;
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
import com.geekchic.wuyou.logic.RequestManager;

/**
 * @ClassName: RequestService
 * @Descritpion: [用一句话描述作用] 
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
			HttpRequestBean httpRequestBean=new HttpRequestBean(context, URLs.LOGIN_VALIDATE_HTTP);
			ConnectionResult result=HttpConnector.execute(httpRequestBean);
			Bundle bundle=new Bundle();
			bundle.putString("result", result.body);
			return bundle;
		}
		
	}

}
