/**
 * @Title: LoginLogic.java
 * @Package com.geekchic.wuyou.logic.login
 * @Description: 登录Logic
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.login;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.common.log.Logger;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConstants.RequestCode;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.wuyou.bean.UserInfo;
import com.geekchic.wuyou.bean.UserInfo.UserField;
import com.geekchic.wuyou.logic.RequestManager;
import com.geekchic.wuyou.service.RequestService;

/**
 * @ClassName: LoginLogic
 * @Descritpion: 登录Logic
 * @author evil
 * @date Apr 30, 2014
 */
public class LoginLogic extends BaseLogic implements ILoginLogic {
	private static final String TAG = "LoginLogic";

	private Context mContext;

	public LoginLogic(Context context) {
		this.mContext = context;
	}

	@Override
	public void login(String username, String password) {
		Request request = new Request(SERVICEWORK.WORKER_LOGIN);
		request.put("username", username);
		request.put("password", password);
		RequestManager.getInstance(mContext).execute(request,
				new RequestListener() {

					@Override
					public void onRequestFinished(Request request,
							Bundle resultData) {
						Logger.d(TAG, resultData.getString(RequestCode.REQUEST_RESULT));
						sendMessage(
								AppActionCode.LoginCode.MESSAGE_LOGIN_SUCCESS,
								resultData);
					}

					@Override
					public void onRequestDataError(Request request) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onRequestCustomError(Request request,
							Bundle resultData) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onRequestConnectionError(Request request,
							int statusCode) {
						sendEmptyMessage(AppActionCode.BaseMessageCode.HTTP_ERROR);
					}
				});
	}

	@Override
	public void logout() {
		Request request=new Request(SERVICEWORK.WORKER_LOGOUT);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				// TODO Auto-generated method stub
				Logger.d(TAG, "结束");
			}
			
			@Override
			public void onRequestDataError(Request request) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRequestCustomError(Request request, Bundle resultData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRequestConnectionError(Request request, int statusCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
