/**
 * @Title: AuthorizeActivity.java
 * @Package com.geekchic.base.share.ui
 * @Description: 验证授权Activity
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.geekchic.common.utils.StringUtils;

/**
 * @ClassName: AuthorizeActivity
 * @Descritpion: 验证授权Activity
 * @author Administrator
 * @date 2014-6-26
 */
public class AuthorizeActivity extends Activity
{
    private static Handler mAuthorHandle;
    private static HashMap<String,AuthorizeActivityListener> mHashMap=new HashMap<String, AuthorizeActivity.AuthorizeActivityListener>();
    private String mShareName;
    public static interface AuthorizeActivityListener
    {

        public abstract void onCreate(AuthorizeActivity authorizeactivity, AuthorizeAdapter authorizeadapter);

        public abstract void onActivityResult(AuthorizeActivity authorizeactivity, int i, int j, Intent intent);

        public abstract boolean onKeyEvent(int i, KeyEvent keyevent);

        public abstract void onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(null==mAuthorHandle){
            mAuthorHandle=new Handler();
            mShareName=getIntent().getStringExtra("name");
            if(StringUtils.isNullOrEmpty(mShareName)){
                finish();
                return;
            }
            AuthorizeActivityListener authorizeActivityListener=mHashMap.get(mShareName);
            if(authorizeActivityListener!=null){
                AuthorizeAdapter authorizeAdapter=getAdapterByClass();
                if(null==authorizeAdapter){
                    authorizeAdapter=new AuthorizeAdapter();
                    authorizeAdapter.setNotitle(isNotitle());
                }
               authorizeAdapter.setActivity(this);
               authorizeActivityListener.onCreate(this, authorizeAdapter);
            }
        }
    }
    private boolean isNotitle(){
        boolean flag=false;
        try
        {
            ActivityInfo activityinfo = getPackageManager().getActivityInfo(getComponentName(), 128);
            flag = activityinfo.metaData.getBoolean("NoTitle");
        }
        catch(Throwable throwable)
        {
            flag = false;
        }
        return flag;
    }
    private AuthorizeAdapter getAdapterByClass(){
        String s;
        Class class1;
        Object obj;
        try
        {
            ActivityInfo activityinfo = getPackageManager().getActivityInfo(getComponentName(), 128);
            s = activityinfo.metaData.getString("Adapter");
            if(s == null || s.length() <= 0)
                return null;
            class1 = Class.forName(s);
            obj = class1.newInstance();
            if(!(obj instanceof AuthorizeAdapter))
                return null;
            return (AuthorizeAdapter)obj;
        }
        catch(Throwable throwable)
        {
            return null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        AuthorizeActivityListener authorizeActivityListener=mHashMap.get(mShareName);
        if(authorizeActivityListener!=null){
            authorizeActivityListener.onActivityResult(this, requestCode, resultCode, data);
        }
    }
    @Override
    public void finish()
    {
        if(mShareName != null)
        {
            AuthorizeActivityListener authorizeactivitylistener = (AuthorizeActivityListener)mHashMap.remove(mShareName);
            if(authorizeactivitylistener != null)
                authorizeactivitylistener.onDestroy();
        }
        super.finish();
    }
    public static void addAuthorizeListener(String share,AuthorizeActivityListener authorizeActivityListener){
        mHashMap.put(share, authorizeActivityListener);
    }
    public boolean onKeyDown(int keyCode,KeyEvent keyEvent){
        boolean flag = false;
        AuthorizeActivityListener authorizeactivitylistener = (AuthorizeActivityListener)mHashMap.get(mShareName);
        if(authorizeactivitylistener != null)
            flag = authorizeactivitylistener.onKeyEvent(keyCode, keyEvent);
        return flag ? true : super.onKeyDown(keyCode, keyEvent);
    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean flag = false;
        AuthorizeActivityListener authorizeactivitylistener = (AuthorizeActivityListener)mHashMap.get(mShareName);
        if(authorizeactivitylistener != null)
            flag = authorizeactivitylistener.onKeyEvent(keyCode, event);
        return flag ? true : super.onKeyUp(keyCode, event);
    };
}
