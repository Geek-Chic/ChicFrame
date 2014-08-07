package com.geekchic.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import com.geekchic.common.log.Logger;

import android.content.Context;
import android.provider.Settings.Secure;

public class DeviceInfoUtil
{
    /**
     * TAG
     */
    private final static String TAG = "DeviceInfoUtil";
    
    /**
     * 单例对象
     */
    private static DeviceInfoUtil mInstance;
    
    /**
     * deviceID
     */
    private String mDeviceID;
    
    /**
     * Context
     */
    private Context mContext;
    
    /**
     * 构造器
     * 
     * @param context
     *            Context
     */
    public DeviceInfoUtil(Context context)
    {
        mContext = context;
        mDeviceID = getAndroidId();
        // TODO 目前只需要 device id 以后关于设备信息的往这个类里加。
    }
    
    /**
     * 单例 对象
     * 
     * @param context
     *            Context
     * @return DeviceInfoUtil
     */
    public static synchronized DeviceInfoUtil getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new DeviceInfoUtil(context);
        }
        return mInstance;
    }
    
    /**
     * 获得android设备唯一标识<BR>
     * 
     * @return deviceID
     */
    private String getAndroidId()
    {
        return Secure.getString(mContext.getContentResolver(),
                Secure.ANDROID_ID);
    }
    
    /**
     * 获得android设备唯一标识，android2.2 之前无法稳定运行.
     * 
     * @return deviceID
     */
    public String getDeviceID()
    {
        return mDeviceID;
    }
    
    /**
     * Get IP address from first non-localhost interface
     * 
     * @return address or empty string
     */
    public static String getIPAddress()
    {
        try
        {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces)
            {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs)
                {
                    if (!addr.isLoopbackAddress())
                    {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        return sAddr;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.d(TAG, "Error: " + e.toString());
        }
        return "";
    }
    
    /**
     * dip转px
     * 
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    
    /**
     * px转dip
     * 
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
