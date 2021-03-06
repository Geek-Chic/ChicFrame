package com.geekchic.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.geekchic.freeshake.GlobalEnv.Common;


public class PreferencesUtil
{
    
    /**
     * PreferencesUtil单例对象
     */
    private static PreferencesUtil mInstance;
    
    /**
     * Context对象
     */
    private static Context mContext;
    
    /**
     * SharedPreferences对象
     */
    private static SharedPreferences mSharedPreferences;
    
    /**
     * 构造器
     */
    private PreferencesUtil()
    {
        if (mContext == null)
        {
            throw new NullPointerException(
                    "Android Context  is null,Please initialization!");
        }
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static SharedPreferences getSharedPreferences()
    {
        if (mSharedPreferences != null)
        {
            return mSharedPreferences;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            mSharedPreferences = mContext.getSharedPreferences(Common.SHARED_PREFERENCE_NAME,
                    Context.MODE_MULTI_PROCESS); // Context.MODE_MULTI_PROCESS
        }
        else
        {
            mSharedPreferences = mContext.getSharedPreferences(Common.SHARED_PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }
    
    /**
     * 清除缓存，重新读取配置文件
     */
    public static void clearCache()
    {
        mSharedPreferences = null;
    }
    
    /**
     * 获取PreferencesUtil单例对象<BR>
     * @return 返回PreferencesUtil对象
     */
    private static PreferencesUtil getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new PreferencesUtil();
        }
        return mInstance;
    }
    
    /**
     * 使用PreferencesUtil前需要在Application启动（onCreate）时调用initContext
     * @param context Context对象
     */
    public static void initContext(Context context)
    {
        mContext = context;
    }
    
    /**
     * 设置参数的KEY和Value
     * @param key 键
     * @param obj 值
     */
    public static void setAttr(String key, Object obj)
    {
        if (obj instanceof String)
        {
            String value = (String) obj;
            getInstance().setStringAttr(key, value);
        }
        else if (obj instanceof Boolean)
        {
            Boolean value = (Boolean) obj;
            getInstance().setBooleanAttr(key, value);
        }
        else if (obj instanceof Integer)
        {
            Integer value = (Integer) obj;
            getInstance().setIntAttr(key, value);
        }
        else if (obj instanceof Long)
        {
            Long value = (Long) obj;
            getInstance().setLongAttr(key, value);
        }
    }
    
    /**
     * 获取参数的String值
     * @param key 键
     * @return 获取String的字符串
     */
    public static String getAttrString(String key)
    {
        return getInstance().getStringAttr(key);
    }
    
    /**
     * 根据Key移除相应的值<BR>
     * @param key 存储的key
     */
    public static void removeAttrString(String key)
    {
        getInstance().removeStringAttr(key);
    }
    
    /**
     * 获取参数的Boolean值
     * @param key 存储的key
     * @return 获取该key的布尔值
     */
    public static boolean getAttrBoolean(String key)
    {
        return getInstance().getBooleanAttr(key);
    }
    
    /**
     * 获取参数的Int值
     * @param key 存储的Key值
     * @return 返回该key的Int值
     */
    public static int getAttrInt(String key)
    {
        return getInstance().getIntAttr(key);
    }
    
    /**
     * 根据Key移除相应的值<BR>
     * @param key 存储的Key值
     */
    public static void removeAttrInt(String key)
    {
        getInstance().removeIntAttr(key);
    }
    
    /**
     * 获取参数的Long值
     * @param key 存储的Key值
     * @return 返回该Key的long值
     */
    public static long getAttrLong(String key)
    {
        return getInstance().getLongAttr(key);
    }
    
    /**
     * 判断SP文件中是都存在key值
     * @param key 存储的Key值
     * @return 是否存在对应的key值
     */
    public static boolean containsKey(String key)
    {
        return getInstance().contains(key);
    }
    
    private boolean contains(String key)
    {
        return getSharedPreferences().contains(key);
    }
    
    private void setStringAttr(String key, String value)
    {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        if (value == null)
        {
            edit.remove(key);
        }
        else
        {
            edit.putString(key, value);
        }
        edit.commit();
    }
    
    private String getStringAttr(String key)
    {
        return getSharedPreferences().getString(key, "");
    }
    
    private void removeStringAttr(String key)
    {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String value = getStringAttr(key);
        if (value != null)
        {
            edit.remove(key);
        }
        edit.commit();
    }
    
    private void setBooleanAttr(String key, boolean value)
    {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    
    private boolean getBooleanAttr(String key)
    {
        return getSharedPreferences().getBoolean(key, false);
        
    }
    
    private void setIntAttr(String key, int value)
    {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putInt(key, value);
        edit.commit();
    }
    
    private int getIntAttr(String key)
    {
        return getSharedPreferences().getInt(key, -1);
    }
    
    private void removeIntAttr(String key)
    {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        int value = getIntAttr(key);
        if (value != -1)
        {
            edit.remove(key);
        }
        edit.commit();
    }
    
    private void setLongAttr(String key, long value)
    {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putLong(key, value);
        edit.commit();
    }
    
    private long getLongAttr(String key)
    {
        return getSharedPreferences().getLong(key, -1);
    }
    
}
