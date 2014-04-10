/**
 * @Title:AppConfig.java
 * @Package com.geekchic.constant
 * @Description:应用全局配置文件
 * @author:jp
 * @date:2014-4-7
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.constant;


/**
 * @ClassName: AppConfig
 * @Descritpion:应用全局配置文件
 * @author jp
 * @date 2014-4-7
 */
public class AppConfig
{
    /**
     * 单例对象
     */
    private static AppConfig mInstance = new AppConfig();
    
    /**
     * 是否打印日志
     */
    private int loglevel = AppConstants.LOG_LEVEL_DEBUG;
    /**
     * 日志记录位置
     */
    private boolean isDebug=AppConstants.ISDEBUG_DEBUG_BUILD_VALUE;
    
    /**
     * AppConfig私有构造函数
     */
    private AppConfig()
    {
        
    }
    
    /**
     * 是否打印日志
     * @author jp 
     * @return AppConfig对象 
     */
    public static AppConfig getInstance()
    {
        return mInstance;
    }

    public int getLoglevel()
    {
        return loglevel;
    }

    public void setLoglevel(int loglevel)
    {
        this.loglevel = loglevel;
    }

    public boolean isDebug()
    {
        return isDebug;
    }

    public void setDebug(boolean isDebug)
    {
        this.isDebug = isDebug;
    }

   
//    /**
//     * 是否打印日志
//     * @author Administrator 
//     * @return isDebug
//     */
//    public boolean isDebug()
//    {
//        
//    }
//    
//    /**
//     * 是否打印日志
//     * @param isDebug
//     */
//    public void setDebug(BuildConfigValues isDebug)
//    {
//        this.isDebug=isDebug;
//    }

}
