package com.geekchic.freeshake.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

public class CommDownloadTask extends AsyncTask<CommDownLoadParams, Integer, Void>
{
    public static final int buffsize=512*1024;
    private static HashMap<String,CommDownloadTask> mDownloadTaskMap=new HashMap<String, CommDownloadTask>();
    private Context mContext;
    private CommDownLoadParams mCommDownLoadParams=null;
    private CommDownloadTaskListener mCommDownloadTaskListener;
    private CommHttpDBOps mCommHttpDBOps;
    private String fileAbsolutePath;
    private String fileName;
    private int notifyId;
    private boolean isFailed;
    private boolean isCancel;
    private File mSaveFile;
    private int fileSize;
    int errorCode;
    int speed;
    public CommDownloadTask(Context context,CommDownloadTaskListener listener){
        mContext=context;
        this.mCommDownloadTaskListener=listener;
        mCommHttpDBOps=new CommHttpInfoDBImpl(context);
    }
    
    @Override
    protected Void doInBackground(CommDownLoadParams... params)
    {
        // TODO Auto-generated method stub
         mCommDownLoadParams=params[0];
         URL downloadURL=mCommDownLoadParams.getDownloadURL();
         notifyId=mCommDownLoadParams.getNotifyId();
         fileAbsolutePath=mCommDownLoadParams.getFileName();
         
         File file=null;
         file=new File(fileAbsolutePath);
         if(mDownloadTaskMap.containsKey(fileAbsolutePath)){
             cancel(true);
             return null;
         }
         BufferedInputStream bis=null;
         BufferedOutputStream bos=null;
         try
        {
            HttpURLConnection connection=(HttpURLConnection)downloadURL.openConnection();
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
         mDownloadTaskMap.put(fileAbsolutePath, this);
         if(!file.getParentFile().exists()){
             file.getParentFile().mkdirs();
         }
         mSaveFile=file;
         if(mSaveFile.exists() && this.mCommDownloadTaskListener!=null && this.mCommDownloadTaskListener.onLoadFileExisting(mContext, mCommDownLoadParams)){
             return null;
         }
        return null;
    }
    public interface CommDownloadTaskListener{
        public boolean onLoadFileExisting(Context context,CommDownLoadParams params);
        public void onLoadProgress(Context context,CommDownLoadParams params,int progress,long allsize,int kbPerSecond);
        public void onLoadFinish(Context context,CommDownLoadParams params);
        public void onLoadFailed(Context context,CommDownLoadParams params,int err);
        public void onLoadCancel(Context context,CommDownLoadParams params);
        
    }
  
    
}
