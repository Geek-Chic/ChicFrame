/**
 * @Title: MailShare.java
 * @Package com.geekchic.base.share.othershare
 * @Description: 邮件分享
 * @author: evil
 * @date: 2014-7-22
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.othershare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.geekchic.base.share.ShareService;
import com.geekchic.base.share.dao.ShareData;
import com.geekchic.wuyou.R;

/**
 * @ClassName: MailShare
 * @Descritpion: 邮件分享
 * @author evil
 * @date 2014-7-22
 */
public class MailShare extends ShareService
{
    public static final String NAME = "Mail";
    
    public static final int MAIL_REQUEST_CODE = 1001;
    
    public MailShare(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void initShareService()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getName()
    {
        return NAME;
    }
    
    @Override
    public String getShowName(Context context)
    {
        return "邮件";
    }
    
    @Override
    public int getIconId()
    {
        return R.drawable.icon_mailact;
    }
    
    @Override
    public int getVersion()
    {
        return 0;
    }
    
    @Override
    public void doShare(ShareData shareData)
    {
        sendMail(shareData.getBindActivity(), shareData.getText());
    }
    
    /**
     * 分享到Email
     * @param emailBody
     */
    private void sendMail(Activity acty, String emailBody)
    {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        String emailSubject = "共享软件";
        // 设置邮件默认地址
        // email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
        // 设置邮件默认标题
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
        // 设置要默认发送的内容
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
        // 调用系统的邮件系统
        acty.startActivityForResult(Intent.createChooser(email, "请选择邮件发送软件"),
                MAIL_REQUEST_CODE);
    }
    
}
