/**
 * @Title: IFeedbackLogic.java
 * @Package com.geekchic.wuyou.logic.feedback
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.feedback;

/**
 * @ClassName: IFeedbackLogic
 * @Descritpion: 反馈Logic
 * @author evil
 * @date May 17, 2014
 */
public interface IFeedbackLogic {
	/**
	 * 发送反馈消息
	 * @param uuid
	 * @param message
	 */
   public void setFeedBack(String uuid,String message);
}
