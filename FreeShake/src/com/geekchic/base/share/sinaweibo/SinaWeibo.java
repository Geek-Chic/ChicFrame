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
import android.os.Bundle;

import com.geekchic.base.share.ShareService;
import com.geekchic.base.share.http.ShareHttpManager;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * @ClassName: SinaWeibo
 * @Descritpion: 新浪微博
 * @author Administrator
 * @date 2014-4-14
 */
public class SinaWeibo extends ShareService 
{
    public static final String NAME = "SinaWeibo";
    private Oauth2AccessToken oauth2AccessToken;
    private WeiboAuth mWeiboAuth;
    private SsoHandler mSsoHandler;
    private String mAppKey;
    private String mAppSecret;
    private String mRedirectUrl;
    
    
    private WeiboAuthListener mSinaAuthListener=new WeiboAuthListener()
    {
        
        @Override
        public void onWeiboException(WeiboException weiboexception)
        {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void onComplete(Bundle bundle)
        {
            oauth2AccessToken=Oauth2AccessToken.parseAccessToken(bundle);
            if(oauth2AccessToken.isSessionValid()){
                //记录信息
            }
        }
        
        @Override
        public void onCancel()
        {
            // TODO Auto-generated method stub
            
        }
    };
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
        return 1;
    }

    @Override
    public int getPlatformId()
    {
        // TODO Auto-generated method stub
        return 0;
    }
    protected void a()
    {
        mAppKey=getParamByKey("AppKey");
        mAppSecret=getParamByKey("AppSecret");
        mRedirectUrl=getParamByKey("RedirectUrl");
     }
//    @Override
//    protected void authUser(String name)
//    {
//        boolean flag=false;
//        SinaParamUtils sinaParamUtils=SinaParamUtils.getInstance();
//        HashMap hashMap=sinaParamUtils.auth(name);
//        if(null==hashMap){
//            if(mBasicShareActionLinstener!=null){
//                mBasicShareActionLinstener.onError(this,ShareService.ACTION_USER_INFOR, new Throwable());
//                return;
//            }
//        }
//        if(hashMap.containsKey("error_code")){
//            int errorCode=((Integer)hashMap.get("error_code")).intValue();
//            if(errorCode!=0){
//                if(mBasicShareActionLinstener!=null){
//                    String error=String.valueOf(hashMap.get("error"));
//                    String request=String.valueOf(hashMap.get("request"));
//                    String errorMsg=(new StringBuilder()).append(error).append(" (").append(errorCode).append("): ").append(request).toString();
//                    mBasicShareActionLinstener.onError(this, ShareService.ACTION_USER_INFOR, new Throwable(errorMsg));
//                }
//                return;
//            }
//        }
//        if(flag){
//            //将id和screen_name插入数据库中
//        }
//        if(mBasicShareActionLinstener!=null){
//            mBasicShareActionLinstener.onComplete(this, ShareService.ACTION_USER_INFOR, hashMap);
//        }
//        return;
//    }
    public void authorize(Context context){
        mWeiboAuth=new WeiboAuth(context, mAppKey,mRedirectUrl,"email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
        mWeiboAuth.authorize(mSinaAuthListener,1);
    }

    @Override
    protected void getUser(String name)
    {
        ShareHttpManager shareHttpManager=ShareHttpManager.getInstance();
        
    }

}
