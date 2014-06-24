/**
 * @Title: Notify.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 12, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;


/**
 * @ClassName: Notify
 * @Descritpion: 通知实体类
 * @author evil
 * @date May 12, 2014
 */
public class Notify {
	/**
	 * 返回码
	 */
	public int code;
	/**
	 * 推送id
	 */
    public int id;
    /**
     * 推送种类
     */
    public int type;
    /**
     * 内容
     */
    public String content;
    /**
     * Url网址
     */
    public String url;
}
