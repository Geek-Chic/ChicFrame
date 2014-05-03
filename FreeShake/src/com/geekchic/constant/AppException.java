/**
 * @Title:AppException.java
 * @Package com.geekchic.constant
 * @Description:全局异常处理类
 * @author:jp
 * @date:2014-4-7
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Looper;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.DateUtil;

/**
 * @ClassName: AppException
 * @Descritpion:全局异常处理类 
 * @author jp
 * @date 2014-4-7
 */
public class AppException extends Exception implements UncaughtExceptionHandler
{
    private static final String TAG=AppException.class.getName();
    /**
     * SD卡LOG路径
     */
    private static final String SD_ERROR_LOG_PATH="/%packagename%/log/error";

    
    /** 系统默认的UncaughtException处理类 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 单例
     */
    private static AppException INSTANCE;
    private AppException()
    {
       
    }
    /** 获取CrashHandler实例 ,单例模式 */
    public static AppException getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new AppException();
        return INSTANCE;
    }
    /**
     * 初始化Exception监听
     * @param context
     */
    public void init(Context context){
        mContext=context;
        this.mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        //设置为默认Exception收集器
//        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        if(!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
        
    }
    public boolean handleException(Throwable ex){
        if (ex == null || mContext == null)
            return false;
        final String crashReport = getCrashReport(mContext, ex);
        new Thread() {
            public void run() {
                Looper.prepare();
                saveErrorLog(crashReport);
                sendAppCrashReport(mContext, crashReport);
                Looper.loop();
            }

        }.start();
        return true;
    }
    /**
     * 保存异常日志
     * @param excp
     */
    public File saveErrorLog(String excp) {
        String savePath = getErrorPath(mContext);
        String date = DateUtil.getDateStr();
        String logFilePath = "appexception";
        FileWriter fw = null;
        PrintWriter pw = null;
        File logFile=null;
        try {
                logFilePath = savePath+"appexception";
            //没有挂载SD卡，无法写文件
            if(logFilePath == ""){
                return logFile;
            }
             logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fw = new FileWriter(logFile,true);
            pw = new PrintWriter(fw);
            pw.println("--------------------"+DateUtil.getFormatDateTime(new Date(), DateUtil.FORMAT_YYYYMMDD_HHMMSS)+"---------------------");   
//            excp.printStackTrace(pw);
            pw.println(excp);
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();        
        }finally{ 
            if(pw != null){ pw.close(); } 
            if(fw != null){ try { fw.close(); } catch (IOException e) { }}
        }
        return logFile;
    }
    public static String getErrorPath(Context context){
        String errorPath = null;
        if(AppConfig.getInstance().isDebug()){
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();        
            if(storageState.equals(Environment.MEDIA_MOUNTED)){
                errorPath=Environment.getExternalStorageDirectory().getAbsolutePath()+SD_ERROR_LOG_PATH.replaceFirst("%packagename%",context.getPackageName());
            }
        }else {
            errorPath=new File(context.getFilesDir().getPath(), "log/error").getAbsolutePath();
        }
        return errorPath;
    }
    /**
     * 获取APP崩溃异常报告
     * @param ex
     * @return
     */
    private String getCrashReport(Context context, Throwable ex) {
        PackageInfo pinfo = getPackageInfo(context);
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: "+pinfo.versionName+"("+pinfo.versionCode+")\n");
        exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
        exceptionStr.append("Exception: "+ex.getMessage()+"\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString()+"\n");
        }
        return exceptionStr.toString();
    }
    /**
     * 获取App安装包信息
     * 
     * @return
     */
    private PackageInfo getPackageInfo(Context context)
    {
        PackageInfo info = null;
        try
        {
            info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        }
        catch (NameNotFoundException e)
        {
            // e.printStackTrace(System.err);
            Logger.i(TAG,"getPackageInfo err = " + e.getMessage());
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }
    /**
     * 发送App异常崩溃报告
     * 
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context cont,
            final String crashReport) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("R.string.app_error");
        builder.setMessage("R.string.app_error_message");
        builder.setPositiveButton("R.string.submit_report",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 发送异常报告
                        Intent i = new Intent(Intent.ACTION_SEND);
                        // i.setType("text/plain"); //模拟器
                        i.setType("message/rfc822"); // 真机
                        i.putExtra(Intent.EXTRA_EMAIL,
                                new String[] { "zhangdeyi@oschina.net" });
                        i.putExtra(Intent.EXTRA_SUBJECT,
                                "开源中国Android客户端 - 错误报告");
                        i.putExtra(Intent.EXTRA_TEXT, crashReport);
                        cont.startActivity(Intent.createChooser(i, "发送错误报告"));
                        // 退出
//                        AppManager.getAppManager().AppExit(cont);
                    }
                });
        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 退出
                        AppManager.getAppManager().AppExit(cont);
                    }
                });
        builder.show();
    }

    
}
