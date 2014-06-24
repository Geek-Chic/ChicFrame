/**
 * @Title: RegisterStepTwo.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description:  注册步骤二--号码验证
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geekchic.common.utils.StringUtils;
import com.geekchic.common.utils.ToastUtil;
import com.geekchic.constant.AppActionCode;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.logic.registration.IRegistrationLogic;

/**
 * @ClassName: RegisterStepTwo
 * @Descritpion: 注册步骤二--号码验证
 * @author evil
 * @date May 1, 2014
 */
public class RegisterStepTwoFragment extends BaseFrameFragment implements
		OnClickListener {
	/**
	 * TAG
	 */
	private static final String TAG = "RegisterStepTwoFragment";
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
	 * 验证码
	 */
	private String mCaptcha;
	/**
	 * 倒计时器
	 */
	private CountDownTimer mCountDownTimer;
	/**
	 * 验证标识
	 */
	private boolean isVerficated = false;
	/**
	 * phone
	 */
	private String tempPhone;
	/**
	 * 注册Logic
	 */
	private IRegistrationLogic mIRegistrationLogic;
	/**
	 * 监听验证码变化
	 */
	private TextWatcher mVerficationTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (!StringUtils.isNullOrEmpty(mCaptcha)
					&& s.toString().equals(mCaptcha)) {
				stopCountDown();
				tempPhone=mPhoneEditText.getText().toString().trim();
				mPhoneEditText.setEnabled(false);
				isVerficated = true;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.register_step_two, null);
		initView(view);
		initCountDown();
		return view;
	}

	private void initView(View view) {
		mPhoneEditText = (EditText) view.findViewById(R.id.register_phone_edt);
		mVerficationEditText = (EditText) view
				.findViewById(R.id.register_verification_text);
		mVerficationButton = (Button) view
				.findViewById(R.id.register_verification_btn);
		mVerficationButton.setOnClickListener(this);
		mVerficationEditText.addTextChangedListener(mVerficationTextWatcher);
	}

	private void initCountDown() {
		mCountDownTimer = new CountDownTimer(COUNT_DOWN_MAX_TIME * DELAY, DELAY) {

			@Override
			public void onTick(long millisUntilFinished) {
				mVerficationButton
						.setText("倒计时：" + millisUntilFinished / DELAY);
			}

			@Override
			public void onFinish() {
				stopCountDown();
			}
		};
	}

	private void startCountDown() {
		mVerficationButton.setClickable(false);
		mVerficationButton.setEnabled(false);
		mCountDownTimer.start();
	}

	/**
	 * 停止计时
	 */
	private void stopCountDown() {
		mVerficationButton.setClickable(true);
		mVerficationButton.setEnabled(true);
		mVerficationButton.setText("验证码");
		mCountDownTimer.cancel();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.register_verification_btn) {
			if (!isVerficated) {
				String phone = mPhoneEditText.getText().toString().trim();
				if (!StringUtils.isNullOrEmpty(phone)) {
					startCountDown();
					mIRegistrationLogic.getCaptcha(phone);
				} else {
					ToastUtil.showShort(getActivity(), "电话号码不能为空");
				}
			} else {
				RegisterActivity registerActivity = (RegisterActivity) getActivity();
				registerActivity.setPhone(tempPhone);
				registerActivity.stepToThirdFragment();
			}
		}
	}

	@Override
	protected void initLogics() {
		super.initLogics();
		mIRegistrationLogic = (IRegistrationLogic) getLogicByInterfaceClass(IRegistrationLogic.class);
	}

	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		Bundle bundle = (Bundle) msg.obj;
		switch (msg.what) {
		case AppActionCode.RegistrationCode.AUTH_REQUEST_SUCCESS:
			mCaptcha = bundle.getString("captcha");
			break;
		case AppActionCode.RegistrationCode.AUTH_REQUEST_FAILED:
			showShortToast("获取验证码失败");
		default:
			break;
		}
	}
}
