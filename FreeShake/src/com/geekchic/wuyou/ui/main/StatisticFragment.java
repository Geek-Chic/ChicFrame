/**
 * @Title: StatisticFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: 统计视图
 * @author: evil
 * @date: May 10, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.ui.main.view.JSinterface;

/**
 * @ClassName: StatisticFragment
 * @Descritpion: 统计视图
 * @author evil
 * @date May 10, 2014
 */
public class StatisticFragment extends BaseFrameFragment {
	private WebView mWebView;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
   @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	   View view=inflater.inflate(R.layout.project_fragment_statistic, null);
	   initView(view);
	return view;
}
@SuppressLint("JavascriptInterface")
private void initView(View view) {
	// TODO Auto-generated method stub
	mWebView=(WebView) view.findViewById(R.id.statistic_webview);
	mWebView.setHorizontalScrollBarEnabled(true);
    
	WebSettings settings = mWebView.getSettings();

	// 设置字符集编码
	settings.setDefaultTextEncodingName("UTF-8");
	// 开启JavaScript支持
	settings.setJavaScriptEnabled(true);
	settings.setSupportZoom(true);
	settings.setBuiltInZoomControls(true);
	settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
	
	mWebView.addJavascriptInterface(new JSinterface(getActivity(), mHandler, mWebView), "chartObject");
	// 加载assets目录下的文件
	String url = "file:///android_asset/index.html";
	mWebView.loadUrl(url);

}
}
