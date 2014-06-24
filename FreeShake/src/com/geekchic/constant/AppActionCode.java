package com.geekchic.constant;

import android.R.integer;
import android.R.interpolator;


public interface AppActionCode {
	/**
	 * @ClassName: BaseMessageCode
	 * @Descritpion: 基础返回码
	 * @author evil
	 * @date May 4, 2014
	 */
	public interface BaseMessageCode{
		/**
		 * 基数
		 */
		int BASE=0x10000000;
		/**
		 * 网络错误
		 */
		int HTTP_ERROR=BASE+1;
	}
   public interface MainMessageCode{
	   /**
        * 此MainMessageCode的基数
        */
       int BASE = 0x11000000;
       /**
        * 登录
        */
       int MAIN_MESSAGE_LOGIN=BASE+1;
       /**
        * 主界面
        */
       int MAIN_MESSAGE_MAIN=BASE+2;
       /**
        * 导航界面
        */
       int MAIN_MESSAGE_NAV=BASE+3;
   }
   public interface LoginCode{
	   /**
	    * LoginCode基数
	    */
	   int BASE=0x12000000;
	   /**
	    * 登录成功
	    */
	   int MESSAGE_LOGIN_SUCCESS=BASE+1;
	   /**
	    * 登录失败
	    */
	   int MESSAGE_LOGIN_FAILED=BASE+2;
   }
   public interface ContactsCode{
	   /**
	    * ContactsCode基数
	    */
	   int BASE=0x13000000;
	   /**
	    * 请求数据库成功
	    */
	   int MESSAGE_CONSTACTS_PROVIDE_SUCCESS=BASE+1;
	   /**
	    * 清求数据库失败
	    */
	   int MESSAGE_CONSTACTS_PROVIDE_FAILED=BASE+2;
	   /**
	    * 本地检索成功
	    */
	   int MESSAGE_CONSTACTS_LOCAL_SEARCH_SUCCESS=BASE+3;
	   /**
	    * 本地检索失败
	    */
	   int MESSAGE_CONSTACTS_LOCAL_SEARCH_FAILED=BASE+4;
	   /**
	    * 同步成功
	    */
	   int MESSAGE_CONTACTS_SYNC_SUCCESS=BASE+5;
	   /**
	    * 同步失败
	    */
	   int MESSAGE_CONTACTS_SYNC_FAILED=BASE+6;
   }
   public interface RegistrationCode{
	   /**
	    * ContactsCode基数
	    */
	   int BASE=0x14000000;
	   /**
	    * 获取验证码成功
	    */
	   int AUTH_REQUEST_SUCCESS=BASE+1;
	   /**
	    * 获取验证码失败
	    */
	   int AUTH_REQUEST_FAILED=BASE+2;
	   /**
	    * 验证成功
	    */
	   int REGISTER_SUCCESS=BASE+3;
	   /**
	    * 验证失败
	    */
	   int REGISETER_FAILED=BASE+4;
   }
   public interface FeedBackCode{
	   /**
	    * ContactsCode基数
	    */
	   int BASE=0x15000000;
	   /**
	    * 成功
	    */
	   int FEED_BACK_SUCCESS=BASE+1;
	   /**
	    * 失败
	    */
	   int FEED_BACK_FAILED=BASE+2;
   }
   public interface ProfileCode{
	   /**
	    * ContactsCode基数
	    */
	   int BASE=0x15000000;
	   /**
	    * 上传头像成功
	    */
	   int PROFILE_AVATOR_SUCCESS=BASE+1;
	   /**
	    * 上传头像失败
	    */
	   int PROFILE_AVATOR_FAILED=BASE+2;
	   /**
	    * 设置头像ID成功
	    */
	   int PROFILE_AVATORID_SUCCESS=BASE+3;
	   /**
	    * 设置头像ID失败
	    */
	   int PROFILE_AVATORID_FAILED=BASE+4;
	   /**
	    * 检查更新获取成功
	    */
	   int PROFILE_UPDATE_SUCCESS=BASE+3;
	   /**
	    * 检查更新获取失败 
	    */
	   int PROFILE_UPDATE_FAILED=BASE+4;
   }
}
