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
 * @Descritpion:[用一句话描述作用] 
 * @author Administrator
 * @date 2014-4-10
 */
public class ActionThread extends Thread
{
    private static final String TAG=ActionThread.class.getName();
    private final int  action;
    private final Object obj;
    private ShareService mShareService;
    public ActionThread(ShareService service,int i,Object obj){
        super(TAG);
        this.action=i;
        this.mShareService=service;
        this.obj=obj;
    }
    @Override
    public void run()
    {
        super.run();
        if(mShareService.a(action,obj)){
            mShareService.handle(action, obj);
        }
    }
}
