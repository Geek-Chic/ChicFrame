/**
 * @Title:BasicShareChildActionLinstener.java
 * @Package com.geekchic.base.share
 * @Description:[用一句话描述做什么]
 * @author:Administrator
 * @date:2014-4-10
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.HashMap;

import android.widget.ShareActionProvider;

/**
 * @ClassName: BasicShareChildActionLinstener
 * @Descritpion:[用一句话描述作用] 
 * @author Administrator
 * @date 2014-4-10
 */
public class BasicShareChildActionLinstener implements ShareActionLinstener
{
    private static final String TAG=BasicShareChildActionLinstener.class.getName();
     private ShareActionLinstener mShareActionLinstener;
     private HashMap<String,String> mShareParams;
     private int c;
     private final BasicShareActionLinstener mBasicShareActionLinstener;
     public BasicShareChildActionLinstener(BasicShareActionLinstener basicShareActionLinstener,ShareActionLinstener shareActionLinstener,HashMap<String,String> hashMap,int i){
         this.mBasicShareActionLinstener=basicShareActionLinstener;
         this.mShareActionLinstener=shareActionLinstener;
         this.mShareParams=hashMap;
         this.c=i;
     }
    @Override
    public void onComplete(ShareService shareService, int i, HashMap hashMap)
    {
        mBasicShareActionLinstener.action(mBasicShareActionLinstener, mShareActionLinstener);
        if(mBasicShareActionLinstener.getShareActionLinstener(mBasicShareActionLinstener)!=null){
            mBasicShareActionLinstener.getShareActionLinstener(mBasicShareActionLinstener).onComplete(shareService, i, hashMap);
        }
        
    }

    @Override
    public void onError(ShareService shareService, int i, Throwable throwable)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onCancel(ShareService shareService, int i)
    {
        // TODO Auto-generated method stub
        
    }
    
}
