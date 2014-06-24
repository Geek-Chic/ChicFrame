/**
 * @Title: AboutActivity.java
 * @Package com.geekchic.wuyou.ui.feedback
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: AboutActivity
 * @Descritpion: 关于
 * @author evil
 * @date May 17, 2014
 */
public class AboutActivity extends BaseTitleBarActivity implements OnClickListener{
	/**
	 * 邮件
	 */
    LinearLayout mailLayout;
    /**
     *  后退监听
     */
	private OnClickListener mbackClickListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			finishActivity();
		}
		
	};
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.about;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.about);
		setTitleBarBackground(R.color.blue);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mbackClickListener);
		return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mailLayout=(LinearLayout) findViewById(R.id.about_mail_feedback);
		mailLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		 Intent intent = new Intent(Intent.ACTION_SEND);
		 intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jiangpengctc@gmail.com"});
		 intent.putExtra(Intent.EXTRA_SUBJECT,"Wuyou Exchange");
		 intent.putExtra(Intent.EXTRA_TEXT, "");
		  intent.setType("text/plain"); 
		  startActivity(intent); 
	}

}
