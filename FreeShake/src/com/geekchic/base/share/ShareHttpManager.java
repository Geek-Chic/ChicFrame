/**
 * @Title: ShareHttpManager.java
 * @Package com.geekchic.base.share
 * @Description: 分享Http请求封装 
 * @author: evil
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.ArrayList;

import android.text.TextUtils;

import com.geekchic.base.share.dao.ApiConnParams;
import com.geekchic.base.share.http.HttpManager;

/**
 * @ClassName: ShareHttpManager
 * @Descritpion: 分享Http请求封装 
 * @author evil
 * @date 2014-7-2
 */
public class ShareHttpManager extends HttpManager
{
    private static ShareHttpManager sInstance;
    private ShareHttpManager(){
    }
    public static ShareHttpManager getInstance(){
        if(null==sInstance){
            sInstance=new ShareHttpManager();
        }
        return sInstance;
    }
    public String a(String s, ArrayList arraylist, String s1, int i)
    {
        return a(s, arraylist, ((ArrayList) (null)), null, s1, i);
    }

    public String a(String s, ArrayList arraylist, ArrayList arraylist1, ArrayList arraylist2, String s1, int i)
    {
        a(s1, i);
        return super.openUrl(s, arraylist, arraylist1, arraylist2);
    }

//    public String b(String s, ArrayList arraylist, String s1, int i)
//    {
//        return a(s, arraylist, null, s1, i);
//    }
//
//    public String a(String s, ArrayList arraylist, cn.sharesdk.framework.b.a a1, String s1, int i)
//    {
//        return a(s, arraylist, a1, null, s1, i);
//    }
//
//    public String a(String s, ArrayList arraylist, cn.sharesdk.framework.b.a a1, ArrayList arraylist1, String s1, int i)
//    {
//        a(s1, i);
//        return a(s, arraylist, a1, arraylist1, null, s1, i);
//    }
//
//    public String a(String s, ArrayList arraylist, cn.sharesdk.framework.b.a a1, ArrayList arraylist1, ArrayList arraylist2, String s1, int i)
//    {
//        return super.a(s, arraylist, a1, arraylist1, arraylist2);
//    }
//
    private void a(String s, int i)
    {
        if(TextUtils.isEmpty(s) || i <= 0)
            return;
        ShareRequestThread requestThread = ShareRequestThread.getInstance(null);
        if(requestThread == null)
        {
            return;
        } else
        {
            ApiConnParams a1 = new ApiConnParams();
            a1.b = s;
            a1.a = i;
            requestThread.a(a1);
            return;
        }
    }
}  
