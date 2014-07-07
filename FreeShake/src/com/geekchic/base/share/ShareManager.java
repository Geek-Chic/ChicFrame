/**
 * @Title:ShareManager.java
 * @Package com.geekchic.base.share
 * @Description:Share管理类
 * @author:jp
 * @date:2014-4-9
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.geekchic.base.share.dao.KeyInfo;
import com.geekchic.base.share.sinaweibo.SinaWeibo;
import com.geekchic.base.xml.SaxServiceHandle;
import com.geekchic.base.xml.XmlBean;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @ClassName: ShareManager
 * @Descritpion:share管理类
 * @author jp
 * @date 2014-4-9
 */
public class ShareManager
{
    //单例
    private static ShareManager sInstance;
    //分享对象管理
    private ShareService mShareServices[];
    //分享参数
    private Map<String, HashMap<String, String>> mAllParamsMap;
    //分享对象管理-列表
    private ArrayList<ShareService> mShareServiceLists;
    
    public static boolean flag = true;
    
    
    public static synchronized ShareManager getInstance(Context context)
    {
        if(null==sInstance){
            sInstance=new ShareManager(context);
        }
        return sInstance;
    }
     private ShareManager(Context context){
         mAllParamsMap=new HashMap<String, HashMap<String,String>>();
     }
    public ArrayList<ShareService> getShareService(Context context)
    {
        if(null==mShareServiceLists){
            parseXML(context);
        }
      return mShareServiceLists;
    }
    private  void parseXML(Context context){
        InputStream paramsStream;
        try
        {
            paramsStream = context.getResources().getAssets().open("shareservice.xml");
            SaxServiceHandle mSaxServiceHandle=new SaxServiceHandle();
            XmlBean rootBean=mSaxServiceHandle.getRootBean(paramsStream);
            for(XmlBean child:rootBean.getChildNode()){
                HashMap<String,String> paramMap=(HashMap<String, String>) child.getAttrMap();
                mAllParamsMap.put(child.getKey(), paramMap);
                ShareService service=null;
                if(child.getKey().equals(SinaWeibo.NAME)){
                   service=new SinaWeibo(context);
                  
                }else if(child.getKey().equals("TencentWeibo")){
                    
                }
                mShareServiceLists.add(service);
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
  
    
    /**
     * 设置每个分享的参数
     * @param shareName 分享模块名
     * @param key 参数key
     * @param value 参数value
     */
    public void setShareParam(String shareName, String key,
            String value)
    {
        if (mAllParamsMap == null)
        {
            mAllParamsMap = new HashMap<String, HashMap<String, String>>();
        }
        HashMap<String,String> paramsMap = (HashMap<String,String>) mAllParamsMap.get(shareName);
        if (paramsMap == null)
        {
            paramsMap=new HashMap<String, String>();
            paramsMap.put(key, value);
            mAllParamsMap.put(shareName, paramsMap);
        } else
        {
            paramsMap.put(key, value);
        }
    }
    
    /**
     * 根据分享模块名，参数key获取value
     * @param shareName 模块名
     * @param key 参数key
     * @return value
     */
    public String getParam(String shareName, String key)
    {
        if (mAllParamsMap == null)
        {
            mAllParamsMap = new HashMap<String, HashMap<String, String>>();
        }
        HashMap<String, String> paramMap = mAllParamsMap.get(shareName);
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
