/**
 * @Title: AppAction.java
 * @Package com.geekchic.constant
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.constant;

import android.R.interpolator;

/**
 * @ClassName: AppAction
 * @Descritpion: APP Action
 * @author evil
 * @date Apr 30, 2014
 */
public interface AppAction {

	/**
	 * @ClassName: LoginAction
	 * @Descritpion: 登录ACTION
	 * @author evil
	 * @date Apr 30, 2014
	 */
	public interface LoginAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.LOGIN";
	}

	/**
	 * @ClassName: RegisterAction
	 * @Descritpion: 注册Action
	 * @author evil
	 * @date May 3, 2014
	 */
	public interface RegisterAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.REGISTER";
	}

	/**
	 * @ClassName: MainAction
	 * @Descritpion: 主界面ACTION
	 * @author evil
	 * @date May 3, 2014
	 */
	public interface MainAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.MAIN";
	}
}
