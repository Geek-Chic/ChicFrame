/**
 * @Title: ImEventHandler.java
 * @Package com.geekchic.framework.service
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service;



/**
 * @ClassName: ImEventHandler
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date Apr 30, 2014
 */
public interface ImEventHandler {
    
	public abstract void onMessage(ImMessage message);
    
	public abstract void onBind(String method, int errorCode, String content);

	public abstract void onNotify(String title, String content);

	public abstract void onNetChange(boolean isNetConnected);


}
