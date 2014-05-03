/**
 * @Title: RegisterStepTwo.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description:  注册步骤二
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.geekchic.wuyou.R;

/**
 * @ClassName: RegisterStepTwo
 * @Descritpion: 注册步骤二
 * @author evil
 * @date May 1, 2014
 */
public class RegisterStepTwoFragment extends Fragment implements OnClickListener {
	/**
     * 时间间隔
     */
    private static final long DELAY = 1000L;
    /**
     * 倒计时总时间
     */
    private static final int COUNT_DOWN_MAX_TIME = 60;
	/**
	 * 
	 * 手机电话
	 */
    private EditText mPhoneEditText;
    /**
     * 验证码
     */
    private EditText mVerficationEditText;
    /**
     * 验证
     */
    private Button mVerficationButton;
    /**
     * 倒计时器
     */
    private CountDownTimer mCountDownTimer;
    /**
     * 验证标识
     */
    private boolean isVerficated=false;
    /**
     * 监听验证码变化
     */
    private TextWatcher mVerficationTextWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			if(s.length()==6){
				stopCountDown();
				isVerficated=true;
			}
			
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.register_step_two, null);
		initView(view);
		initCountDown();
		return view;
	}
	private void initView(View view){
		mPhoneEditText=(EditText) view.findViewById(R.id.register_phone_edt);
		mVerficationEditText=(EditText) view.findViewById(R.id.register_verification_text);
		mVerficationButton=(Button) view.findViewById(R.id.register_verification_btn);
		mVerficationButton.setOnClickListener(this);
		mVerficationEditText.addTextChangedListener(mVerficationTextWatcher);
	}
	private void initCountDown(){
		mCountDownTimer=new CountDownTimer(COUNT_DOWN_MAX_TIME*DELAY, DELAY) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				mVerficationButton.setText("倒计时："+millisUntilFinished/DELAY);
			}
			
			@Override
			public void onFinish() {
				stopCountDown();
			}
		};
	}
	private void startCountDown(){
		mVerficationButton.setClickable(false);
		mVerficationButton.setEnabled(false);
		mCountDownTimer.start();
	}
	/**
	 * 停止计时
	 */
	private void stopCountDown(){
		mVerficationButton.setClickable(true);
		mVerficationButton.setEnabled(true);
		mVerficationButton.setText("验证码");
		mCountDownTimer.cancel();
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.register_verification_btn){
		   if(!isVerficated){
			   startCountDown();
		   }else {
			RegisterActivity registerActivity=(RegisterActivity) getActivity();
			registerActivity.stepToThirdFragment();
		}
		}
	}
	
}
