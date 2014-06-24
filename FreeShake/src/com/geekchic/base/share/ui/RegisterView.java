/**
 * @Title: RegisterView.java
 * @Package com.geekchic.base.share.ui
 * @Description: web界面
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geekchic.base.share.DefaultWebChromeClient;
import com.geekchic.framework.ui.titlebar.TitleBar;
import com.geekchic.wuyou.R;

/**
 * @ClassName: RegisterView
 * @Descritpion: web界面
 * @author Administrator
 * @date 2014-6-26
 */
public class RegisterView extends ResizeLayout
{
    private TitleBar mTitleBar;
    private RelativeLayout mRelativeLayout;
    private WebView mWebView;
    
    public RegisterView(Context context)
    {
        super(context);
        initView(context);
        
    }
    public RegisterView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        initView(context);
    }
    private void initView(Context context){
        int width=getScreenWidth(context);
        setBackgroundColor(-1);
        setOrientation(LinearLayout.VERTICAL);
        mTitleBar=new TitleBar(context, null);
        mTitleBar.setTitle(R.string.weibo_oauth_regiseter);
        addView(mTitleBar);
        
        mRelativeLayout=new RelativeLayout(context);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        layoutParams.weight=1.0f;
        mRelativeLayout.setLayoutParams(layoutParams);
        addView(mRelativeLayout);
        
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams LayoutParams2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(LayoutParams2);
        mRelativeLayout.addView(linearLayout);
        
        TextView textview = new TextView(context);
        textview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5));
        textview.setBackgroundColor(-12929302);
        linearLayout.addView(textview);
        textview.setVisibility(8);
        
        mWebView =new WebView(context);
        LinearLayout.LayoutParams webLayoutParams = new android.widget.LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        webLayoutParams.weight=1.0f;
        mWebView.setLayoutParams(webLayoutParams);
        DefaultWebChromeClient webChromeClient=new DefaultWebChromeClient(this, textview, width);
        mWebView.setWebChromeClient(webChromeClient);
        linearLayout.addView(mWebView);
        mWebView.requestFocus();
    }
    private int getScreenWidth(Context context){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        if(!(context instanceof Activity))
            return 0;
        WindowManager windowmanager = ((Activity)context).getWindowManager();
        if(windowmanager == null)
        {
            return 0;
        } else
        {
            windowmanager.getDefaultDisplay().getMetrics(displaymetrics);
            return displaymetrics.widthPixels;
        }
    }
       
}
