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

import com.geekchic.base.share.ShareManager;
import com.geekchic.base.share.ShareService;
import com.geekchic.base.share.dao.ShareData;
import com.geekchic.base.share.othershare.MailShare;
import com.geekchic.base.share.ui.ui.ShareWebActivity;
import com.geekchic.base.share.ui.ui.ShareViewType;
import com.geekchic.constant.AppAction;
import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.framework.ui.dialog.BasicDialog.Builder;
import com.geekchic.framework.ui.dialog.effect.Effectstype;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ShareActivity
 * @Descritpion: 社交绑定
 * @author evil
 * @date 2014-7-2
 */
public class ShareActivity extends BaseTitleBarActivity implements
        OnClickListener
{
    private Button mAuthBtn, mShareBtn, mShareAllBtn, mShareUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    private void initView()
    {
        mAuthBtn = (Button) findViewById(R.id.btnLogin);
        mShareBtn = (Button) findViewById(R.id.btnShareAllGui);
        mShareAllBtn = (Button) findViewById(R.id.btnShareAll);
        mShareUser = (Button) findViewById(R.id.btnUserInfo);
        mShareUser.setOnClickListener(this);
        mShareAllBtn.setOnClickListener(this);
        mAuthBtn.setOnClickListener(this);
        mShareBtn.setOnClickListener(this);
    }
    
    /**
     * 后退
     */
    private OnClickListener mBackClickListener = new OnClickListener()
    {
        
        @Override
        public void onClick(View v)
        {
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
        setLeftButton(R.drawable.icon_tab_metra_back_selector,
                mBackClickListener);
        setTitleBarBackground(R.color.blue);
        return true;
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
//                Intent bindIntent = new Intent(AppAction.AuthAction.ACTION);
//                startActivity(bindIntent);
                BasicDialog basicDialog=new Builder(this)
                .setMessage("测试")
                .setEffectType(Effectstype.Shake)
                .setEffectDuration(700)
                .create()
                ;
                basicDialog.show();
                break;
            case R.id.btnShareAllGui:
                showGrid(false);
                break;
            case R.id.btnShareAll:
                ShareService sinaService = ShareService.getShareService(this,
                        MailShare.NAME);
                ShareData shareData = new ShareData();
                shareData.setText("分享感谢");
                shareData.bindActivity(this);
                sinaService.doShare(shareData);
                break;
            case R.id.btnUserInfo:
                Intent intent = new Intent(this, ShareWebActivity.class);
                startActivity(intent);
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
        ShareManager.getInstance(this).share(this,
                ShareViewType.WHITE_GRID,
                contentshareData);
    }
    
}
