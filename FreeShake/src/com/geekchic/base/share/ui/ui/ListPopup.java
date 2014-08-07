package com.geekchic.base.share.ui.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.geekchic.base.share.ShareService;
import com.geekchic.common.utils.DeviceInfoUtil;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.common.utils.NetUtil;
import com.geekchic.wuyou.R;

/**
 * 白色列表样式的分享框
 * @author youtui
 * @since 14/5/4 
 */
public class ListPopup extends ShareBasePopupWindow implements OnClickListener
{
    /**用于判断分享页面是否正在运行*/
    private Button sharelist_knowaction_btn;
    
    private ShareGridAdapter adapter;
    
    private ArrayList<ShareService> services;
    
    public ListPopup(Context context, ArrayList<ShareService> services)
    {
        super(context);
        this.services = services;
        instance = this;
    }
    
    /**
     * 显示分享列表窗口
     */
    public void show()
    {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.yt_popup_list, null);
        initListView(view);
        initButton(view);
        
        // 设置popupwindow的属
        setFocusable(true);
        setOutsideTouchable(true);
        //		setBackgroundDrawable(YtCore.res.getDrawable(YtCore.res.getIdentifier("yt_side", "drawable", YtCore.packName)));
        setContentView(view);
        setWidth(DisplayInfo.getScreenWidth(context));
        setHeight(DeviceInfoUtil.dip2px(context, 350));
        setAnimationStyle(R.style.SharePopupAnim);
        // R.style.YtSharePopupAnim
        showAtLocation(getContentView(), Gravity.BOTTOM, 0, 0);
    }
    
    /**
     * 初始化ListView
     * 
     * @param view
     */
    private void initListView(View view)
    {
        ListView listView = (ListView) view.findViewById(R.id.sharelist_share_list);
        adapter = new ShareGridAdapter(context, services, showStyle);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    
    /**
     * 初始化查看和了解积分按钮
     * 
     * @param view
     */
    private void initButton(View view)
    {
        // 如果么有活动显示取消，如果有活动显示了解活动规则
        sharelist_knowaction_btn = (Button) view.findViewById(R.id.sharelist_knowaction_btn);
        sharelist_knowaction_btn.setText("取  消");
        sharelist_knowaction_btn.setOnClickListener(this);
    }
    
    /**
     * 查看和了解积分按钮监听
     */
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.sharelist_knowaction_btn)
        {
            this.dismiss();
        }
        //		else if (v.getId() == R.id.sharelist_checkpoint_btn) {
        //			Intent checkIt = new Intent(act, ShareActivity.class);
        //			checkIt.putExtra("from", "check");
        //			act.startActivity(checkIt);
        //		}
    }
    
    @Override
    /**列表项点击事件*/
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
            long arg3)
    {
        if (NetUtil.isNetConnected(context))
        {
            OnShare(position);
        }
        else
        {
            Toast.makeText(context, "无网络连接，请查看您的网络情况", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    
    /**
     * 关闭分享界面
     */
    @Override
    public void dismiss()
    {
        super.dismiss();
    }
    
    /**关闭 分享界面*/
    public static void close()
    {
        if (instance != null)
        {
            instance.dismiss();
        }
    }
    
}
