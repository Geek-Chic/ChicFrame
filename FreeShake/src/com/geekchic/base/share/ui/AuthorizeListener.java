/**
 * @Title: AuthorizeListener.java
 * @Package com.geekchic.base.share.ui
 * @Description: 认证监听
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import android.content.Context;
import android.os.Bundle;

/**
 * @ClassName: AuthorizeListener
 * @Descritpion: 认证监听
 * @author Administrator
 * @date 2014-6-26
 */
public abstract class AuthorizeListener
{
    public abstract void onComplete(Bundle bundle);

    public abstract void onError(Throwable throwable);

    public abstract void onCancel();

    public abstract Context getContext();

    public abstract void startAuthorize();
}
