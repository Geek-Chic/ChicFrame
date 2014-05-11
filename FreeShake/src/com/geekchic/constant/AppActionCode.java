package com.geekchic.constant;


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
   }
}
