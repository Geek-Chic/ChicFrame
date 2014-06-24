/**
 * @Title: ShreadRequestThread.java
 * @Package com.geekchic.base.share
 * @Description: 分享线程
 * @author: evil
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.ArrayList;

import android.content.Context;

import com.geekchic.base.share.dao.ConnParams;

/**
 * @ClassName: ShreadRequestThread
 * @Descritpion: 分享线程
 * @author evil
 * @date 2014-7-2
 */
public class ShareRequestThread extends Thread
{
    private static ShareRequestThread sInstance;
    private Context mContext;
    private ArrayList arrayList;
    private NetManager mNetManager;
    private String mAppKey;
    private boolean flag;
    public static synchronized ShareRequestThread getInstance(Context context){
        if(null==sInstance){
            sInstance=new ShareRequestThread(context);
        }
        return sInstance;
    }
    private ShareRequestThread(Context context){
        this.mContext=context;
        this.arrayList=new ArrayList();
        this.mNetManager=NetManager.getInstance(context);
    }
    public synchronized void start(){
        if(!flag){
            flag=true;
            super.start();
        }
    }
    public void a(ConnParams connParams){
        synchronized (arrayList)
        {
            if(flag){
                initHttpParams(connParams);
                arrayList.add(connParams);
            }
        }
    }
    private void initHttpParams(ConnParams connParams){
//        connParams.=this.mNetManager.geth
    }
    @Override
    public void run()
    {
        super.run();
        while (flag || arrayList.size()>0)
        {
            
        }
    }
    public void setAppKey(String appkey){
        this.mAppKey=appkey;
    }
}
