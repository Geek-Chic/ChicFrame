/**
 * @Title: TaskListener.java
 * @Package com.geekchic.base.mutitask
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 16, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.mutitask;

/**
 * @ClassName: TaskListener
 * @Descritpion: AsyncTask回调 
 * @author evil
 * @date May 16, 2014
 */
public abstract class TaskListener<Progress,Result> {
	/**
	 * 开始异步处理
	 */
   public void onStart(){};
   /**
    * 更新数据
    * @param values
    */
   public void onProgressUpdate(Progress... values){
   };
   /**
    * 成功返回结果
    * @param result
    */
   public abstract void onSuccess(Result result);
   /**
    * 错误
    * @param throwable
    */
   public abstract void onError(Throwable throwable);
   /**
    * 取消操作
    */
   public void onCancelled(){};
   /**
    * 结束
    */
   public void onFinish(){}
}
