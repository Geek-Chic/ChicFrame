/**
 * @Title: KeyInfo.java
 * @Package com.geekchic.base.share.dao
 * @Description: 读取shareservice.xml下配置信息 
 * @author: evil
 * @date: 2014-7-5
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import android.content.Context;

import com.geekchic.base.xml.SaxServiceHandle;
import com.geekchic.base.xml.XmlBean;

/**
 * @ClassName: KeyInfo
 * @Descritpion: 读取shareservice.xml下配置信息 
 * @author evil
 * @date 2014-7-5
 */
public class KeyInfo
{
    /**新浪微博是否不设置key进行分享*/
    public  String sinaWeibo_IsNoKeyShare;
    /** 新浪微博AppKey */
    public  String sinaWeibo_AppKey;
    /** 新浪微博微博Appsecret */
    public  String sinaWeibo_AppSecret;
    /** 新浪微博RedirectUrl */
    public static String sinaWeibo_RedirectUrl;
    /** 腾讯微博AppKey */
    public  String tencentWeibo_AppKey;
    /** 腾讯微博AppSecret */
    public  String tencentWeibo_AppSecret;
    /** 腾讯微博RedirectUrl */
    public  String tencentWeibo_RedirectUrl;
    /** qq空间AppKey */
    public  String qZone_AppKey;
    /** qq空间AppId */
    public  String qZone_AppId;
    /** 微信AppId */
    public  String wechat_AppId;
    /** 微信Enable */
    public  String wechat_Enable;
    /** 微信朋友圈AppId */
    public  String wechatMoments_AppId;
    /** qq AppKey */
    public  String qQ_AppKey;
    /** qq AppId */
    public  String qQ_AppId;
    /** 人人网AppKey */
    public  String renren_AppKey;
    /** 人人网AppId */
    public  String renren_AppId;
    /** 人人网SecretKey */
    public static String renren_SecretKey;
    /** 短信Enable */
    public  String shortMessage_Enable;
    /**复制链接*/
    public  String copyLink;
    /** 微信在分享列表中的位置 */
    public  int weChatIndex;
    /** 朋友圈在分享列表中的位置 */
    public static int wechatMomentsIndex;
    /** 新浪微博在分享列表中的位置 */
    public static int sinaWeiboIndex;
    /** qq在分享列表中的位置 */
    public static int qQIndex;
    /** qq空间在分享列表中的位置 */
    public static int qZoneIndex;
    /** 腾讯微博在分享列表中的位置 */
    public static int tencentWeiboIndex;
    /** 人人网在分享列表中的位置 */
    public static int renrenIndex;
    /** 短信在分享列表中的位置 */
    public static int shortMessageIndex;
    /** 邮件在分享列表中的位置 */
    public static int emailIndex;
    /** 更多分享分享列表中的位置 */
    public static int moreIndex;
    /**复制链接在分享列表中的位置*/
    public static int copyLinkIndex;
    /** 用于添加需要分享的平台 */
    public static ArrayList<String> enList ;
    /**
     * 解析shareservice.xml
     */
    public static SaxServiceHandle mSaxServiceHandle;
    public KeyInfo(Context context){
            enList=new ArrayList<String>();
            parseXML(context);
    }
    private  void parseXML(Context context){
        enList.clear();
        InputStream paramsStream;
        try
        {
            paramsStream = context.getResources().getAssets().open("shareservice.xml");
            if(null==mSaxServiceHandle){
                mSaxServiceHandle=new SaxServiceHandle();
            }
            XmlBean rootBean=mSaxServiceHandle.getRootBean(paramsStream);
            for(XmlBean child:rootBean.getChildNode()){
                Map<String,String> paramMap=child.getAttrMap();
                if(child.getKey().equals("SinaWeibo")){
                    sinaWeibo_AppKey=paramMap.get("AppKey");
                    sinaWeibo_AppSecret=paramMap.get("AppSecret");
                    sinaWeibo_RedirectUrl=paramMap.get("RedirectUrl");
                }else if(child.getKey().equals("TencentWeibo")){
                    tencentWeibo_AppKey=paramMap.get("AppKey");
                    tencentWeibo_AppSecret=paramMap.get("AppSecret");
                    tencentWeibo_RedirectUrl=paramMap.get("RedirectUrl");
                }
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
