/**
 * @Title: SinaWeibo.java
 * @Package com.geekchic.base.share.sinaweibo
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-4-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.sinaweibo;

import android.content.Context;

import com.geekchic.base.share.ShareService;

/**
 * @ClassName: SinaWeibo
 * @Descritpion: 新浪微博
 * @author Administrator
 * @date 2014-4-14
 */
public class SinaWeibo extends ShareService
{
    private String mAppKey;
    private String mAppSecret;
    private String mRedirectUrl;
    public static class ShareParams extends ShareService.ShareParams
    {

        public float longitude;
        public float latitude;
 
        public ShareParams()
        {
        }

        public ShareParams(ShareParams shareparams)
        {
            text = shareparams.text;
            imagePath = shareparams.imagePath;
        }
    }
    public SinaWeibo(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected boolean a(int k, Object obj)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void a(int k, int l, String shareName)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void b(String s)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initShareService(String shareName)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName()
    {
        return "SinaWeibo";
    }

    @Override
    public int getVersion()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPlatformId()
    {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    protected void a()
    {
        mAppKey=getParamByKey("AppKey");
        mAppSecret=getParamByKey("AppSecret");
        mRedirectUrl=getParamByKey("RedirectUrl");
    }
    
}
