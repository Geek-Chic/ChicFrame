/**
 * @Title: ApiConnParams.java
 * @Package com.geekchic.base.share.dao
 * @Description: api操作
 * @author: evil
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.dao;

/**
 * @ClassName: ApiConnParams
 * @Descritpion: api操作
 * @author evil
 * @date 2014-7-2
 */
public class ApiConnParams extends ConnParams
{
    public int a;
    public String b;
    @Override
    protected String getParams()
    {
        return "[API]";
    }
    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(super.toString());
        stringbuilder.append('|').append(a);
        stringbuilder.append('|').append(b);
        return stringbuilder.toString();
    }
    
}
