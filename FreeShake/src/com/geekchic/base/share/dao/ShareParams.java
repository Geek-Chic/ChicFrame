/**
 * @Title: ShareParams.java
 * @Package com.geekchic.base.share.dao
 * @Description: 分享请求参数
 * @author: evil
 * @date: 2014-7-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.dao;

import java.util.ArrayList;

import android.text.TextUtils;

/**
 * @ClassName: ShareParams
 * @Descritpion: 分享请求参数
 * @author Evil
 * @date 2014-7-14
 */
public class ShareParams
{


    public ShareParams()
    {
        mKeys = new ArrayList();
        mValues = new ArrayList();
    }

    public void add(String key, String value)
    {
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value))
        {
            mKeys.add(key);
            mValues.add(value);
        }
    }

    public void add(String key, int value)
    {
        mKeys.add(key);
        mValues.add(String.valueOf(value));
    }

    public void add(String key, long value)
    {
        mKeys.add(key);
        mValues.add(String.valueOf(value));
    }

    public void remove(String key)
    {
        int firstIndex = mKeys.indexOf(key);
        if(firstIndex >= 0)
        {
            mKeys.remove(firstIndex);
            mValues.remove(firstIndex);
        }
    }

    public void remove(int i)
    {
        if(i < mKeys.size())
        {
            mKeys.remove(i);
            mValues.remove(i);
        }
    }

    private int getLocation(String key)
    {
        if(mKeys.contains(key))
            return mKeys.indexOf(key);
        else
            return -1;
    }

    public String getKey(int location)
    {
        if(location >= 0 && location < mKeys.size())
            return (String)mKeys.get(location);
        else
            return "";
    }

    public String getValue(String key)
    {
        int index = getLocation(key);
        if(index >= 0 && index < mKeys.size())
            return (String)mValues.get(index);
        else
            return null;
    }

    public String getValue(int location)
    {
        if(location >= 0 && location < mKeys.size())
        {
            String rlt = (String)mValues.get(location);
            return rlt;
        } else
        {
            return null;
        }
    }

    public int size()
    {
        return mKeys.size();
    }

    public void addAll(ShareParams parameters)
    {
        for(int i = 0; i < parameters.size(); i++)
            add(parameters.getKey(i), parameters.getValue(i));

    }

    public void clear()
    {
        mKeys.clear();
        mValues.clear();
    }

    private ArrayList mKeys;
    private ArrayList mValues;

}
