/**
 * @Title: MainActivity.java
 * @Package com.geekchic.wuyou
 * @Description: 欢迎界面
 * @author: evil
 * @date: Apr 29, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.geekchic.common.utils.PreferencesUtils;
import com.geekchic.constant.AppAction.LoginAction;
import com.geekchic.constant.AppAction.MainAction;
import com.geekchic.constant.AppAction.NavAction;
import com.geekchic.constant.AppActionCode.MainMessageCode;
import com.geekchic.constant.AppConstants;
import com.geekchic.framework.ui.BaseFrameActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: MainActivity
 * @Descritpion: 欢迎界面
 * @author evil
 * @date Apr 29, 2014
 */
public class WelcomeActivity extends BaseFrameActivity {
	private static final int WAIT_SECOND = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		bindPush(false);
		boolean nav = PreferencesUtils
				.getAttrBoolean(AppConstants.Common.NAV_HAS_SHOW);
		if (nav) {
			login();
		} else {
			sendEmptyMessageDelayed(MainMessageCode.MAIN_MESSAGE_NAV,WAIT_SECOND);
		}
	}

	private void login() {
		if (hasLogined()) {
			sendEmptyMessageDelayed(MainMessageCode.MAIN_MESSAGE_MAIN,WAIT_SECOND);
		} else {
			sendEmptyMessageDelayed(MainMessageCode.MAIN_MESSAGE_LOGIN,
					WAIT_SECOND);
		}
	}

	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		switch (msg.what) {
		case MainMessageCode.MAIN_MESSAGE_LOGIN:
			Intent loginIntent = new Intent(LoginAction.ACTION);
			startActivity(loginIntent);
			finishActivity();
			
			break;
		case MainMessageCode.MAIN_MESSAGE_NAV:
			Intent navIntent = new Intent(NavAction.ACTION);
			startActivity(navIntent);
			finishActivity();
			break;
		case MainMessageCode.MAIN_MESSAGE_MAIN:
			Intent mainIntent = new Intent(MainAction.ACTION);
			startActivity(mainIntent);
			finishActivity();
			break;
		default:
			break;
		}
	}

	@Override
	protected boolean needLogin() {
		// TODO Auto-generated method stub
		return false;
	}
}
