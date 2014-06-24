/**
 * @Title: SharePreferenceUtils.java
 * @Package com.geekchic.base.share
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-4-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.PerformanceTestCase;

/**
 * @ClassName: SharePreferenceUtils
 * @Descritpion: 保存分享参数
 * @author Administrator
 * @date 2014-4-14
 */
public class SharePreferenceUtils
{
    private static final String DB_NAME = "cn_sharesdk_weibodb";
    
    private SharedPreferences preferences;
    
    private String weiboNname;
    
    private int weiboVersion;
    
    /**
     * SharePreferenceUtils构造函数
     * @param context Context
     * @param share Share Name
     * @param version Version
     */
    public SharePreferenceUtils(Context context, String share, int version)
    {
        String shareFile = (new StringBuilder()).append("cn_sharesdk_weibodb")
                .append(share)
                .append("-")
                .append(version)
                .toString();
        preferences = context.getSharedPreferences(shareFile,
                Context.MODE_PRIVATE);
        weiboNname = share;
        weiboVersion = version;
    }
    
    public void put(String shareKey, String value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(shareKey, value);
        editor.commit();
    }
    
    public String get(String shareKey)
    {
        return preferences.getString(shareKey, "");
    }
    
    public String getToken()
    {
        return preferences.getString("token", "");
    }
    
    public void putToken(String token)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }
    
    public String getTokenSecret()
    {
        return preferences.getString("secret", "");
    }
    
    public void putTokenSecret(String s)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("secret", s);
        editor.commit();
    }
    public long getExpiresIn(){
        long l = 0L;
        try
        {
            l = preferences.getLong("expiresIn", 0L);
        } catch(Throwable throwable)
        {
            try
            {
                l = preferences.getInt("expiresIn", 0);
            }
            catch(Throwable throwable1)
            {
                l = 0L;
            }
        }
        return l;
    }
    public void putExpiresIn(long l)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("expiresIn", l);
        editor.putLong("expiresTime", System.currentTimeMillis());
        editor.commit();
    }
    public long getExpiresTime()
    {
        long expiresTime = preferences.getLong("expiresTime", 0L);
        long expiresIn = getExpiresIn();
        return expiresTime + expiresIn * 1000L;
    }

    public int getWeiboVersion()
    {
        return weiboVersion;
    }
    public String getWeiboNname()
    {
        return weiboNname;
    }
    public void putWeiboId(String s)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("weibo", s);
        editor.commit();
    }
    public String getWeiboId()
    {
        return preferences.getString("weibo", "");
    }
    public void removeAccount()
    {
        ArrayList arraylist = new ArrayList();
        java.util.Map.Entry entry;
        for(Iterator iterator = preferences.getAll().entrySet().iterator(); iterator.hasNext(); arraylist.add(entry.getKey()))
            entry = (java.util.Map.Entry)iterator.next();

        SharedPreferences.Editor editor = preferences.edit();
        String s;
        for(Iterator iterator1 = arraylist.iterator(); iterator1.hasNext(); editor.remove(s))
            s = (String)iterator1.next();

        editor.commit();
    }
    /**
     * 从Preference导出为json字符串
     * @return
     */
    public String exportData()
    {
        try
        {
            HashMap hashmap = new HashMap();
            hashmap.putAll(preferences.getAll());
            return (new NetJsonParser()).packJsonStr(hashmap);
        }
        catch(Throwable throwable)
        {
            return null;
        }
    }
    /**
     * 将网络同步的数据存入Preference
     * @param json json数据
     */
    public void importData(String json)
    {
        try
        {
            HashMap hashmap = (new NetJsonParser()).parserJson(json);
            if(hashmap != null)
            {
                SharedPreferences.Editor editor = preferences.edit();
                for(Iterator iterator = hashmap.entrySet().iterator(); iterator.hasNext();)
                {
                    java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                    Object obj = entry.getValue();
                    if(obj instanceof Boolean)
                        editor.putBoolean((String)entry.getKey(), ((Boolean)obj).booleanValue());
                    else
                    if(obj instanceof Float)
                        editor.putFloat((String)entry.getKey(), ((Float)obj).floatValue());
                    else
                    if(obj instanceof Integer)
                        editor.putInt((String)entry.getKey(), ((Integer)obj).intValue());
                    else
                    if(obj instanceof Long)
                        editor.putLong((String)entry.getKey(), ((Long)obj).longValue());
                    else
                        editor.putString((String)entry.getKey(), String.valueOf(obj));
                }

                editor.commit();
            }
        }
        catch(Throwable throwable) { }
    }
    /**
     * 授权是否到期
     * @return isValid
     */
    public boolean isValid()
    {
        String s = getToken();
        if(s == null || s.length() <= 0)
            return false;
        if(getExpiresIn() == 0L)
            return true;
        else
            return getExpiresTime() > System.currentTimeMillis();
    }
}
