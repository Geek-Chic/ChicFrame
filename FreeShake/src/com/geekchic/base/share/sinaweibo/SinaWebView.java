/**
 * @Title: SinaWebView.java
 * @Package com.geekchic.base.share.sinaweibo
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-7-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.sinaweibo;

import android.webkit.WebView;

import com.geekchic.base.share.BaseWebClient;

/**
 * @ClassName: SinaWebView
 * @Descritpion: [用一句话描述作用] 
 * @author Administrator
 * @date 2014-7-14
 */
public class SinaWebView
{
    private String b;
    private String url;
    private boolean d;
    private class SinaWeb extends BaseWebClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            if(s.startsWith("")){
                
            }
            return super.shouldOverrideUrlLoading(webview, s);
        }
        private void doCall(WebView webview,String s){
            
        }
    }
}
