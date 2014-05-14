/**
 * @Title: RegistrationLogic.java
 * @Package com.geekchic.wuyou.logic.registration
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 12, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.registration;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.wuyou.logic.RequestManager;

/**
 * @ClassName: RegistrationLogic
 * @Descritpion: 注册Logic
 * @author evil
 * @date May 12, 2014
 */
public class RegistrationLogic extends BaseLogic implements IRegistrationLogic {
	/**
	 * 上下文 
	 */
   private Context mContext;
   /**
    * RegistrationLogic构造函数
    * @param context
    */
   public RegistrationLogic(Context context){
	   this.mContext=context;
   }
	@Override
	public void getCaptcha(String phone) {
		Request request=new Request(SERVICEWORK.WORKDER_REQUEST_CHAPTCHA);
		request.put("phone",phone);
        RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				sendMessage(AppActionCode.RegistrationCode.AUTH_REQUEST_SUCCESS,resultData);
			}
			
			@Override
			public void onRequestDataError(Request request) {
               				
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
	@Override
	public void register(String truename, String phone, String password,String pushid,String pushchannelid) {
		Request request=new Request(SERVICEWORK.WORKER_REGISTER);
		request.put("truename", truename);
		request.put("password", password);
		request.put("phone", phone);
		request.put("pushid", pushid);
		request.put("channelid", pushchannelid);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
               	AppConfig.getInstance().setSessionId(request.getString("sessionid"));
               	AppConfig.getInstance().setUid(request.getString("uuid"));
               	sendEmptyMessage(AppActionCode.RegistrationCode.REGISTER_SUCCESS);
			}
			
			@Override
			public void onRequestDataError(Request request) {
				sendEmptyMessage(AppActionCode.RegistrationCode.REGISETER_FAILED);
			}
			
			@Override
			public void onRequestCustomError(Request request, Bundle resultData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRequestConnectionError(Request request, int statusCode) {
				// TODO Auto-generated method stub
				sendEmptyMessage(AppActionCode.BaseMessageCode.HTTP_ERROR);
			}
		});
	}


}
