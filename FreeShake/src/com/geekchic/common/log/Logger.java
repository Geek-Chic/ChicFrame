/**
 * @Title:Logger.java
 * @Package com.geekchic.common.log
 * @Description:日志工具类
 * @author:jp
 * @date:2014-4-7
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.common.log;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.LoggerFactory;

import android.content.Context;
import android.os.Environment;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicyBase;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.StatusPrinter;

import com.geekchic.constant.AppConfig;

/**
 * @ClassName: Logger
 * @Descritpion:Logger日志工具类
 * @author jp
 * @date 2014-4-7
 */
public class Logger
{
    /**
     * 日志打印到Sdcard的存储路径
     */
    private static final String SDCARD_LOG_PATH = "/%packagename%/log/file/";
    
    /**
     * 单个文件存储的最大容量
     */
    private static final int LOG_MAXSIZE = 1024 * 200;
    
    /**
     * Logger缓存
     */
    private static final ConcurrentHashMap<String, org.slf4j.Logger> mLogCache = new ConcurrentHashMap<String, org.slf4j.Logger>();
    
    private Logger()
    {
        
    }
    /**
     * 配置Log策略
     * @param context
     */
    public static void configureLogbackDirectly(Context context)
    {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
   
        //配置LogcatAppender
        PatternLayoutEncoder logcatEncoder = new PatternLayoutEncoder();
        logcatEncoder.setContext(lc);
        logcatEncoder.setPattern("[%thread] %msg%n");
        logcatEncoder.start();
        
        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(lc);
        logcatAppender.setEncoder(logcatEncoder);
        logcatAppender.start();
        
        //设置RollFileAppender
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        
        TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
        timeBasedRollingPolicy.setContext(lc);
        timeBasedRollingPolicy.setFileNamePattern(getLogPath(context)
                + "/log-%d{yyyy-MM-dd}");
        timeBasedRollingPolicy.setMaxHistory(5);
        timeBasedRollingPolicy.setParent(rollingFileAppender);
        timeBasedRollingPolicy.start();
        
        PatternLayoutEncoder rollFileEncoder = new PatternLayoutEncoder();
        rollFileEncoder.setContext(lc);
        rollFileEncoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        rollFileEncoder.start();
        
        
        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        rollingFileAppender.setContext(lc);
        rollingFileAppender.setEncoder(rollFileEncoder);
        rollingFileAppender.setPrudent(true);
        rollingFileAppender.start();
        
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(rollingFileAppender);
        root.addAppender(logcatAppender);
        if (AppConfig.getInstance().isDebug())
        {
            root.setLevel(Level.DEBUG);
        }
        else
        {
            root.setLevel(Level.INFO);
        }
    }
    
    public static String getLogPath(Context context)
    {
        String logPath;
        if (AppConfig.getInstance().isDebug())
        {
            logPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + SDCARD_LOG_PATH.replaceFirst("%packagename%",
                            context.getPackageName());
        }
        else
        {
            logPath = new File(context.getFilesDir().getPath(), "log").getAbsolutePath();
        }
        return logPath;
    }
    
    /**
     * 根据Tag获取缓存Logger
     * @param tag Tag
     * @return logger
     */
    private static org.slf4j.Logger getLogger(String tag)
    {
        org.slf4j.Logger logger = mLogCache.get(tag);
        if (logger == null)
        {
            logger = LoggerFactory.getLogger(tag);
            mLogCache.put(tag, logger);
        }
        return logger;
    }
    
    /**
     * 打印info级别的日志
     * @param tag TAG
     * @param msg Message
     */
    public static void i(String tag, String msg)
    {
        getLogger(tag).info(msg);
    }
    
    /**
     * 打印info级别的日志
     * @param tag TAG
     * @param msg Message
     * @param throwable Throwable
     */
    public static void i(String tag, String msg, Throwable throwable)
    {
        getLogger(tag).info(msg, throwable);
    }
    
    /**
     * 打印Debug级别日志
     * @param tag Tag
     * @param msg Message
     */
    public static void d(String tag, String msg)
    {
        getLogger(tag).debug(msg);
    }
    
    /**
     * 打印Debug级别日志
     * @param tag Tag
     * @param msg Message
     * @param throwable Throwable
     */
    public static void d(String tag, String msg, Throwable throwable)
    {
        getLogger(tag).debug(msg);
    }
    
    /**
     *  打印Error级别日志
     * @param tag Tag
     * @param msg Message
     */
    public static void e(String tag, String msg)
    {
        getLogger(tag).error(msg);
    }
    
    /**
     *  打印Error级别日志
     * @param tag Tag
     * @param msg Message
     * @param throwable Throwable
     */
    public static void e(String tag, String msg, Throwable throwable)
    {
        getLogger(tag).error(msg, throwable);
    }
    
    /**
     * 打印warning级别日志
     * @param tag Tag
     * @param msg Message
     */
    public static void w(String tag, String msg)
    {
        getLogger(tag).warn(msg);
    }
    
    /**
     * 打印warning级别日志
     * @param tag Tag
     * @param msg Message
     * @param throwable Throwable
     */
    public static void w(String tag, String msg, Throwable throwable)
    {
        getLogger(tag).warn(msg, throwable);
    }
    
    /**
     * 打印Verbose级别日志
     * @param tag Tag
     * @param msg Message
     */
    public static void v(String tag, String msg)
    {
        getLogger(tag).trace(msg);
    }
    
    /**
     * 打印Verbose级别日志
     * @param tag Tag
     * @param msg Message
     * @param throwable Throwable
     */
    public static void v(String tag, String msg, Throwable throwable)
    {
        getLogger(tag).trace(msg, throwable);
    }
    
    /**
     * 打印日志内部状态 
     * @param tag Tag
     */
    public static void displayStatus()
    {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }
}
