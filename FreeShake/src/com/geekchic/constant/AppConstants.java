package com.geekchic.constant;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class AppConstants
{
    public static final int LOG_LEVEL_ERROR = 4;
    public static final int LOG_LEVEL_WARN = 3;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int Log_LEVEL_VERBOSE = 0;

    /**
     * 开发版本 debug 属性设置为TRUE
     */
    public static final boolean ISDEBUG_DEBUG_BUILD_VALUE = true;
    
    /**
     * 发布版本 debug 属性设置为FALSE
     */
    public static final boolean ISDEBUG_RELEASE_BUILD_VALUE = false;
    private static final boolean Log_WITH_POSTION = false;
    
    private static final String LOG_TAG = "freeshake";
    private static final String LOG_FILE = LOG_TAG + ".log";
    
    private static long curTime;
    private static Context mContext = null;
//    public LogConstants(){
//        
//    }
//    public static void openLog(Context context){
//        mContext=context;
//    }
//    public static void startCal(String title){
//        curTime=System.currentTimeMillis();
//    }
//    public static void calSpan(String log){
//        long time=System.currentTimeMillis();
//        curTime=time;
//    }
//    public static void v(String msg){
//        v(LOG_TAG,msg);
//    }
//    public static void v(String logTag, String msg)
//    {
//          if(Log_Level_verbose>=logLevel){
//              if(Log_WITH_POSTION){
//                  msg=msg+"on"+new Throwable().getStackTrace()[1].toString();
//              }
//              Log.v(logTag, msg);
//              if(Log_IN_FILE){
//                  writeIntoFile(msg);
//              }
//          }
//    }
//    public static void d(String msg){
//        d(LOG_TAG,msg);
//    }
//    public static void d(String logTag, String debugInfo)
//    {
//        if(Log_Level_debug>=logLevel){
//            debugInfo=debugInfo+"on "+new Throwable().getStackTrace()[1].toString(); 
//        }
//        Log.d(logTag, debugInfo);
//        if(Log_IN_FILE){
//            writeIntoFile(debugInfo);
//        }
//        
//    }
//    public static void i(String infoMsg){
//           i(LOG_TAG,infoMsg);
//    }
//    public static void i(String logTag,String infoMsg){
//        if(Log_Level_info>=logLevel){
//            infoMsg=infoMsg+"on "+new Throwable().getStackTrace()[1].toString();
//        }
//        Log.i(logTag, infoMsg);
//        if(Log_IN_FILE){
//            writeIntoFile(infoMsg);
//        }
//    }
//    public static void e(String errorMsg){
//        e(LOG_TAG,errorMsg);
//    }
//    public static void e(String logTag,String errorMsg){
//        if(Log_Level_error>=logLevel){
//            errorMsg=errorMsg+"on "+new Throwable().getStackTrace()[1].toString();
//        }
//        Log.e(logTag, errorMsg);
//        if(Log_IN_FILE){
//            writeIntoFile(errorMsg);
//        }
//    }
//    
//    public static void e(String title,Exception e) {
//            
//            String msg=null;
//            if (e==null) {
//                    msg = title+": "+"null"; 
//            } else {
//                    msg = title+": "+e.toString();
//            }
//            e(msg);
//    }
//    public static boolean writeIntoFile(String log){
//        log=log+"\n";
//        boolean res=false;
//        try
//        {
//            FileOutputStream fileOutputStream=mContext.openFileOutput(LOG_FILE, Context.MODE_APPEND);
//            try
//            {
//                fileOutputStream.write(log.getBytes());
//                fileOutputStream.flush();
//                res=true;
//            }
//            catch (IOException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                LogConstants.e(e.toString());
//            }
//        }
//        catch (FileNotFoundException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            LogConstants.e(e.toString());
//        }
//        return res;
//        
//    }
}
