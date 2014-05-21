/**
 * @Title: FeedbackActivity.java
 * @Package com.geekchic.wuyou.ui.feedback
 * @Description: 消息反馈
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.feedback;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.geekchic.common.utils.PreferencesUtil;
import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.Common;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.logic.feedback.IFeedbackLogic;

/**
 * @ClassName: FeedbackActivity
 * @Descritpion: 消息反馈
 * @author evil
 * @date May 17, 2014
 */
public class FeedbackActivity extends BaseTitleBarActivity implements OnClickListener{

	/**
	 * 反馈输入
	 */
	private EditText mEditText;
	/**
	 * 反馈确认
	 */
	private Button mConfirmButton;
	/**
	 * 提交Logic
	 */
	private IFeedbackLogic mFeedbackLogic;
	/**
	 * 反馈后退
	 */
	private OnClickListener mBackOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finishActivity();
		}
	};
	@Override
	public int getLayoutId() {
		return R.layout.feed_back_view;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.feedback_title);
		setTitleBarBackground(R.color.blue);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackOnClickListener);
		return true;
	}
	@Override
	protected void initLogics() {
		super.initLogics();
		this.mFeedbackLogic=(IFeedbackLogic) getLogic(IFeedbackLogic.class);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	private void initView(){
		mEditText=(EditText) findViewById(R.id.feed_back_edit);
		mConfirmButton=(Button) findViewById(R.id.feed_back_btn);
		mConfirmButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		  if(v.getId()==R.id.feed_back_btn){
			  String message=mEditText.getText().toString().trim();
			  if(!StringUtil.isNullOrEmpty(message)){
				  String uuid=PreferencesUtil.getAttrString(Common.KEY_USER_ID);
				  mFeedbackLogic.setFeedBack(uuid, message);
				  showProgressDialog(R.string.dialog_upload,true);
			  }
		  }
	}
	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		switch (msg.what) {
		case AppActionCode.FeedBackCode.FEED_BACK_SUCCESS:
			 showShortToast(R.string.toast_success);
			 finishActivity();
			break;
		case AppActionCode.FeedBackCode.FEED_BACK_FAILED:
			 showShortToast(R.string.toast_failed);
		case AppActionCode.BaseMessageCode.HTTP_ERROR:
			showShortToast(R.string.toast_http_error);
		default:
			break;
		}
		closeProgressDialog();
	}

}
