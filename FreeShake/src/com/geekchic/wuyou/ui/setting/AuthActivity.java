/**
 * @Title: AuthActivity.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: 用户授权登录
 * @author: Evilester
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import java.util.HashMap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;

import com.geekchic.base.share.ShareActionLinstener;
import com.geekchic.base.share.ShareService;
import com.geekchic.base.share.sinaweibo.SinaWeibo;
import com.geekchic.base.share.ui.WeiboGridView;
import com.geekchic.common.utils.StringUtils;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: AuthActivity
 * @Descritpion: 用户授权登录
 * @author Evilester
 * @date 2014-7-2
 */
public class AuthActivity extends BaseTitleBarActivity implements OnClickListener,ShareActionLinstener,Callback
{
    private CheckedTextView sinaCheckedTextView;
    private CheckedTextView qzoneCheckedTextView;
    private CheckedTextView txCheckedTextView;
    private CheckedTextView renrenCheckedTextView;
    
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ShareService.initSDK(this);
        initView();
        initData();
    }
    /**
     * 初始化视图
     */
    private void initView(){
        //授权消息回调接口监听
        mHandler=new Handler(this);
        
        sinaCheckedTextView=(CheckedTextView)findViewById(R.id.ctvSw);
        qzoneCheckedTextView=(CheckedTextView) findViewById(R.id.ctvQz);
        txCheckedTextView=(CheckedTextView) findViewById(R.id.ctvTc);
        renrenCheckedTextView=(CheckedTextView) findViewById(R.id.ctvRr);
        
    }
    /**
     * 初始化数据
     */
    private void initData(){
        sinaCheckedTextView.setOnClickListener(this);
        qzoneCheckedTextView.setOnClickListener(this);
        txCheckedTextView.setOnClickListener(this);
        renrenCheckedTextView.setOnClickListener(this);
    }
    /**
     * 后退
     */
    private OnClickListener mBackClickListener=new OnClickListener(){

        @Override
        public void onClick(View v) {
            finishActivity();
        }
        
    };
    @Override
    public int getLayoutId()
    {
        return R.layout.profile_share_auth;
    }

    @Override
    public boolean initializeTitlBar()
    {
        setMiddleTitle("授权");
        setTitleBarBackground(R.color.blue);
        setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
        setTitleBarBackground(R.color.blue);
        return true;
    }
    private ShareService getShareService(int vid){
        String name=null;
        switch (vid)
        {
            case R.id.ctvSw:
                name=SinaWeibo.NAME;
                break;
            
            default:
                break;
        }
        if(!StringUtils.isNullOrEmpty(name)){
            return ShareService.getShareService(this, name);
        }
        return null;
    }
    @Override
    public void onClick(View v)
    {
        ShareService shareService=new SinaWeibo(this);
        CheckedTextView ctv=(CheckedTextView) v;
        if(null==shareService){
            ctv.setChecked(false);
            ctv.setText(R.string.not_yet_authorized);
            return;
        }
        if(shareService.isValid()){
            shareService.removeAccount();
            ctv.setChecked(false);
            ctv.setText(R.string.not_yet_authorized);
            return;
        }
        shareService.setShareServiceActionLinstener(this);
        shareService.showUser(null);
    }
    @Override
    public void onComplete(ShareService shareService, int i, HashMap hashMap)
    {
        // TODO Auto-generated method stub
        
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
    @Override
    public boolean handleMessage(Message msg)
    {
        return false;
    }
    
}
