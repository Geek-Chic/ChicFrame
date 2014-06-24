/**
 * @Title: LogicBuilder.java
 * @Package com.geekchic.framework.logic
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import android.content.Context;
import android.os.Handler;

/**
 * @ClassName: LogicBuilder
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date Apr 30, 2014
 */
public abstract class BaseLogicBuilder implements ILogicBuilder {
    /**
     * 对所有logic进行管理的缓存
     */
    private Map<String, ILogic> mLogicCache = new HashMap<String, ILogic>();
    public BaseLogicBuilder(Context context){
    	init(context);
    	initAllLogics(context);
    }
	@Override
	public ILogic getLogic(Class<?> interfaceClass) {
		return mLogicCache.get(interfaceClass.getName());
	}

	@Override
	public void registerHandleToAllLogics(Handler handler) {
		  Set<Entry<String, ILogic>> logics = mLogicCache.entrySet();
	        for (Entry<String, ILogic> logicEntry : logics)
	        {
	            ILogic logic = logicEntry.getValue();
	            logic.addHandler(handler);
	        }
	}

	@Override
	public void removeAllHandlerRegister(Handler handler) {
		Set<Entry<String, ILogic>> logics = mLogicCache.entrySet();
        for (Entry<String, ILogic> logicEntry : logics)
        {
            ILogic logic = logicEntry.getValue();
            logic.removeHandler(handler);
        }
	}
	/**
	 * 注册Logic
	 * @param interfaceClass
	 * @param logic
	 */
	protected void registerLogic(Class<?> interfaceClass,ILogic logic){
		if(!mLogicCache.containsKey(interfaceClass.getName())){
			String interfaceName=interfaceClass.getName();
			Class<?> logicClass=logic.getClass();
			if(isInterface(logicClass, interfaceName) && isInterface(logicClass, ILogic.class.getName())){
				mLogicCache.put(interfaceName, logic);
			}
		}
	}
	/**
	 * 移除Logic
	 * @param interfaceClass
	 */
	public void removeLogic(Class<?> interfaceClass){
		mLogicCache.remove(interfaceClass.getName());
	}
	/**
	 * 初始化所有Logic
	 * @param context
	 */
   public void initAllLogics(Context context){
	   Set<java.util.Map.Entry<String,ILogic>> logics=mLogicCache.entrySet();
	   for(Entry<String,ILogic> logicEntry:logics){
		   logicEntry.getValue().init(context);
	   }
   }
   /**
    * 判断一个子类是否实现了接口<BR>
    * 
    * @param c
    *            子类类型
    * @param szInterface
    *            接口类名称
    * @return 是否实现了接口
    */
   private boolean isInterface(Class<?> c, String szInterface)
   {
       Class<?>[] face = c.getInterfaces();
       for (int i = 0, j = face.length; i < j; i++)
       {
           if (face[i].getName().equals(szInterface))
           {
               return true;
           }
           else
           {
               Class<?>[] face1 = face[i].getInterfaces();
               for (int x = 0; x < face1.length; x++)
               {
                   if (face1[x].getName().equals(szInterface))
                   {
                       return true;
                   }
                   else if (isInterface(face1[x], szInterface))
                   {
                       return true;
                   }
               }
           }
       }
       if (null != c.getSuperclass())
       {
           return isInterface(c.getSuperclass(), szInterface);
       }
       return false;
   }
   protected abstract void init(Context context);
}
