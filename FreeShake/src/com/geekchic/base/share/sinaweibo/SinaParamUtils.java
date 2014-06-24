/**
 * @Title: SinaParamUtils.java
 * @Package com.geekchic.base.share.sinaweibo
 * @Description: 分享参数工具类
 * @author: jp
 * @date: 2014-7-1
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.sinaweibo;

import java.util.ArrayList;
import java.util.HashMap;

import com.geekchic.base.share.BasePairs;
import com.geekchic.base.share.ShareHttpManager;
import com.geekchic.base.share.ShareManager;
import com.geekchic.base.share.ui.AuthorizeListener;
import com.geekchic.base.share.util.ResultUtils;
import com.geekchic.common.utils.StringUtils;

/**
 * @ClassName: SinaParamUtils
 * @Descritpion: 分享参数工具类
 * @author jp
 * @date 2014-7-1
 */
public class SinaParamUtils
{
    private static SinaParamUtils sInstance=null;
    private String sourceString;
    private String accessTokenString;
    private String uidScreenName;
    private int b;
    public static synchronized SinaParamUtils getInstance(){
        if(null==sInstance){
            sInstance=new SinaParamUtils();
        }
        return sInstance;
    }
    public HashMap auth(String name){
        ArrayList<BasePairs> arrayList=new ArrayList<BasePairs>();
        arrayList.add(new BasePairs("source", sourceString));
        if(StringUtils.isNullOrEmpty(accessTokenString)){
            arrayList.add(new BasePairs("access_token", accessTokenString));
        }
        boolean flag=true;
        try
        {
            Long.parseLong(name);
        }
        catch (Exception e)
        {
            flag=false;
        }
        if(flag){
            arrayList.add(new BasePairs("uid", uidScreenName));
        }else {
            arrayList.add(new BasePairs("screen_name", uidScreenName));
        }
        String useUrl="https://api.weibo.com/2/users/show.json";
        ShareHttpManager shareHttpManager=ShareHttpManager.getInstance();
        String res=shareHttpManager.a(useUrl, arrayList,"/2/users/show.json",b);
        if(!StringUtils.isNullOrEmpty(res)){
            return (new ResultUtils()).decodeRes(res);
        }else{
            return null;
        }
            
    }
    public void addAuthorizeLinstener(AuthorizeListener authorizeListener){
        SinaSSOLinstener sinaSSOLinstener=new SinaSSOLinstener(this, authorizeListener);
    }
}
