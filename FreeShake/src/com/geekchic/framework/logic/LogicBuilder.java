/**
 * @Title: LogicBuilder.java
 * @Package com.geekchic.framework.logic
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.logic;

import com.geekchic.wuyou.logic.contacts.ContactsLogic;
import com.geekchic.wuyou.logic.contacts.IContactsLogic;
import com.geekchic.wuyou.logic.login.ILoginLogic;
import com.geekchic.wuyou.logic.login.LoginLogic;
import com.geekchic.wuyou.logic.registration.IRegistrationLogic;
import com.geekchic.wuyou.logic.registration.RegistrationLogic;

import android.content.Context;

/**
 * @ClassName: LogicBuilder
 * @Descritpion: LogicBuilder单例类
 * @author evil
 * @date Apr 30, 2014
 */
public class LogicBuilder extends BaseLogicBuilder {
	private static LogicBuilder sInstance = null;

	/**
	 * LogicBuilder构造函数
	 * 
	 * @param context
	 */
	private LogicBuilder(Context context) {
		super(context);
	}

	/**
	 * 单例模式
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized LogicBuilder getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new LogicBuilder(context);
		}
		return sInstance;
	}

	@Override
	protected void init(Context context) {
		registerAllLogics(context);
	}

	private void registerAllLogics(Context context) {
		registerLogic(ILoginLogic.class, new LoginLogic(context));
		registerLogic(IContactsLogic.class, new ContactsLogic(context));
		registerLogic(IRegistrationLogic.class, new RegistrationLogic(context));
	}

}
