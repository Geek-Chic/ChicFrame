/**
 * @Title: BasePairs.java
 * @Package com.geekchic.base.share
 * @Description: 分享参数
 * @author: Administrator
 * @date: 2014-7-1
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

/**
 * @ClassName: BasePairs
 * @Descritpion: 分享参数
 * @author jp
 * @date 2014-7-1
 */
public class BasePairs
{
    public BasePairs(String s, Object obj)
    {
        this.key = s;
        this.value = obj;
    }

    public String toString()
    {
        return (new StringBuilder()).append(key).append(" = ").append(value).toString();
    }
    public final String key;
    public final Object value;
    
}
