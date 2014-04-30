/**
 * @Title: LoginLogic.java
 * @Package com.geekchic.wuyou.logic.login
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.login;

import android.content.Context;

import com.geekchic.framework.logic.BaseLogic;

/**
 * @ClassName: LoginLogic
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date Apr 30, 2014
 */
public class LoginLogic extends BaseLogic implements ILoginLogic{

	private Context mContext;
	public LoginLogic(Context context){
		this.mContext=context;
	}
	@Override
	public void login(String userAccount, String passwd) {
		sendMessage(1, "login");
	}

	@Override
	public void logout() {
		sendMessage(2, "logout");
	}

}
