/**
 * @Title: ShareWebActivity.java
 * @Package com.geekchic.base.share.ui.ui
 * @Description: Auth网页
 * @author: evil
 * @date: 2014-7-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui.ui;

import android.os.Bundle;

import com.geekchic.base.share.ui.RegisterView;
import com.geekchic.framework.ui.BaseFrameActivity;

/**
 * @ClassName: ShareWebActivity
 * @Descritpion: Auth网页
 * @author evil
 * @date 2014-7-14
 */
public class ShareWebActivity extends BaseFrameActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        RegisterView registerView=new RegisterView(this);
        setContentView(registerView);
    }
    
}
