/**
 * @Title: UserInfo.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

/**
 * @ClassName: UserInfo
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date May 3, 2014
 */
public class UserInfo {
	public static final String TYPE_PHONE_MARK = "phone";
	public static final String TYPE_PASSWORD_MARK = "password";
	/**
	 * 用户ID
	 */
	private String uuid;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 用户名
	 */
	private String nickName;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 注册时间
	 */
	private String time;
	/**
	 * 验证状态
	 */
	private int status;

}
