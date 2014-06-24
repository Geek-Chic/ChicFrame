/**
 * @Title: ShareActivity.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: 社交绑定
 * @author: jp
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.geekchic.constant.AppAction;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ShareActivity
 * @Descritpion: 社交绑定
 * @author evil
 * @date 2014-7-2
 */
public class ShareActivity extends BaseTitleBarActivity implements OnClickListener
{
    private Button mAuthButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        mAuthButton=(Button) findViewById(R.id.btnLogin);
        mAuthButton.setOnClickListener(this);
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
        return R.layout.profile_share_main;
    }

    @Override
    public boolean initializeTitlBar()
    {
        setMiddleTitle("分享");
        setTitleBarBackground(R.color.blue);
        setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
        setTitleBarBackground(R.color.blue);
        return true;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                Intent bindIntent=new Intent(AppAction.AuthAction.ACTION);
                startActivity(bindIntent);
                break;
            
            default:
                break;
        }
    }
    

    
}
