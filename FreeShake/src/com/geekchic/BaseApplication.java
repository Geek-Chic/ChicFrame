package com.geekchic;

import android.app.Application;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.PreferencesUtils;
import com.geekchic.constant.AppException;

public class BaseApplication extends Application
{
	public LocationClient mLocationClient = null;
	public GeofenceClient mGeofenceClient;
    @Override
    public void onCreate()
    {
        super.onCreate();
        //初始化日志
        Logger.configureLogbackDirectly(getApplicationContext());
//        Logger.displayStatus();
        //初始化错误监听
        AppException.getInstance().init(getBaseContext());
        //初始化Preference
        PreferencesUtils.initContext(getApplicationContext());
        //初始化百度云推
        FrontiaApplication.initFrontiaApplication(getApplicationContext());
        //初始化百度定位
        mLocationClient = new LocationClient( this );
        mLocationClient.setAK(PreferencesUtils.getMetaValue(this, "api_key"));
        mGeofenceClient = new GeofenceClient(this);
    }
    
}
