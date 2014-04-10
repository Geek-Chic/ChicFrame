package com.geekchic;

import com.geekchic.common.log.Logger;
import com.geekchic.constant.AppException;

import android.app.Application;

public class BaseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        //初始化日志
        Logger.configureLogbackDirectly(getApplicationContext());
        Logger.displayStatus();
        //初始化错误监听
        AppException.getInstance().init(getBaseContext());
    }
    
}
