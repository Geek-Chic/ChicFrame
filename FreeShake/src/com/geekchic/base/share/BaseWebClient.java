/**
 * @Title: BaseWebClient.java
 * @Package com.geekchic.base.share
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-7-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @ClassName: BaseWebClient
 * @Descritpion: 默认webviewclient
 * @author evil
 * @date 2014-7-14
 */
public class BaseWebClient extends WebViewClient
{


    public BaseWebClient()
    {
    }

    public boolean shouldOverrideUrlLoading(WebView webview, String s)
    {
        return false;
    }

    public void onPageStarted(WebView webview, String s, Bitmap bitmap)
    {
    }

    public void onPageFinished(WebView webview, String s)
    {
    }

    public void onLoadResource(WebView webview, String s)
    {
    }

    public void onTooManyRedirects(WebView webview, Message message, Message message1)
    {
        message.sendToTarget();
    }

    public void onReceivedError(WebView webview, int i, String s, String s1)
    {
    }

    public void onFormResubmission(WebView webview, Message message, Message message1)
    {
        message.sendToTarget();
    }

    public void doUpdateVisitedHistory(WebView webview, String s, boolean flag)
    {
    }

    public void onReceivedSslError(WebView webview, SslErrorHandler sslerrorhandler, SslError sslerror)
    {
        sslerrorhandler.cancel();
    }

    public void onReceivedHttpAuthRequest(WebView webview, HttpAuthHandler httpauthhandler, String s, String s1)
    {
        httpauthhandler.cancel();
    }

    public boolean shouldOverrideKeyEvent(WebView webview, KeyEvent keyevent)
    {
        return false;
    }

    public void onUnhandledKeyEvent(WebView webview, KeyEvent keyevent)
    {
    }

    public void onScaleChanged(WebView webview, float f, float f1)
    {
    }

}
