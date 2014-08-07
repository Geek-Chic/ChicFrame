/**
 * @Title: SinaWeiboManager.java
 * @Package com.geekchic.base.share.sinaweibo
 * @Description: 新浪微博请求管理类
 * @author: evil
 * @date: 2014-7-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.sinaweibo;

import java.util.ArrayList;

import com.geekchic.base.share.http.HttpManager;

import android.content.Context;

/**
 * @ClassName: SinaWeiboManager
 * @Descritpion: 新浪微博请求管理类
 * @author evil
 * @date 2014-7-14
 */
public class SinaWeiboManager
{
    public static SinaWeiboManager sInstance;
    private int b;
    private String mClientId;
    private String mRedirectUrl;
    private String mAccessToken;
    public static synchronized SinaWeiboManager getInstance(){
        if(null==sInstance){
            sInstance=new SinaWeiboManager();
        }
        return sInstance;
    }
}
