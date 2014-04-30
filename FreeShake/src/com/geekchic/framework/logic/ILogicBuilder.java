/**
 * @Title: ILogicBuilder.java
 * @Package com.geekchic.framework.logic
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.logic;

import android.os.Handler;

/**
 * @ClassName: ILogicBuilder
 * @Descritpion: Logic管理接口 
 * @author evil
 * @date Apr 30, 2014
 */
public interface ILogicBuilder {
	/**
	 * 获取Logic对象
	 * @param interfaceClass
	 * @return Logic实例
	 */
  public ILogic getLogic(Class<?> interfaceClass);
  /**
   * 添加Handler
   * @param handler
   */
  public void addHandleToLogics(Handler handler);
  /**
   * 移除Handler
   * @param handler
   */
  public void  removeHandlerFromLogics(Handler handler);
}
