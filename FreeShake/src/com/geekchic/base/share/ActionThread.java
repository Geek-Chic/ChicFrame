/**
 * @Title:AuthThread.java
 * @Package com.geekchic.base.share
 * @Description:[用一句话描述做什么]
 * @author:Administrator
 * @date:2014-4-10
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

/**
 * @ClassName: AuthThread
 * @Descritpion:分享操作线程
 * @author Administrator
 * @date 2014-4-10
 */
public class ActionThread extends Thread
{
    private static final String TAG=ActionThread.class.getName();
    private final int  mActionId;
    private final Object obj;
    private ShareService mShareService;
    public ActionThread(ShareService service,int actionId,Object obj){
        super(TAG);
        this.mActionId=actionId;
        this.mShareService=service;
        this.obj=obj;
    }
    @Override
    public void run()
    {
        super.run();
        if(mShareService.isValid()){
            mShareService.handle(mActionId, obj);
        }
    }
}
