/**
 * @Title:ShareManager.java
 * @Package com.geekchic.base.share
 * @Description:Share管理类
 * @author:jp
 * @date:2014-4-9
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.test.AndroidTestRunner;

/**
 * @ClassName: ShareManager
 * @Descritpion:share管理类
 * @author jp
 * @date 2014-4-9
 */
public class ShareManager
{
    private ShareService mShareServices[];
    
    private Map<String, HashMap<String, String>> mAllParamsMap;
    
    private ArrayList mShareServiceLists;
    
    public static boolean flag = true;
    
    public ShareManager()
    {
        
    }
    public String getParam(Context context,String pKey){
        return getParam(context, "ShareSDK",pKey);
    }
   
    public ShareService[] init(Context context)
    {
        Context appContext = context.getApplicationContext();
        if (mShareServices != null)
        {
            return mShareServices;
        }
        ArrayList<ShareService> arrayList = null;
        if (mShareServiceLists != null && mShareServiceLists.size() > 0)
        {
            arrayList = initArrayList(appContext);
        }
        else
        {
            PackageInfo packageInfo = getPackageInfo(appContext);
            if (packageInfo == null)
            {
                return null;
            }
            mShareServiceLists = new ArrayList<ShareService>();
            arrayList = init(appContext, packageInfo);
        }
        mShareServices = init(arrayList);
        return mShareServices;
    }
    
    private ShareService[] init(ArrayList arrayList)
    {
        if (arrayList == null)
        {
            return null;
        }
        ArrayList tempArrayList = new ArrayList();
        do
        {
            if (tempArrayList.size() <= 0)
            {
                break;
            }
            int max = Integer.MAX_VALUE;
            int low = Integer.MIN_VALUE;
            int length = arrayList.size();
            for (int i = 0; i < length; i++)
            {
                ShareService service = (ShareService) arrayList.get(i);
                int sortID = service.getSortId();
                if (sortID < max)
                {
                    max = sortID;
                    low = i;
                }
            }
            if (low >= 0)
            {
                tempArrayList.add(arrayList.remove(low));
            }
        } while (true);
        arrayList = tempArrayList;
        int serviceSize = arrayList.size();
        ShareService services[] = new ShareService[serviceSize];
        for (int k = 0; k < serviceSize; k++)
        {
            services[k] = (ShareService) arrayList.get(k);
        }
        return services;
    }
    
    private ArrayList<ShareService> init(Context context,
            PackageInfo packageInfo)
    {
        ArrayList<ShareService> arrayList;
        Enumeration enumeration;
        if (flag)
        {
            return initBasicService(context);
        }
        arrayList = new ArrayList<ShareService>();
        enumeration = null;
        try
        {
            DexFile dexFile = new DexFile(packageInfo.applicationInfo.sourceDir);
            enumeration = dexFile.entries();
            try
            {
                dexFile.close();
            }
            catch (Throwable throwable1)
            {
                // TODO: handle exception
            }
        }
        catch (Throwable throwable)
        {
            // TODO: handle exception
            return null;
        }
        return arrayList;
    }
    
    /**
     * 设置每个分享的参数
     * @param context 上下文
     * @param shareName 分享模块名
     * @param key 参数key
     * @param value 参数value
     */
    public void setShareParam(Context context, String shareName, String key,
            String value)
    {
        if (mAllParamsMap == null)
        {
            mAllParamsMap = new HashMap<String, HashMap<String, String>>();
        }
        HashMap paramsMap = (HashMap) mAllParamsMap.get(shareName);
        if (paramsMap == null)
        {
            initParamsMap(context, shareName);
        }
        paramsMap = (HashMap<String, String>) mAllParamsMap.get(shareName);
        if (paramsMap == null)
        {
            paramsMap = new HashMap<String, String>();
            mAllParamsMap.put(shareName, paramsMap);
        }
        else
        {
            paramsMap.put(key, value);
        }
    }
    
    /**
     * 根据分享模块名，参数key获取value
     * @param context 上下文
     * @param shareName 模块名
     * @param key 参数key
     * @return value
     */
    public String getParam(Context context, String shareName, String key)
    {
        if (mAllParamsMap == null)
        {
            mAllParamsMap = new HashMap<String, HashMap<String, String>>();
        }
        HashMap<String, String> paramMap = mAllParamsMap.get(shareName);
        if (paramMap == null)
        {
            initParamsMap(context, shareName);
        }
        paramMap = mAllParamsMap.get(shareName);
        if (paramMap == null)
        {
            return null;
        }
        else
        {
            return paramMap.get(key);
        }
    }
    
    private void initParamsMap(Context context, String shareName)
    {
        HashMap<String, String> paramsHashMap = null;
        mAllParamsMap.put("", paramsHashMap);
    }
    
    private ArrayList<ShareService> initBasicService(Context context)
    {
        String as[] = { "cn.sharesdk.douban.Douban",
                "cn.sharesdk.evernote.Evernote",
                "cn.sharesdk.facebook.Facebook",
                "cn.sharesdk.netease.microblog.NetEaseMicroBlog",
                "cn.sharesdk.renren.Renren",
                "cn.sharesdk.sina.weibo.SinaWeibo",
                "cn.sharesdk.sohu.microblog.SohuMicroBlog",
                "cn.sharesdk.system.email.Email",
                "cn.sharesdk.system.text.ShortMessage",
                "cn.sharesdk.tencent.qzone.QZone",
                "cn.sharesdk.tencent.weibo.TencentWeibo",
                "cn.sharesdk.twitter.Twitter",
                "cn.sharesdk.wechat.friends.Wechat",
                "cn.sharesdk.wechat.moments.WechatMoments" };
        ArrayList arraylist = new ArrayList();
        String as1[] = as;
        for (int i = 0; i < as1.length; i++)
        {
            String shareClassName = as1[i];
            try
            {
                Class class1 = Class.forName(shareClassName);
                mShareServiceLists.add(class1);
                Constructor constructor = class1.getConstructor(new Class[] { Context.class });
                Object obj = constructor.newInstance(new Object[] { context });
                arraylist.add((ShareService) obj);
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
        }
        return arraylist;
    }
    
    private ArrayList initArrayList(Context context)
    {
        ArrayList arrayList = new ArrayList();
        for (Iterator iterator = mShareServiceLists.iterator(); iterator.hasNext();)
        {
            Class class1 = (Class) iterator.next();
            try
            {
                Constructor constructor = class1.getConstructor(new Class[] { Context.class });
                Object obj = constructor.newInstance(new Object[] { context });
                arrayList.add((ShareService) obj);
            }
            catch (NoSuchMethodException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return arrayList;
    }
    
    /**
     * 根据本应用上下文获取PackageInfo
     * @param context 上下文
     * @return PackageInfo
     */
    private PackageInfo getPackageInfo(Context context)
    {
        try
        {
            PackageManager packagemanager = context.getPackageManager();
            String pkgName = context.getPackageName();
            List list = packagemanager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            PackageInfo packageInfo = null;
            Iterator iterator = list.iterator();
            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }
                PackageInfo tempPackageInfo = (PackageInfo) iterator.next();
                if (!pkgName.equals(tempPackageInfo.packageName))
                {
                    continue;
                }
                packageInfo = tempPackageInfo;
                break;
            } while (true);
            return packageInfo;
        }
        catch (Exception e)
        {
            return null;
        }
        
    }
}
