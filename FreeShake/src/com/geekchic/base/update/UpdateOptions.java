/**
 * @Title: UpdateOptions.java
 * @Package com.geekchic.base.update
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 12, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.update;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: UpdateOptions
 * @Descritpion: 更新构造器
 * @author evil
 * @date Apr 12, 2014
 */
public class UpdateOptions
{
    public enum UpdateFormat
    {
        /**
         * xml格式
         */
        XML,
        
        /**
         * json格式
         */
        JSON
    }
    
    private final UpdateFrequent updateFrequent;
    
    private final boolean forceUpdate;
    
    private final boolean autoUpdate;
    
    private final boolean checkUpdate;
    
    private final boolean checkPackageName;
    
    private final String checkUrl;
    
    private final Context context;
    
    private final UpdateFormat updateFormat;
    
    // private final UpdateFormat updateFormat;
    
    private UpdateOptions(Builder builder)
    {
        this.updateFrequent = builder.updateFrequent;
        this.forceUpdate = builder.forceUpdate;
        this.checkUpdate = builder.checkUpdate;
        this.autoUpdate = builder.autoUpdate;
        this.checkPackageName = builder.checkPackageName;
        this.checkUrl = builder.checkURL;
        this.context = builder.context;
        this.updateFormat = builder.updateFormat;
        
    }
    
    /**
     * 判断更新时间
     * 
     * @return 是否检测更新
     */
    public boolean checkUpdateTest()
    {
        boolean checkMark = false;
        if (checkUpdate)
        {
            checkMark = true;
            return checkMark;
        }
        if (null == context)
        {
            checkMark = false;
            return checkMark;
        }
        long now = System.currentTimeMillis();
        SharedPreferences sp = context.getSharedPreferences(UpdateConfig.PREFS_NAME,
                Context.MODE_PRIVATE);
        long next = sp.getLong(UpdateConfig.PREFS_KEY_NEXT_CHECK_UPDATE_TIME,
                -1);
        if (-1 == next)
        {
            checkMark = false;
            return checkMark;
        }
        long frequent = 0;
        if (null != updateFrequent)
        {
            frequent = updateFrequent.getFrequentMillis();
        }
        else if (now + frequent >= next)
        {
            checkMark = true;
        }
        else
        {
            checkMark = false;
        }
        return checkMark;
    }
    
    public boolean shouldForceUpdate()
    {
        return forceUpdate;
    }
    
    public boolean shouldAutoUpdate()
    {
        return autoUpdate;
    }
    
    public boolean shouldCheckPackageName()
    {
        return checkPackageName;
    }
    
    public String getCheckURL()
    {
        return checkUrl;
    }
    
    public UpdateFrequent getUpdateFrequent()
    {
        return updateFrequent;
    }
    
    public UpdateFormat getUpdateFormat()
    {
        return updateFormat;
    }
    
    public static class Builder
    {
        private UpdateFrequent updateFrequent = new UpdateFrequent(
                UpdateFrequent.EACH_TIME);
        
        private boolean forceUpdate = false;
        
        private boolean autoUpdate = false;
        
        private String checkURL = null;
        
        private Context context = null;
        
        private boolean checkUpdate = false;
        
        private boolean checkPackageName = true;
        
        private UpdateFormat updateFormat = UpdateFormat.XML;
        
        public Builder(Context context)
        {
            this.context = context.getApplicationContext();
        }
        
        public Builder udpateFrequent(UpdateFrequent updateFrequent)
        {
            this.updateFrequent = updateFrequent;
            return this;
        }
        
        public Builder forceUpdate(boolean forceUpdate)
        {
            this.forceUpdate = forceUpdate;
            return this;
        }
        
        public Builder autoUpdate(boolean autoUpdate)
        {
            this.autoUpdate = autoUpdate;
            return this;
        }
        
        public Builder checkUpdate(String checkURL)
        {
            this.checkURL = checkURL;
            return this;
        }
        
        public Builder checkPackageName(boolean checkPackageName)
        {
            this.checkPackageName = checkPackageName;
            return this;
        }
        
        public Builder checkUpdateFormat(UpdateFormat format)
        {
            this.updateFormat = format;
            return this;
        }
        
        public UpdateOptions build()
        {
            return new UpdateOptions(this);
        }
    }
}
