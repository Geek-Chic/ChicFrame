/**
 * @Title: LoginActivity.java
 * @Package com.geekchic.wuyou.ui
 * @Description: 登录
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.geekchic.common.log.Logger;
import com.geekchic.constant.AppAction.RegisterAction;
import com.geekchic.framework.ui.BaseFrameActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.logic.login.ILoginLogic;

/**
 * @ClassName: LoginActivity
 * @Descritpion: 登录
 * @author evil
 * @date Apr 30, 2014
 */
public class LoginActivity extends BaseFrameActivity implements OnClickListener {
	/**
	 * TAG
	 */
	private static final String TAG = "LoginActivity";
	/**
	 * 登录
	 */
	private Button mLoginButton;
	/**
	 * 注册
	 */
	private TextView mRegisterView;
	/**
	 * 登录逻辑
	 */
	private ILoginLogic mLoginLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initViews();
	}

	private void initViews() {
		mLoginButton = (Button) findViewById(R.id.login_btn);
		mRegisterView = (TextView) findViewById(R.id.login_tv_register);
		mLoginButton.setOnClickListener(this);
		mRegisterView.setOnClickListener(this);
	}

	@Override
	protected void initLogics() {
		super.initLogics();
		mLoginLogic = (ILoginLogic) getLogic(ILoginLogic.class);
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login_btn) {
			mLoginLogic.login("b", "a");
		} else if (v.getId() == R.id.login_tv_register) {
			Intent intent=new Intent(RegisterAction.ACTION);
			startActivity(intent);
		}
	}

	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		Logger.d(TAG, "有消息了" + msg.obj);
	}
}
