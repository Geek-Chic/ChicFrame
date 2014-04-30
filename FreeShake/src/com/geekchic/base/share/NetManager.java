package com.geekchic.base.share;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
/**
 * @ClassName: NetManager
 * @Descritpion:网络工具类
 * @author jp
 * @date 2014-4-10
 */
public class NetManager
{
    /**
     * 上下文
     */
    private Context mContext;
    
    /**
     * 网络管理
     */
    private static NetManager mNetManager;
    
    public NetManager(Context context)
    {
        mContext = context.getApplicationContext();
    }
    
    public static NetManager getInstance(Context context)
    {
        if (mNetManager == null)
        {
            mNetManager = new NetManager(context);
        }
        return mNetManager;
    }
    
    /**
     * 获取手机MAC值
     * @return MAC值
     */
    public String getMacAddress()
    {
        
        WifiManager wifimanager = (WifiManager) mContext.getSystemService("wifi");
        if (wifimanager == null)
            return null;
        WifiInfo wifiinfo = wifimanager.getConnectionInfo();
        if (wifiinfo != null)
        {
            String s = wifiinfo.getMacAddress();
            return s != null ? s : null;
        }
        else
        {
            return null;
        }
        
    }
    
    /**
     * 获取设备ID
     * 从Android 2.3（“姜饼”）开始，通过android.os.Build.SERIAL方法序列号可被使用。没有电话功能的设备也都需要上给出唯一的设备ID;
     * @return 设备ID
     */
    public String getDeviceID()
    {
        TelephonyManager telephonymanager = (TelephonyManager) mContext.getSystemService("phone");
        if (telephonymanager == null)
            return null;
        String s = telephonymanager.getDeviceId();
        String s1 = "";
        if (s != null)
        {
            s1 = new String(s);
            s1 = s1.replace("0", "");
        }
        if ((s == null || s1.length() <= 0)
                && android.os.Build.VERSION.SDK_INT >= 9)
            try
            {
                Class class1 = Class.forName("android.os.SystemProperties");
                Method method = class1.getMethod("get", new Class[] {
                        String.class, String.class });
                s = (String) (String) method.invoke(class1, new Object[] {
                        "ro.serialno", "unknown" });
            }
            catch (Throwable throwable)
            {
                //                cn.sharesdk.framework.i.a(throwable);
                s = null;
            }
        return s;
        
    }
    
    /**
     * 获取网络类型
     * @return 网络类型
     */
    public String getNetworkType()
    {
        ConnectivityManager connectivitymanager = (ConnectivityManager) mContext.getSystemService("connectivity");
        if (connectivitymanager == null)
            return null;
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable())
            return null;
        int netType = networkinfo.getType();
        if (ConnectivityManager.TYPE_WIFI == netType)
            return "wifi";
        if (ConnectivityManager.TYPE_MOBILE == netType)
        {
            String s = android.net.Proxy.getDefaultHost();
            String s1 = "";
            if (s != null && s.length() > 0)
                s1 = " wap";
            return (new StringBuilder()).append(is3G() ? "3G" : "2G")
                    .append(s1)
                    .toString();
        }
        else
        {
            return null;
        }
        
    }
    
    /**
     * 是否3G网络
     * @return 是否3G网络
     */
    private boolean is3G()
    {
        TelephonyManager telephonymanager = (TelephonyManager) mContext.getSystemService("phone");
        if (telephonymanager == null)
            return false;
        switch (telephonymanager.getNetworkType())
        {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0: //电信3G
            case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3G
            case TelephonyManager.NETWORK_TYPE_HSDPA: //联通3G
            case TelephonyManager.NETWORK_TYPE_HSUPA: //联通3G
            case TelephonyManager.NETWORK_TYPE_UMTS: //联通3G
                return true;
            default:
                return false;
        }
    }
    /**
     * 获取包名
     * @return 包名
     */
    public String getPackageName()
    {
        return mContext.getPackageName();
    }
    
    public int getVersionCode()
    {
        
        try
        {
            PackageManager packagemanager = mContext.getPackageManager();
            PackageInfo packageinfo = packagemanager.getPackageInfo(mContext.getPackageName(),
                    0);
            return packageinfo.versionCode;
        }
        catch (Throwable throwable)
        {
        }
        return 0;
        
    }
}
