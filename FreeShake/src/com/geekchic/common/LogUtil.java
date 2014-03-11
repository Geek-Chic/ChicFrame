package com.geekchic.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class LogUtil
{
    private static final int Log_Level_error = 4;
    private static final int Log_Level_warn = 3;
    private static final int Log_Level_info = 2;
    private static final int Log_Level_debug = 1;
    private static final int Log_Level_verbose = 0;

    /** Log��Ϣ����>=logLevel����־��Ϣ��ӡ���� */
    private static int logLevel = 0;

    //�Ƿ���־д���ļ���
    private static final boolean Log_IN_FILE = false;
    private static final boolean Log_WITH_POSTION = false;
    private static final String LOG_TAG = "freeshake";
    private static final String LOG_FILE = LOG_TAG + ".log";
    
    private static long curTime;
    private static Context mContext = null;
    public LogUtil(){
        
    }
    public static void openLog(Context context){
        mContext=context;
    }
    public static void startCal(String title){
        curTime=System.currentTimeMillis();
    }
    public static void calSpan(String log){
        long time=System.currentTimeMillis();
        curTime=time;
    }
    public static void v(String msg){
        v(LOG_TAG,msg);
    }
    //��ϸ��Ϣ
    public static void v(String logTag, String msg)
    {
          if(Log_Level_verbose>=logLevel){
              if(Log_WITH_POSTION){
                  msg=msg+"on"+new Throwable().getStackTrace()[1].toString();
              }
              Log.v(logTag, msg);
              if(Log_IN_FILE){
                  writeIntoFile(msg);
              }
          }
    }
    //������־
    public static void d(String msg){
        d(LOG_TAG,msg);
    }
    public static void d(String logTag, String debugInfo)
    {
        if(Log_Level_debug>=logLevel){
            debugInfo=debugInfo+"on "+new Throwable().getStackTrace()[1].toString(); 
        }
        Log.d(logTag, debugInfo);
        if(Log_IN_FILE){
            writeIntoFile(debugInfo);
        }
        
    }
    //��Ϣ��־
    public static void i(String infoMsg){
           i(LOG_TAG,infoMsg);
    }
    public static void i(String logTag,String infoMsg){
        if(Log_Level_info>=logLevel){
            infoMsg=infoMsg+"on "+new Throwable().getStackTrace()[1].toString();
        }
        Log.i(logTag, infoMsg);
        if(Log_IN_FILE){
            writeIntoFile(infoMsg);
        }
    }
    public static void e(String errorMsg){
        e(LOG_TAG,errorMsg);
    }
    public static void e(String logTag,String errorMsg){
        if(Log_Level_error>=logLevel){
            errorMsg=errorMsg+"on "+new Throwable().getStackTrace()[1].toString();
        }
        Log.e(logTag, errorMsg);
        if(Log_IN_FILE){
            writeIntoFile(errorMsg);
        }
    }
    
    public static void e(String title,Exception e) {
            
            String msg=null;
            if (e==null) {
                    msg = title+": "+"null"; 
            } else {
                    msg = title+": "+e.toString();
            }
            e(msg);
    }
    public static boolean writeIntoFile(String log){
        log=log+"\n";
        boolean res=false;
        try
        {
            FileOutputStream fileOutputStream=mContext.openFileOutput(LOG_FILE, Context.MODE_APPEND);
            try
            {
                fileOutputStream.write(log.getBytes());
                fileOutputStream.flush();
                res=true;
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                LogUtil.e(e.toString());
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
        return res;
        
    }
}
