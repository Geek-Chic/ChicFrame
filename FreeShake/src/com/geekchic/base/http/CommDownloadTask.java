package com.geekchic.base.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.NetStringUtil;

public class CommDownloadTask extends
        AsyncTask<CommDownLoadParams, Integer, Void>
{
    private static final String TAG = CommDownloadTask.class.getName();
    
    public static final int buffsize = 512 * 1024;
    
    private static HashMap<String, CommDownloadTask> mDownloadTaskMap = new HashMap<String, CommDownloadTask>();
    
    private Context mContext;
    
    private CommDownLoadParams mCommDownLoadParams = null;
    
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
    
    public CommDownloadTask(Context context, CommDownloadTaskListener listener)
    {
        mContext = context;
        this.mCommDownloadTaskListener = listener;
    }
    
    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Void doInBackground(CommDownLoadParams... params)
    {
        // TODO Auto-generated method stub
        mCommDownLoadParams = params[0];
        URL downloadURL = mCommDownLoadParams.getDownloadURL();
        notifyId = mCommDownLoadParams.getNotifyId();
        fileAbsolutePath = mCommDownLoadParams.getFileName();
        
        File file = null;
        file = new File(fileAbsolutePath);
        if (mDownloadTaskMap.containsKey(fileAbsolutePath))
        {
            cancel(true);
            return null;
        }
        mDownloadTaskMap.put(fileAbsolutePath, this);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        mSaveFile = file;
        if (mSaveFile.exists()
                && this.mCommDownloadTaskListener != null
                && this.mCommDownloadTaskListener.onLoadFileExisting(mContext,
                        mCommDownLoadParams))
        {
            return null;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try
        {
            HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();
            CommHttpURL commHttpURL = CommHttpInfoDBImpl.getINSTANCE(mContext)
                    .getUrl(downloadURL.toString());
            if (commHttpURL != null)
            {
                if (!NetStringUtil.isEmpty(commHttpURL.getLastModified()))
                {
                    connection.setRequestProperty("If-Modified-Since",
                            commHttpURL.getLastModified());
                }
                if (!NetStringUtil.isEmpty(commHttpURL.getEtag()))
                {
                    connection.setRequestProperty("If-None-Match",
                            commHttpURL.getEtag());
                }
            }
            int stateCode = connection.getResponseCode();
            switch (stateCode)
            {
                case HttpURLConnection.HTTP_OK:
                    if (mSaveFile.exists())
                    {
                        mSaveFile.delete();
                        mSaveFile.createNewFile();
                    }
                    bis = new BufferedInputStream(connection.getInputStream(),
                            buffsize);
                    bos = new BufferedOutputStream(new FileOutputStream(
                            mSaveFile), buffsize);
                    fileSize = connection.getContentLength();
                    readFromInputStream(bis, bos);
                    break;
                
                case HttpURLConnection.HTTP_NOT_MODIFIED:
                    File cacheFile = new File(commHttpURL.getLocalData());
                    connection.disconnect();
                    bis = new BufferedInputStream(
                            new FileInputStream(cacheFile), buffsize);
                    bos = new BufferedOutputStream(new FileOutputStream(
                            mSaveFile), buffsize);
                    fileSize = (int) cacheFile.length();
                    readFromInputStream(bis, bos);
                    break;
                
                default:
                    isFailed = true;
                    errorCode = stateCode;
                    break;
            }
            
        }
        catch (Exception e)
        {
            Logger.e(TAG, new Throwable().getStackTrace()[0].toString()
                    + " Exception", e);
            isFailed = true;
        }
        finally
        {
            try
            {
                if (bos != null)
                {
                    bos.flush();
                    bos.close();
                }
                if (bis != null)
                {
                    bis.close();
                }
            }
            catch (IOException e2)
            {
            }
        }
        
        return null;
    }
    
    private void readFromInputStream(BufferedInputStream bis,
            BufferedOutputStream bos) throws IOException
    {
        byte[] buf = new byte[buffsize];
        int progress = 0;
        int finishedSize = 0;
        int readLen = -1;
        int lencount = 0;
        long time = System.currentTimeMillis();
        while ((readLen = bis.read(buf)) != -1 && !isCancel)
        {
            bos.write(buf, 0, readLen);
            finishedSize += readLen;
            lencount += readLen;
            // 计算新进度
            int newProgress = (int) (((double) finishedSize / fileSize) * 100);
            long curTime = System.currentTimeMillis();
            if (newProgress - progress > 0)
            {
                if (curTime - time > 1000)
                {
                    speed = (int) (((lencount * 1000) >> 10) / (curTime - time));
                    lencount = 0;
                    time = curTime;
                }
                publishProgress(newProgress);
            }
            progress = newProgress;
        }
        if (isCancel && finishedSize != fileSize)
        {
        }
        else
        {
            publishProgress(100);// 下载完成
        }
    }
    
    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);
        if (mDownloadTaskMap != null)
        {
            mDownloadTaskMap.remove(fileAbsolutePath);
        }
        if (this.mCommDownloadTaskListener != null)
        {
            if (isFailed)
            {
                this.mCommDownloadTaskListener.onLoadFailed(mContext,
                        mCommDownLoadParams,
                        errorCode);
            }
            else if (isCancel)
            {
                this.mCommDownloadTaskListener.onLoadCancel(mContext,
                        mCommDownLoadParams);
            }
            else
            {
                this.mCommDownloadTaskListener.onLoadFinish(mContext,
                        mCommDownLoadParams);
            }
        }
    }
    
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
        int progress = values[0];
        if (this.mCommDownloadTaskListener != null)
        {
            this.mCommDownloadTaskListener.onLoadProgress(mContext,
                    mCommDownLoadParams,
                    progress,
                    fileSize,
                    speed);
        }
    }
    
    @Override
    protected void onCancelled()
    {
        if (mDownloadTaskMap != null)
        {
            mDownloadTaskMap.remove(fileAbsolutePath);
        }
        if (this.mCommDownloadTaskListener != null)
        {
            this.mCommDownloadTaskListener.onLoadCancel(mContext,
                    mCommDownLoadParams);
        }
        super.onCancelled();
        
    }
    
    // 是否正在下载，fileName不包含路径名
    public static boolean isDownLoadingFile(String fileName)
    {
        return mDownloadTaskMap.containsKey(fileName);
    }
    
    public static void cancelDownload(String filename)
    {
        try
        {
            if (isDownLoadingFile(filename))
            {
                mDownloadTaskMap.get(filename).isCancel = true;
            }
        }
        catch (Exception e)
        {
            Logger.e(TAG, new Throwable().getStackTrace()[0].toString()
                    + " Exception ", e);
        }
    }
    
    public interface CommDownloadTaskListener
    {
        public boolean onLoadFileExisting(Context context,
                CommDownLoadParams params);
        
        public void onLoadProgress(Context context, CommDownLoadParams params,
                int progress, long allsize, int kbPerSecond);
        
        public void onLoadFinish(Context context, CommDownLoadParams params);
        
        public void onLoadFailed(Context context, CommDownLoadParams params,
                int err);
        
        public void onLoadCancel(Context context, CommDownLoadParams params);
        
    }
    
}
