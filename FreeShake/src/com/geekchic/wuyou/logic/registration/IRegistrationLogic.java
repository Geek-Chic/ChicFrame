/**
 * @Title: IRegistrationLogic.java
 * @Package com.geekchic.wuyou.logic.registration
 * @Description:注册Logic接口
 * @author: evil
 * @date: May 12, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.registration;

/**
 * @ClassName: IRegistrationLogic
 * @Descritpion: 注册Logic接口
 * @author evil
 * @date May 12, 2014
 */
public interface IRegistrationLogic  {
	/**
	 * 获取验证码请求
	 * @param phone
	 */
   void getCaptcha(String phone);
   /**
    * 注册
    * @param truename
    * @param phone
    * @param password
    * @param pushid
    * @param channelid
    */
   void register(String truename,String phone,String password,String pushid,String channelid);
  
}
