/**
 * @Title: ProfileLogic.java
 * @Package com.geekchic.wuyou.logic.profile
 * @Description: 个人资料设置Logic
 * @author: evil
 * @date: May 31, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.profile;

import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;

import com.geekchic.common.utils.PreferencesUtils;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConstants.Common;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.wuyou.logic.RequestManager;

/**
 * @ClassName: ProfileLogic
 * @Descritpion: 个人资料设置Logic
 * @author evil
 * @date May 31, 2014
 */
public class ProfileLogic extends BaseLogic implements IProfileLogic {
	private Context mContext;

	public ProfileLogic(Context context) {
		this.mContext = context;
	}

	@Override
	public void updateAvator(String path) {
         Request request=new Request(SERVICEWORK.PROFILE_UPAVATOR);
         request.put("avators",path);
         RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				String avatorid=resultData.getString("avatorid");
				sendMessage(AppActionCode.ProfileCode.PROFILE_AVATOR_SUCCESS, avatorid);
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

	@Override
	public void appUpdate() {
		Request request=new Request(SERVICEWORK.PROFILE_UPDATE);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				sendMessage(AppActionCode.ProfileCode.PROFILE_UPDATE_SUCCESS,null);
			}
			
			@Override
			public void onRequestDataError(Request request) {
				sendEmptyMessage(AppActionCode.ProfileCode.PROFILE_UPDATE_FAILED);
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
	public void setAvator(String avatorId) {
		Request request=new Request(SERVICEWORK.PROFILE_SET_AVATORID);
		request.put("uuid", PreferencesUtils.getAttrString(Common.KEY_USER_ID));
		request.put("avatorid", avatorId);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				sendEmptyMessage(AppActionCode.ProfileCode.PROFILE_AVATORID_SUCCESS);
			}
			
			@Override
			public void onRequestDataError(Request request) {
				sendEmptyMessage(AppActionCode.ProfileCode.PROFILE_AVATORID_FAILED);				
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
