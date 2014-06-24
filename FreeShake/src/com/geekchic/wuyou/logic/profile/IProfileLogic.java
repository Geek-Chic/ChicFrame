/**
 * @Title: IProfileLogic.java
 * @Package com.geekchic.wuyou.logic.profile
 * @Description: 个人资料设置Logic接口
 * @author: evil
 * @date: May 31, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.profile;

/**
 * @ClassName: IProfileLogic
 * @Descritpion: 个人资料设置Logic 接口
 * @author evil
 * @date May 31, 2014
 */
public interface IProfileLogic {
	/**
	 * 上传头像
	 * @param path 头像路数
	 */
  public void updateAvator(String path);
  /**
   * 设置头像ID
   * @param avatorId
   */
  public void setAvator(String avatorId);
  /**
   * 检测APP更新
   */
  public void appUpdate();
}
