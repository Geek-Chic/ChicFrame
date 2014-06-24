/**
 * @Title: SinaSSOLinstener.java
 * @Package com.geekchic.base.share.sinaweibo
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-7-1
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.sinaweibo;

import com.geekchic.base.share.ui.AuthorizeListener;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * @ClassName: SinaSSOLinstener
 * @Descritpion: 新浪SSO监听
 * @author Administrator
 * @date 2014-7-1
 */
public class SinaSSOLinstener implements SSOLinstener
{
    private SinaParamUtils mSinaParamUtils;
    private AuthorizeListener mAuthorizeListener;
    public SinaSSOLinstener(SinaParamUtils sinaParamUtils,AuthorizeListener authorizeListener){
        this.mSinaParamUtils=sinaParamUtils;
        this.mAuthorizeListener=authorizeListener;
    }
    @Override
    public void onFailed(Throwable throwable)
    {
        CookieSyncManager.createInstance(mAuthorizeListener.getContext());
        CookieManager cookiemanager = CookieManager.getInstance();
        cookiemanager.removeAllCookie();
        CookieSyncManager.createInstance(mAuthorizeListener.getContext());
        mAuthorizeListener.startAuthorize();
    }

    @Override
    public void onCancel()
    {
        mAuthorizeListener.onCancel();
    }

    @Override
    public void onComplete(Bundle bundle)
    {
        mAuthorizeListener.onComplete(bundle);
    }
    
}
