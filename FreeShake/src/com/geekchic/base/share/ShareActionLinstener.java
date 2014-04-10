/**
 * @Title:ShareActionLinstener.java
 * @Package com.geekchic.base.share
 * @Description:分享监听回调
 * @author:jp
 * @date:2014-4-9
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.util.HashMap;

/**
 * @ClassName: ShareActionLinstener
 * @Descritpion:分享监听回调
 * @author jp
 * @date 2014-4-9
 */
public interface ShareActionLinstener
{
    public abstract void onComplete(ShareService shareService,int i,HashMap hashMap);
    public abstract void onError(ShareService shareService,int i,Throwable throwable);
    public abstract void onCancel(ShareService shareService,int i);
}
