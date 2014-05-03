package com.geekchic.constant;

import android.R.interpolator;

public interface AppActionCode {
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
}
