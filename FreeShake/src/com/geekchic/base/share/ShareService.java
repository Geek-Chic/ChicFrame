/**
 * @Title:ShareService.java
 * @Package com.geekchic.base.share
 * @Description:分享核心类 
 * @author:jp
 * @date:2014-4-9
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

import android.content.Context;

/**
 * @ClassName: ShareService
 * @Descritpion:分享核心类 
 * @author jp
 * @date 2014-4-9
 */
public abstract class ShareService
{
    public static final int ACTION_AUTHORIZING = 1;
    
    public static final int ACTION_GETTING_FRIEND_LIST = 2;
    
    public static final int ACTION_SENDING_DIRECT_MESSAGE = 5;
    
    public static final int ACTION_FOLLOWING_USER = 6;
    
    public static final int ACTION_TIMELINE = 7;
    
    public static final int ACTION_USER_INFOR = 8;
    
    public static final int ACTION_SHARE = 9;
    
    public static final int SHARE_TEXT = 1;
    
    public static final int SHARE_IMAGE = 2;
    
    public static final int SHARE_IMAGE_TEXT = 3;
    
    public static final int SHARE_WEBPAGE = 4;
    
    public static final int SHARE_MUSIC = 5;
    
    public static final int SHARE_VIDEO = 6;
    
    public static final int SHARE_APPS = 7;
    
    protected Context mContext;
    protected SharePreferenceUtils mSharePreferenceUtils;
    
    protected BasicShareActionLinstener mBasicShareActionLinstener;
    
    protected int id;
    
    private int sortID;
    
    private static boolean flag;
    
    /**
     * 分享参数管理类
     */
    private static ShareManager mShareManager;
    /**
     * @ClassName: ShareParams
     * @Descritpion:分享参数
     * @author jp
     * @date 2014-4-10
     */
    public static class ShareParams{
        public String text;
        public String imagePath;
    }
    
    public static void initSDK(Context context)
    {
        initSDK(context, null, true);
    }
    
    public static void initSDK(Context context, boolean flag)
    {
        initSDK(context, null, flag);
    }
    
    public static void initSDK(Context context, String s)
    {
        initSDK(context, s, true);
    }
    
    public static void initSDK(Context context, String share, boolean flag)
    {
        if(mShareManager==null){
            mShareManager=new ShareManager();
            String s1=share;
            if(s1==null){
                s1=mShareManager.getParam(context, "AppKey");
            }
            if(flag){
                
            }
        }
    }
    
    public static void stopSDK(Context context)
    {
    }
    
    public ShareService(Context context)
    {
        this.mContext = context;
        String share=getName();
        mSharePreferenceUtils=new SharePreferenceUtils(context, share, getVersion());
        mBasicShareActionLinstener=new BasicShareActionLinstener();
        setParam(share);
    }
    public static ShareService getShareService(Context context, String shareName)
    {
        if (shareName == null)
            return null;
        ShareService shareServices[] = getShareServices(context);
        if (shareServices == null)
            return null;
        int size = shareServices.length;
        for (int i = 0; i < size; i++)
        {
            ShareService service = shareServices[i];
            if (shareName.equals(service.getName()))
            {
                return service;
            }
        }
        return null;
    }
    
    public static ShareService[] getShareServices(Context context)
    {
        long start=System.currentTimeMillis();
        if(null==mShareManager){
            String s = "Please call ShareService.initSDK(Context) before any action.";
            throw new NullPointerException(s);
        }else{
            ShareService[] shareServices=mShareManager.init(context);
            return shareServices;
        }
        
    }
    
    public static void shortLinkTransformationSetting(boolean flag)
    {
        ShareService.flag = flag;
    }
    
    public void setShareParam(String key, String value)
    {
        if (mShareManager == null)
        {
            String tips = "Please call ShareService.initSDK(Context) before any action.";
            throw new NullPointerException(tips);
        }
        String shareName = getName();
        mShareManager.setShareParam(getContext(), shareName, key, value);
        setParam(shareName);
    }
    
    private void setParam(String shareName)
    {
        if (mShareManager == null)
        {
            String tips = "Please call ShareService.initSDK(Context) before any action.";
            throw new NullPointerException(tips);
        }
        String idParam = mShareManager.getParam(mContext, shareName, "Id");
        try
        {
            id = Integer.parseInt(idParam);
        }
        catch (Throwable throwable)
        {
            System.err.println("Failed to parse Id, this will cause method getId() always returens 0");
        }
        String sortIdParam = mShareManager.getParam(mContext,
                shareName,
                "SortId");
        try
        {
            sortID = Integer.parseInt(sortIdParam);
        }
        catch (Throwable throwable1)
        {
            System.err.println("Failed to parse SortId, this won't cause any problem, don't worry");
        }
        initShareService(shareName);
    }
    
    /**
     * 根据模块名获取参数
     * @param key 参数key
     * @return 参数值
     */
    protected String getParamByKey(String key)
    {
        if (mShareManager == null)
        {
            String s1 = "Please call AbstractWeibo.initSDK(Context) before any action.";
            throw new NullPointerException(s1);
        }
        else
        {
            return mShareManager.getParam(mContext, getName(), key);
        }
    }
    
    public Context getContext()
    {
        return mContext;
    }
    
    public int getSortId()
    {
        return sortID;
    }
    
    public void setShareServiceActionLinstener(
            ShareActionLinstener shareActionLinstener)
    {
        
    }
    
    public void showUser(String shareName)
    {
        startAction(ACTION_USER_INFOR, shareName);
    }
    
    protected void getUserInfo(int k, Object obj)
    {
        (new ActionThread(this, k, obj)).start();
    }
    
    public void followFriend(String s)
    {
        startAction(ACTION_FOLLOWING_USER, s);
    }
    
    public void listFriend(int k, int l, String s)
    {
        startAction(ACTION_GETTING_FRIEND_LIST, (Object) (new Object[] {
                Integer.valueOf(k), Integer.valueOf(l), s }));
    }
    
    public void getTimeLine(String s, int k, int l)
    {
        startAction(ACTION_TIMELINE,
                ((Object) (new Object[] { Integer.valueOf(k),
                        Integer.valueOf(l), s })));
    }
    
    protected void startAction(int actionId, Object obj)
    {
        (new ActionThread(this, actionId, obj)).start();
    }
    
    protected void handle(int what, Object obj)
    {
        switch (what)
        {
            case ACTION_AUTHORIZING:
                if (mBasicShareActionLinstener != null)
                {
                    mBasicShareActionLinstener.onComplete(this,
                            ACTION_AUTHORIZING,
                            null);
                }
                break;
            case ACTION_FOLLOWING_USER:
                b((String) obj);
                break;
            case ACTION_TIMELINE:
                Object aobj[] = (Object[]) (Object[]) obj;
                a(((Integer) aobj[0]).intValue(),
                        ((Integer) aobj[1]).intValue(),
                        (String) aobj[2]);
                break;
            case ACTION_USER_INFOR:
                authUser(obj!=null?(String)obj:null);
                break;
            default:
                break;
        }
    }
    public boolean isValid(){
        /**
         * 由数据库判断
         */
        return false;
    }
    public void removeAccount(){
        //从数据库中移除数据
    }
    protected abstract void a();
    
    protected abstract boolean a(int k, Object obj);
    
    protected abstract void a(int k, int l, String shareName);
    
    protected abstract void b(String s);
    
    /**
     * 验证获取授权用户信息
     * @param name
     */
    protected abstract void authUser(String name);
    
    public abstract void initShareService(String shareName);
    
    public abstract String getName();
    
    public abstract int getVersion();
    
    public abstract int getPlatformId();
    
}
