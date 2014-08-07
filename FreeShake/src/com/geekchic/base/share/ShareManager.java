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
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.geekchic.base.share.dao.ShareData;
import com.geekchic.base.share.othershare.MailShare;
import com.geekchic.base.share.othershare.SmsShare;
import com.geekchic.base.share.sinaweibo.SinaWeibo;
import com.geekchic.base.share.ui.ShareListener;
import com.geekchic.base.share.ui.ui.ListPopup;
import com.geekchic.base.share.ui.ui.ShareBasePopupWindow;
import com.geekchic.base.share.ui.ui.ViewPagerPopup;
import com.geekchic.base.share.ui.ui.ShareViewType;
import com.geekchic.base.share.ui.ui.ShareBasePopupWindow.OnShareClickListener;
import com.geekchic.base.xml.SaxServiceHandle;
import com.geekchic.base.xml.XmlBean;

/**
 * @ClassName: ShareManager
 * @Descritpion:share管理类
 * @author jp
 * @date 2014-4-9
 */
public class ShareManager
{
    /**
     * xml配置文件名
     */
    private final static String XML_FILE = "shareservice.xml";
    
    //单例
    private static ShareManager sInstance;
    
    //分享对象管理
    private ShareService mShareServices[];
    
    //分享参数
    private Map<String, HashMap<String, String>> mAllParamsMap;
    
    //分享对象管理-列表
    private HashMap<String, ShareService> mShareServiceMaps;
    
    //宿主
    private Activity mActy;
    
    private ShareListener mShareListener;
    
    /**
     * 分享数据
     */
    private ShareData mShareData;
    
    /**
     * 单例
     * @param context
     * @return
     */
    public static synchronized ShareManager getInstance(Context context)
    {
        if (null == sInstance)
        {
            sInstance = new ShareManager(context);
        }
        return sInstance;
    }
    
    private OnShareClickListener mOnShareClickListener = new OnShareClickListener()
    {
        
        @Override
        public void onShareClick(int index)
        {
            ShareService service = getShareServiceList().get(index);
            service.doShare(mShareData);
        }
    };
    
    private ShareManager(Context context)
    {
        mAllParamsMap = new HashMap<String, HashMap<String, String>>();
        initShareServiceMaps(context);
    }
    
    private void initShareServiceMaps(Context context)
    {
        if (null == mShareServiceMaps)
        {
            parseXML(context);
        }
    }
    
    private void parseXML(Context context)
    {
        mShareServiceMaps = new HashMap<String, ShareService>();
        InputStream paramsStream;
        try
        {
            paramsStream = context.getResources().getAssets().open(XML_FILE);
            SaxServiceHandle mSaxServiceHandle = new SaxServiceHandle();
            XmlBean rootBean = mSaxServiceHandle.getRootBean(paramsStream);
            for (XmlBean child : rootBean.getChildNode())
            {
                HashMap<String, String> paramMap = (HashMap<String, String>) child.getAttrMap();
                mAllParamsMap.put(child.getKey(), paramMap);
                ShareService service = initService(child.getKey(), context);
                if (service != null)
                {
                    mShareServiceMaps.put(service.getName(), service);
                }
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private ShareService initService(String shareName, Context context)
    {
        ShareService service = null;
        if (SinaWeibo.NAME.equals(shareName))
        {
            service = new SinaWeibo(context);
        }
        else if ("TencentWeibo".equals(shareName))
        {
            
        }
        else if (SmsShare.NAME.equals(shareName))
        {
            service = new SmsShare(context);
        }
        else if (MailShare.NAME.equals(shareName))
        {
            service = new MailShare(context);
        }
        return service;
    }
    
    /**
     * 获取分享实例
     * @param shareName
     * @return
     */
    public ShareService getShareService(String shareName)
    {
        if (null != mShareServiceMaps)
        {
            ShareService service = mShareServiceMaps.get(shareName);
            if (null != service)
            {
                service.initShareService();
            }
            return service;
        }
        return null;
    }
    
    /**
     * 设置每个分享的参数
     * @param shareName 分享模块名
     * @param key 参数key
     * @param value 参数value
     */
    public void setShareParam(String shareName, String key, String value)
    {
        if (mAllParamsMap == null)
        {
            mAllParamsMap = new HashMap<String, HashMap<String, String>>();
        }
        HashMap<String, String> paramsMap = (HashMap<String, String>) mAllParamsMap.get(shareName);
        if (paramsMap == null)
        {
            paramsMap = new HashMap<String, String>();
            paramsMap.put(key, value);
            mAllParamsMap.put(shareName, paramsMap);
        }
        else
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
     * 设置分享监听器
     * @param listener
     */
    public void setOnShareListener(ShareListener listener)
    {
        this.mShareListener = listener;
    }
    
    public void share(Context context, ShareData shareData)
    {
        ShareBasePopupWindow basePopupWindow = new ViewPagerPopup(context,
                getShareServiceList());
        basePopupWindow.addShareClickListener(mOnShareClickListener);
    }
    
    public void share(Activity acty, int shareType, ShareData shareData)
    {
        this.mActy = acty;
        if (shareType == ShareViewType.WHITE_GRID)
        {
            ViewPagerPopup viewPagerPopup = new ViewPagerPopup(mActy,
                    getShareServiceList());
            viewPagerPopup.show();
        }
        else if (shareType == ShareViewType.WHITE_LIST)
        {
            ListPopup listPopup = new ListPopup(mActy, getShareServiceList());
            listPopup.show();
        }
        else if (shareType == 2)
        {
            
        }
    }
    
    public ArrayList<ShareService> getShareServiceList()
    {
        if (null != mShareServiceMaps)
        {
            ArrayList<ShareService> list = new ArrayList<ShareService>();
            Iterator<Entry<String, ShareService>> iterator = mShareServiceMaps.entrySet()
                    .iterator();
            while (iterator.hasNext())
            {
                Entry<String, ShareService> entry = iterator.next();
                list.add(entry.getValue());
            }
            return list;
        }
        return null;
    }
    
    /**
     * 获取上下文
     * @return
     */
    public Context getContext()
    {
        return mActy.getApplicationContext();
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
