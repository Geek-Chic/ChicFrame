package com.geekchic.comm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.ActionBar.Tab;
import android.content.Context;
import android.util.Log;

public class LogUtil
{
    private static final int Log_Level_error = 4;
    private static final int Log_Level_warn = 3;
    private static final int Log_Level_info = 2;
    private static final int Log_Level_debug = 1;
    private static final int Log_Level_verbose = 0;

    /** Log信息级别>=logLevel的日志信息打印出来 */
    private static int logLevel = 0;

    //是否将日志写到文件中
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
    //详细信息
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
    //调试日志
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
    //信息日志
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
    //错误日志
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
