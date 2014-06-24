package com.geekchic.wuyou;

/**
 * 全局参数定义
 * @author jp
 *
 */
public class GlobalEnv
{
    /**
     * 一些公共信息
     */
    public interface Common
    {
        /**
         * 程序保存shared preferences的名字
         */
        String SHARED_PREFERENCE_NAME = "WuYou";
        
        /**
         * shared preference 键 存储当前登录userId
         */
        String KEY_USER_ID = "userId";
        
        /**
         * shared preference 键 标识是否登录状态
         */
        String KEY_SESSION_ID = "sessionId";
    }
}
