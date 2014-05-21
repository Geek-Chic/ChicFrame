/**
 * @Title: RegisterStepThirdFragment.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description: 注册步骤三--个人信息
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConfig;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.logic.registration.IRegistrationLogic;

/**
 * @ClassName: RegisterStepThirdFragment
 * @Descritpion: 注册步骤三--个人信息
 * @author evil
 * @date May 1, 2014
 */
public class RegisterStepThirdFragment extends BaseFrameFragment implements OnClickListener{
	/**
	 * 完成注册去主界面
	 */
	private Button mGoButton;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 姓名输入框
	 */
	private EditText mTrueNameEditText;
	/**
	 * 密码输入框
	 */
	private EditText mPasswordEditText;
	/**
	 * 注册Logic
	 */
	private IRegistrationLogic mRegistrationLogic;
	/**
	 * 生成新的Fragment
	 * @param phone
	 * @return
	 */
	public static RegisterStepThirdFragment newInstance(String phone){
	   RegisterStepThirdFragment registerStepThirdFragment=new RegisterStepThirdFragment();
	   Bundle bundle=new Bundle();
	   bundle.putString("phone", phone);
	   registerStepThirdFragment.setArguments(bundle);
	return registerStepThirdFragment;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
    	Bundle args = getArguments();  
        if (args != null) {  
        	phone = args.getString("phone");  
        }  
    	View view=inflater.inflate(R.layout.register_step_third, null);
    	mGoButton=(Button) view.findViewById(R.id.register_confirm);
    	mGoButton.setOnClickListener(this);
    	mTrueNameEditText=(EditText) view.findViewById(R.id.register_truename_edit);
    	mPasswordEditText=(EditText)view.findViewById(R.id.register_password);
    	return view;
    }
    @Override
    public void onClick(View v) {
    	if(v.getId()==R.id.register_confirm){
          doRegister();    		
    	}
    }
	private void doRegister(){
		String trueName=mTrueNameEditText.getText().toString().trim();
		if(StringUtil.isNullOrEmpty(trueName)){
			showShortToast(R.string.register_error_name);
			return;
		}
		String password=mPasswordEditText.getText().toString().trim();
		if(StringUtil.isNullOrEmpty(password)){
			showShortToast(R.string.register_error_password);
			return;
		}
		String pushId=AppConfig.getInstance().getPushUid();
		String channelId=AppConfig.getInstance().getChannelId();
		mRegistrationLogic.register(trueName, phone, password,pushId,channelId);
		showProgressDialog(R.string.dialog_loading, true);
		
	}
	@Override
	protected void initLogics() {
		super.initLogics();
		mRegistrationLogic=(IRegistrationLogic) getLogicByInterfaceClass(IRegistrationLogic.class);
	}
	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		switch (msg.what) {
		case AppActionCode.RegistrationCode.REGISTER_SUCCESS:
			showShortToast("登录成功");
			closeProgressDialog();
			
			RegisterActivity activity=(RegisterActivity) getActivity();
			activity.stepToMain();
			
			break;
		case AppActionCode.RegistrationCode.REGISETER_FAILED:
			showShortToast("注册数据错误");
			closeProgressDialog();
		case AppActionCode.BaseMessageCode.HTTP_ERROR:
			showShortToast("网络错误");
			closeProgressDialog();
		default:
			break;
		}
	}
}
