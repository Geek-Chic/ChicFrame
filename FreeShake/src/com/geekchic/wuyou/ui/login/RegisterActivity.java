/**
 * @Title: RegisterActivity.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.os.Bundle;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.ui.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: RegisterActivity
 * @Descritpion: 注册
 * @author evil
 * @date May 1, 2014
 */
public class RegisterActivity extends BaseTitleBarActivity {
	/**
	 * TAG
	 */
private static final String TAG="RegisterActivity";
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Logger.d(TAG, "error");
}
	@Override
	public int getLayoutId() {
		return R.layout.register_step_first;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("注册");
		return true;
	}
	@Override
	protected boolean needLogin() {
		return false;
	}
	

}
