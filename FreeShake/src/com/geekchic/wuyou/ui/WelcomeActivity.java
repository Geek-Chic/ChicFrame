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

import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppAction.LoginAction;
import com.geekchic.constant.AppActionCode.MainMessageCode;
import com.geekchic.framework.ui.BaseActivity;
import com.geekchic.framework.ui.BaseFrameActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.R.layout;
import com.geekchic.wuyou.logic.login.LoginLogic;

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
		login();
	}

	private void login() {
		if (hasLogined()) {
			sendEmptyMessage(MainMessageCode.MAIN_MESSAGE_MAIN);
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
			Intent intent = new Intent(LoginAction.ACTION);
			startActivity(intent);
			finish();
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
