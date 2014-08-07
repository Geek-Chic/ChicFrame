/**
 * @Title: MailShare.java
 * @Package com.geekchic.base.share.othershare
 * @Description: 短信分享
 * @author: evil
 * @date: 2014-7-22
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.othershare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.geekchic.base.share.ShareService;
import com.geekchic.base.share.dao.ShareData;
import com.geekchic.wuyou.R;

/**
 * @ClassName: SmsShare
 * @Descritpion: 短信分享
 * @author evil
 * @date 2014-7-22
 */
public class SmsShare extends ShareService
{
    public static final String NAME = "Sms";
    
    public static final int SMS_REQUEST_CODE = 1000;
    
    public SmsShare(Context context)
    {
        super(context);
    }
    
    @Override
    public void initShareService()
    {
        
    }
    
    @Override
    public String getName()
    {
        return NAME;
    }
    
    @Override
    public String getShowName(Context context)
    {
        return "短信";
    }
    
    /**
     * 分享到短信
     * @param sms_body
     */
    private void sendSMS(Activity acty, String sms_body)
    {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        sendIntent.putExtra("sms_body", sms_body);
        sendIntent.setType("vnd.android-dir/mms-sms");
        acty.startActivityForResult(sendIntent, SMS_REQUEST_CODE);
    }
    
    @Override
    public int getVersion()
    {
        return 0;
    }
    
    @Override
    public void doShare(ShareData shareData)
    {
        sendSMS(shareData.getBindActivity(), shareData.getText());
    }
    
    @Override
    public int getIconId()
    {
        return R.drawable.icon_messact;
    }
    
}
