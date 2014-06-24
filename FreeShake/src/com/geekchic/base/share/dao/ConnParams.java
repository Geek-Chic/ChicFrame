/**
 * @Title: ConnParams.java
 * @Package com.geekchic.base.share
 * @Description: 请求参数 
 * @author: evil
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.dao;

/**
 * @ClassName: ConnParams
 * @Descritpion: 请求参数 
 * @author evil
 * @date 2014-7-2
 */
public abstract class ConnParams
{
    public long d;
    public String mNetTypeString;
    String f;
    String g;
    int h;
    String mPackageNameString;
    int mVersionCode;
    String k;
    protected abstract String getParams();
    @Override
    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(getParams()).append(':');
        stringbuilder.append(d).append('|');
        stringbuilder.append(mNetTypeString).append('|');
        stringbuilder.append(f).append('|');
        stringbuilder.append(g).append('|');
        stringbuilder.append(h).append('|');
        stringbuilder.append(mPackageNameString).append('|');
        stringbuilder.append(mVersionCode).append('|');
        stringbuilder.append(k);
        return stringbuilder.toString();
    }
}
