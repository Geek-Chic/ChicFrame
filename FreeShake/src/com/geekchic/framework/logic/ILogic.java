/**
 * @Title: ILogic.java
 * @Package com.geekchic.framework.logic
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.logic;

import android.content.Context;
import android.os.Handler;

/**
 * @ClassName: ILogic
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date Apr 30, 2014
 */
public interface ILogic {
	/**
	 * 初始化
	 * @param context
	 */
    public void init(Context context);
    /**
     * 添加监听的Handler
     * @param handler
     */
    public void addHandler(Handler handler);
    /**
     * 移除监听的Handler
     * @param handler
     */
    public void removeHandler(Handler handler);
}
