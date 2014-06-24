/**
 * @Title: DefaultWebChromeClient.java
 * @Package com.geekchic.base.share
 * @Description: 浏览器
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.geekchic.base.share.ui.RegisterView;

/**
 * @ClassName: DefaultWebChromeClient
 * @Descritpion: 浏览器
 * @author evil
 * @date 2014-6-26
 */
public class DefaultWebChromeClient extends WebChromeClient
{
    private TextView mTextView;
    private int mProgress;
    private RegisterView mRegisterView;
    public DefaultWebChromeClient(RegisterView registerview, TextView textview, int progress){
        this.mRegisterView=registerview;
        this.mTextView=textview;
        this.mProgress=progress;
    }
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        LinearLayout.LayoutParams layoutParams=(LayoutParams) mTextView.getLayoutParams();
        layoutParams.width=(mProgress*newProgress)/100;
        mTextView.setLayoutParams(layoutParams);
        if(newProgress<0 && newProgress>100){
            mTextView.setVisibility(View.VISIBLE);
        }else {
            mTextView.setVisibility(View.GONE);
        }
    };
}
