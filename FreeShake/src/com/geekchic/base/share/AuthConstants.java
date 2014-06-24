/**
 * @Title: AuthConstants.java
 * @Package com.geekchic.base.share
 * @Description: 分享配置参数
 * @author: Evil
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share;

/**
 * @ClassName: AuthConstants
 * @Descritpion: 分享配置参数
 * @author Evil
 * @date 2014-7-2
   <SinaWeibo
                SortId="此平台在分享列表中的位置，由开发者自行定义，可以是任何整型数字，数值越大越靠后"
                AppKey="填写你在新浪微博上注册的AppKey"
                AppSecret="填写你在新浪微博上注册到的AppKey"
                Id="自定义字段，整形，用于你项目中对此平台的识别符"
                RedirectUrl="填写你在新浪微博上注册的RedirectUrl" />      
        
        各个平台注册应用信息的地址如下：
            新浪微博：http://open.weibo.com
            腾讯微博：http://dev.t.qq.com
            QQ空间：http://connect.qq.com/intro/login/
            网易微博：http://open.t.163.com
            搜狐微博：http://open.t.sohu.com
            豆瓣：http://developers.douban.com
            人人网：http://dev.renren.com
            开心网：http://open.kaixin001.com
            Instapaper：http://www.instapaper.com/main/request_oauth_consumer_token
            有道云笔记：http://note.youdao.com/open/developguide.html#app
            facebook：https://developers.facebook.com
            twitter：https://dev.twitter.com
            搜狐随身看：https://open.sohu.com
            QQ好友分享：http://mobile.qq.com/api/
            微信：http://open.weixin.qq.com-->
 */
public class AuthConstants
{
    public interface SinaWeiBo{
        public String sortIdString="1";
        public String AppKey="3201194191";
        public String AppSecret="0334252914651e8f76bad63337b3b78f";
        public String Id="1";
        public String RedirectUrl="http://appgo.cn";
    }
}
