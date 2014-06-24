/**
 * @Title:BasicShareActionLinstener.java
 * @Package com.geekchic.base.share
 * @Description:[用一句话描述做什么]
 * @author:Administrator
 * @date:2014-4-10
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.HashMap;

import org.apache.http.auth.AUTH;

/**
 * @ClassName: BasicShareActionLinstener
 * @Descritpion:[用一句话描述作用] 
 * @author Administrator
 * @date 2014-4-10
 */
public class BasicShareActionLinstener implements ShareActionLinstener
{
    private ShareActionLinstener mShareActionLinstener;
    public BasicShareActionLinstener(){
        
    }
    public void addBasicShareActionLinstener(ShareActionLinstener shareActionLinstener){
       this.mShareActionLinstener=shareActionLinstener;   
    }
    public ShareActionLinstener getShareActionLinstener(){
        return mShareActionLinstener;
    }
    @Override
    public void onComplete(ShareService shareService, int k, HashMap hashMap)
    {
        switch (k)
        {
            case ShareService.ACTION_AUTHORIZING:
//                  auth();  
                break;
            
            default:
                break;
        }
    }

    @Override
    public void onError(ShareService shareService, int i, Throwable throwable)
    {
        if(mShareActionLinstener!=null){
            mShareActionLinstener.onError(shareService, i, throwable);
        }
    }

    @Override
    public void onCancel(ShareService shareService, int i)
    {
        // TODO Auto-generated method stub
        
    }
    private void auth(ShareService service,int k,HashMap hashMap){
        ShareActionLinstener shareActionLinstener=mShareActionLinstener;
//        mShareActionLinstener=new 
           service.showUser(null);
    }
    public static ShareActionLinstener action(BasicShareActionLinstener basicShareActionLinstener,ShareActionLinstener shareActionLinstener){
        return basicShareActionLinstener.mShareActionLinstener=shareActionLinstener;
    }
     public static ShareActionLinstener getShareActionLinstener(BasicShareActionLinstener basicShareActionLinstener){
         return basicShareActionLinstener.mShareActionLinstener;
     }
}
