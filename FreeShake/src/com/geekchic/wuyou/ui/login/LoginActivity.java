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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConstants.REQUESTCODE;
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
	 * 用户输入框
	 */
	private EditText mAccountEditText;
	/**
	 * 密码输入框
	 */
	private EditText mPasswordEditText;
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
		
		mAccountEditText=(EditText) findViewById(R.id.login_account_edt);
		mPasswordEditText=(EditText) findViewById(R.id.longin_password_edt);
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
			doLogin();
		} else if (v.getId() == R.id.login_tv_register) {
			mLoginLogic.logout();
//			Intent intent=new Intent(RegisterAction.ACTION);
//			startActivity(intent);
		}
	}
	 /**
     * 做登录操作
     */
    private void doLogin()
    {
        String accountText = mAccountEditText.getText().toString().trim();
        String passwordText = mPasswordEditText.getText().toString();
        
        if (StringUtil.isNullOrEmpty(accountText))
        {
            //用户名为空
            showShortToast("用户名为空");
            return;
        }
        else if (StringUtil.isNullOrEmpty(passwordText))
        {
            //密码为空
            showShortToast("密码不能为空");
            return;
        }
        if (!StringUtil.isNullOrEmpty(passwordText))
        {
            showProgressDialog(R.string.dialog_loading, true);
            mLoginLogic.login(accountText, passwordText);
        }
    }
	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		Logger.d(TAG, "有消息了" + msg.obj);
		switch (msg.what) {
		case AppActionCode.LoginCode.MESSAGE_LOGIN_SUCCESS:
			Bundle bundle=(Bundle) msg.obj;
			Toast.makeText(this,bundle.getString(REQUESTCODE.REQUEST_RESULT), Toast.LENGTH_LONG).show();
			closeProgressDialog();
			if(bundle.getInt("code")==0){
				Intent intent=new Intent(AppAction.MainAction.ACTION);
				startActivity(intent);
				finishActivity();
			}
			break;
		case AppActionCode.BaseMessageCode.HTTP_ERROR:
			closeProgressDialog();
			Logger.e(TAG, "网络错误");
			Toast.makeText(this,"网络错误", Toast.LENGTH_LONG).show();
		default:
			break;
		}
//		JSON.parse(bundle.getString("result"));
	}
}
