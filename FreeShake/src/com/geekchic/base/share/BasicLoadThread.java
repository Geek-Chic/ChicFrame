/**
 * @Title:BasicLoadThread.java
 * @Package com.geekchic.base.share
 * @Description:[用一句话描述做什么]
 * @author:Administrator
 * @date:2014-4-10
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.ArrayList;

import android.content.Context;

/**
 * @ClassName: BasicLoadThread
 * @Descritpion:[用一句话描述作用] 
 * @author Administrator
 * @date 2014-4-10
 */
public class BasicLoadThread extends Thread
{
    private static BasicLoadThread INSTANCE;
    private Context mContext;
    private ArrayList d;
    private String e;
    private boolean f;
    private NetManager mNetManager;
    
    public static synchronized BasicLoadThread getInstance(Context context){
        if(INSTANCE==null){
            if(context==null){
                return null;
            }
            INSTANCE=new BasicLoadThread(context.getApplicationContext());
                
        }
        return INSTANCE;
    }
    private BasicLoadThread(Context context){
        this.mContext=context;
        d=new ArrayList();
        mNetManager=NetManager.getInstance(context);
    }
    public synchronized void start(){
        if(!f){
            f=true;
            super.start();
        }
    }
    @Override
    public void run()
    {
        super.run();
        while(f|| d.size()>0){
            synchronized (d){
                if(d.size()>0){
                    
                }
            }
            {
                
            }
        }
    }
}
