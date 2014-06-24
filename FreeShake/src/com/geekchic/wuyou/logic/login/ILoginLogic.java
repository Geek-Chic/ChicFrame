/**
 * @Title: ILoginLogic.java
 * @Package com.geekchic.wuyou.logic.login
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.login;

/**
 * @ClassName: ILoginLogic
 * @Descritpion: 登录操作
 * @author evil
 * @date Apr 30, 2014
 */
public interface ILoginLogic {
	/**
	 * 登录
	 * @param username
	 * @param password
	 */
	void login(String username, String password);
	/**
	 * 登出
	 */
    void logout();
}
