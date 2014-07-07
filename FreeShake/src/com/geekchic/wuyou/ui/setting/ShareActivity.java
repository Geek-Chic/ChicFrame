/**
 * @Title: ShareActivity.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: 社交绑定
 * @author: jp
 * @date: 2014-7-2
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import cn.bidaround.youtui_template.YtTemplate;

import com.geekchic.base.share.dao.ShareData;
import com.geekchic.base.share.ui.YouTuiViewType;
import com.geekchic.constant.AppAction;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ShareActivity
 * @Descritpion: 社交绑定
 * @author evil
 * @date 2014-7-2
 */
public class ShareActivity extends BaseTitleBarActivity implements OnClickListener
{
    private Button mAuthBtn,mShareBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        mAuthBtn=(Button) findViewById(R.id.btnLogin);
        mShareBtn=(Button)findViewById(R.id.btnShareAllGui);
        mAuthBtn.setOnClickListener(this);
        mShareBtn.setOnClickListener(this);
    }
    /**
     * 后退
     */
    private OnClickListener mBackClickListener=new OnClickListener(){

        @Override
        public void onClick(View v) {
            finishActivity();
        }
        
    };
    @Override
    public int getLayoutId()
    {
        return R.layout.profile_share_main;
    }

    @Override
    public boolean initializeTitlBar()
    {
        setMiddleTitle("分享");
        setTitleBarBackground(R.color.blue);
        setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
        setTitleBarBackground(R.color.blue);
        return true;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                Intent bindIntent=new Intent(AppAction.AuthAction.ACTION);
                startActivity(bindIntent);
                break;
            case R.id.btnShareAllGui:
                showGrid(false);
                break;
            default:
                break;
        }
    }
    private void showGrid(boolean b)
    {
        ShareData contentshareData = new ShareData();
        contentshareData.isAppShare = false;
        contentshareData.setDescription("友推积分组件");
        contentshareData.setTitle("友推分享");
        contentshareData.setText("通过友推积分组件，开发者几行代码就可以为应用添加分享送积分功能，并提供详尽的后台统计数据，除了本身具备的分享功能外，开发者也可将积分功能单独集成在已有分享组件的app上，快来试试吧 ");
        contentshareData.setTarget_url("http://youtui.mobi/");
        contentshareData.setImageUrl("http://cdnup.b0.upaiyun.com/media/image/default.png");
//        // shareData.setImagePath(Environment.getExternalStorageDirectory()+YoutuiConstants.FILE_SAVE_PATH+"demo.png");
//
//        YtTemplate whiteTemplate = new YtTemplate(this, YouTuiViewType.WHITE_LIST, true);
//        whiteTemplate.setShareData(contentshareData);
//
//        YtShareListener listener2 = new YtShareListener() {
//
//            @Override
//            public void onSuccess(ErrorInfo error) {
//                YtLog.e("----", error.getErrorMessage());
//            }
//
//            @Override
//            public void onPreShare() {
//
//            }
//
//            @Override
//            public void onError(ErrorInfo error) {
//                YtLog.e("----", error.getErrorMessage());
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        };
//        /** 添加监听事件，非必需 */
//        whiteTemplate.addListener(YtPlatform.PLATFORM_QQ, listener2);
//        whiteTemplate.addListener(YtPlatform.PLATFORM_QZONE, listener2);
//        whiteTemplate.addListener(YtPlatform.PLATFORM_RENN, listener2);
//        whiteTemplate.addListener(YtPlatform.PLATFORM_SINAWEIBO, listener2);
//        whiteTemplate.addListener(YtPlatform.PLATFORM_TENCENTWEIBO, listener2);
//        whiteTemplate.addListener(YtPlatform.PLATFORM_WECHAT, listener2);
//        whiteTemplate.addListener(YtPlatform.PLATFORM_WECHATMOMENTS, listener2);
//        /** 添加分享数据，必需，找不到分享数据就可能空指针异常 */
        /*
         * whiteTemplate.addData(YtPlatform.PLATFORM_QQ, contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_QZONE,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_RENN,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_SINAWEIBO,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_TENCENTWEIBO,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_WECHAT,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_WECHATMOMENTS,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_MESSAGE,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_EMAIL,
         * contentshareData);
         * whiteTemplate.addData(YtPlatform.PLATFORM_MORE_SHARE,
         * contentshareData);
         */
//        whiteTemplate.show();
    }
    

    
}
