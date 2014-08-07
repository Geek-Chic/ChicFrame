package com.geekchic.base.share;

import android.content.Context;

public interface IShareClient
{
    /**
     * 验证获取授权用户信息
     * @param name
     */
    public void getUser(String name);
    
    
    public int getPlatformId();
    
    public void authorize(Context context);
}
