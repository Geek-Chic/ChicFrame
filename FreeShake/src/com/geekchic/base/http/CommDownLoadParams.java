package com.geekchic.base.http;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import com.geekchic.common.LogUtil;

public class CommDownLoadParams
{
    private URL downloadURL;
    private String titleName;
    private String fileName;
    private Context context;
    private int notifyId=0;
    public CommDownLoadParams(Context context,URL downloadURL,String titleName,String fileName,int notifyId){
        this.context=context;
        this.downloadURL=downloadURL;
        this.fileName=fileName;
        this.titleName=titleName;
        this.notifyId=notifyId;
    }
    public CommDownLoadParams(Context context,String downloadPath,String titleName,String fileName,int notifyId){
        this.context=context;
        this.fileName=fileName;
        this.titleName=titleName;
        this.notifyId=notifyId;
        try
        {
            this.downloadURL=new URL(downloadPath);
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.e(e.getMessage());
        }
    }
    public URL getDownloadURL()
    {
        return downloadURL;
    }
    public void setDownloadURL(URL downloadURL)
    {
        this.downloadURL = downloadURL;
    }
    public String getTitleName()
    {
        return titleName;
    }
    public void setTitleName(String titleName)
    {
        this.titleName = titleName;
    }
    public String getFileName()
    {
        return fileName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    public Context getContext()
    {
        return context;
    }
    public int getNotifyId()
    {
        return notifyId;
    }
    public void setNotifyId(int notifyId)
    {
        this.notifyId = notifyId;
    }
    
    
}
