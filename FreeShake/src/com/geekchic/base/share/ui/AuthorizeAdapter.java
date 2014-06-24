/**
 * @Title: AuthorizeAdapter.java
 * @Package com.geekchic.base.share.ui
 * @Description: 分享界面实体
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import android.app.Activity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.geekchic.framework.ui.titlebar.TitleBar;

/**
 * @ClassName: AuthorizeAdapter
 * @Descritpion：分享界面实体 
 * @author Administrator
 * @date 2014-6-26
 */
public class AuthorizeAdapter
{
    private Activity activity;
    private TitleBar title;
    private WebView webview;
    private boolean noTitle;
    private String weibo;
    private RelativeLayout rlBody;
    private boolean popUpAnimationDisable;
    public AuthorizeAdapter()
    {
    }

    void setActivity(Activity activity1)
    {
        activity = activity1;
    }

    public Activity getActivity()
    {
        return activity;
    }

    void setTitleView(TitleBar titlelayout)
    {
        title = titlelayout;
    }

    public TitleBar getTitleLayout()
    {
        return title;
    }

    void setWebView(WebView webview1)
    {
        webview = webview1;
    }

    public WebView getWebBody()
    {
        return webview;
    }

    void setNotitle(boolean flag)
    {
        noTitle = flag;
    }

    boolean isNotitle()
    {
        return noTitle;
    }

    void setWeiboName(String s)
    {
        weibo = s;
    }

    public String getWeiboName()
    {
        return weibo;
    }

    void setBodyView(RelativeLayout relativelayout)
    {
        rlBody = relativelayout;
    }

    public RelativeLayout getBodyView()
    {
        return rlBody;
    }

    public void onCreate()
    {
    }

    public void onResize(int i, int j, int k, int l)
    {
    }

    public void onDestroy()
    {
    }

    public boolean onKeyEvent(int i, KeyEvent keyevent)
    {
        return false;
    }

    protected void disablePopUpAnimation()
    {
        popUpAnimationDisable = true;
    }

    boolean isPopUpAnimationDisable()
    {
        return popUpAnimationDisable;
    }
    
}
