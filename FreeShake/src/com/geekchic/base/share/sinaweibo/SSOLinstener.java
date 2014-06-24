/**
 * @Title: SSOLinstener.java
 * @Package com.geekchic.base.share.sinaweibo
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-4-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.sinaweibo;

import android.os.Bundle;

/**
 * @ClassName: SSOLinstener
 * @Descritpion: [用一句话描述作用] 
 * @author Administrator
 * @date 2014-4-14
 */
public interface SSOLinstener
{
    public abstract void onFailed(Throwable throwable);
    
    public abstract void onCancel();
    
    public abstract void onComplete(Bundle bundle);
    
}
