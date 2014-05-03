/**
 * @Title: SetpOneFragment.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.URLs;

/**
 * @ClassName: SetpOneFragment
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date May 1, 2014
 */
public class RegisterStepOneFragment extends Fragment implements OnClickListener {
	/**
	 * 申明
	 */
	private WebView mWebView;
	/**
	 * 同意并继续
	 */
	private Button mContinueButton;
  @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	  View view=inflater.inflate(R.layout.register_step_one, null);
	  mWebView=(WebView) view.findViewById(R.id.register_declaration_view);
	  mContinueButton=(Button) view.findViewById(R.id.register_declaration_confirm);
	  mContinueButton.setOnClickListener(this);
	  mWebView.loadUrl(URLs.REGISTER_DECLARATION);
	return view;
}
@Override
public void onClick(View v) {
	RegisterActivity main=(RegisterActivity) getActivity();
	main.stepToSecondFragment();
}
}
