package com.geekchic.base.http;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.geekchic.base.http.CommDownloadTask.CommDownloadTaskListener;
import com.geekchic.freeshake.R;

public class SimpleDownloadLinstener implements CommDownloadTaskListener
{
    Context mContext;
    Notification notification;
    PendingIntent loadingIntent;
    NotificationManager mNotificationManager;
    public SimpleDownloadLinstener(Context context){
        this.mContext = context;
        initNotification();
    }
    private void initNotification() {
            mNotificationManager = (NotificationManager) mContext
                            .getSystemService(Context.NOTIFICATION_SERVICE);
            long when = System.currentTimeMillis();

            loadingIntent = PendingIntent.getActivity(mContext, 0,
                            new Intent(), 0);
            notification = new Notification(R.drawable.ic_launcher, "开始下载", when);
            notification.flags = Notification.FLAG_ONGOING_EVENT;
    }

    @Override
    public boolean onLoadFileExisting(Context context, CommDownLoadParams params)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLoadProgress(Context context, CommDownLoadParams params,
            int progress, long allsize, int kbPerSecond)
    {

        float size = ((int)((allsize>>10)*10.0f/1024))/10.0f;
        CharSequence contentTitle = params.getTitleName() +" ["+size+"M]";

        CharSequence contentText = "正在下载，已完成  " + progress + "%,"+kbPerSecond+"k/s";
        notification.setLatestEventInfo(mContext, contentTitle, contentText,
                        loadingIntent);
        mNotificationManager.notify(params.getNotifyId(), notification);

        
    }

    @Override
    public void onLoadFinish(Context context, CommDownLoadParams params)
    {
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.icon =  R.drawable.ic_launcher;
        CharSequence contentText = "下载完成";
        notification.setLatestEventInfo(context, params.getTitleName(), contentText,
                        loadingIntent);
        mNotificationManager.notify(params.getNotifyId(), notification);
        
    }

    @Override
    public void onLoadFailed(Context context, CommDownLoadParams params, int err)
    {
        CharSequence contentTitle = params.getTitleName();
        CharSequence contentText = params.getTitleName()+" 下载失败";
        notification.setLatestEventInfo(mContext, contentTitle, contentText,
                        loadingIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(params.getNotifyId(), notification);
        
    }

    @Override
    public void onLoadCancel(Context context, CommDownLoadParams params)
    {

        CharSequence contentTitle = params.getTitleName();
        CharSequence contentText = "已取消下载 "+params.getTitleName();
        notification.setLatestEventInfo(mContext, contentTitle, contentText,
                        loadingIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(params.getNotifyId(), notification);

        
    }
    
}
