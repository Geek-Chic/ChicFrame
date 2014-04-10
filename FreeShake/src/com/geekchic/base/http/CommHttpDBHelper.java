package com.geekchic.base.http;

import android.content.Context;

import com.geekchic.base.db.BaseDBHelper;

public class CommHttpDBHelper extends BaseDBHelper{
    private static final String DBNAME = "comhttp.db";// 数据库名
    private static final int DBVERSION = 1;
    private static final Class<?>[] clazz = { CommHttpURL.class };// 反射类
    public CommHttpDBHelper(Context context)
    {
        super(context, DBNAME, null, DBVERSION, clazz);
    }
    
}

