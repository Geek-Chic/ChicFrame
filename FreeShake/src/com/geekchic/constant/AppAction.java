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

	/**
	 * @ClassName: NavAction
	 * @Descritpion: 使用导航
	 * @author evil
	 * @date May 4, 2014
	 */
	public interface NavAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.NAV";
	}

	/**
	 * @ClassName: ZXingAction
	 * @Descritpion:二维码
	 * @author evil
	 * @date May 6, 2014
	 */
	public interface ZXingAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.ZXING";
	}

	/**
	 * @ClassName: SettingAction
	 * @Descritpion: 设置界面
	 * @author evil
	 * @date May 9, 2014
	 */
	public interface SettingAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.SETTING";
	}

	/**
	 * @ClassName: ChatAction
	 * @Descritpion: 聊天ACTION
	 * @author evil
	 * @date May 9, 2014
	 */
	public interface ChatAction {
		/**
		 * ACTION
		 */
		public String ACTION = "com.geekchic.wuyou.CHAT";
	}
	/**
	 * @ClassName: ProjectCreateAction
	 * @Descritpion: 创建项目 
	 * @author evil
	 * @date May 10, 2014
	 */
	public interface ProjectCreateAction{
		/**
		 * ACTION
		 */
		public String ACTION="com.geekchic.wuyou.PROJECTCREATE";
	}
	/**
	 * @ClassName: ProfileSetting
	 * @Descritpion: 个人资料设置
	 * @author evil
	 * @date May 11, 2014
	 */
	public interface ProfileSetting{
		/**
		 * ACTION
		 */
		public String ACTION="com.geekchic.wuyou.PROFILESETTING";
	}
	/**
	 * @ClassName: About
	 * @Descritpion:关于
	 * @author evil
	 * @date May 17, 2014
	 */
	public interface About{
		/**
		 * ACTION
		 */
		public String ACTION="com.geekchic.wuyou.ABOUT";
	}
	/**
	 * @ClassName: FeekBack
	 * @Descritpion: 反馈
	 * @author evil
	 * @date May 17, 2014
	 */
	public interface FeekBack{
		/**
		 * ACTION
		 */
		public String ACTION="com.geekchic.wuyou.FEEDBACK";
	}
	/**
	 * @ClassName: Timer
	 * @Descritpion: 时间轴
	 * @author evil
	 * @date May 17, 2014
	 */
	public interface Timer{
		/**
		 * ACTION
		 */
		public String ACTION="com.geekchic.wuyou.TIMER";
	}
	/**
	 * @ClassName: Task
	 * @Descritpion: 添加Task
	 * @author evil
	 * @date May 19, 2014
	 */
	public interface Task{
		/**
		 * ACTION
		 */
		public String ACTION="com.geekchic.wuyou.TASK";
	}
	/**
	 * @ClassName: ShareAction
	 * @Descritpion:分享
	 * @author evil
	 * @date 2014-7-2
	 */
	public interface ShareAction{
	    /**
	     * ACTION
	     */
	    public String ACTION="com.geekchic.wuyou.SHARE";
	}
	/**
	 * @ClassName: AuthAction
	 * @Descritpion: 验证Action
	 * @author Evil
	 * @date 2014-7-2
	 */
	public interface AuthAction{
	    /**
         * ACTION
         */
        public String ACTION="com.geekchic.wuyou.AUTH";
	}
}
