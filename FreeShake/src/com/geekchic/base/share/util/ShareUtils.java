/**
 * @Title: ShareUtils.java
 * @Package com.geekchic.base.share.util
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import com.geekchic.wuyou.R;

/**
 * @ClassName: ShareUtils
 * @Descritpion: 资源加载类
 * @author Administrator
 * @date 2014-6-26
 */
public class ShareUtils
{
    private static HashMap strings;
    private static float density;
    public static int dipToPx(Context context, int i)
    {
        if(density <= 0.0F)
            density = context.getResources().getDisplayMetrics().density;
        return (int)((float)i * density + 0.5F);
    }

    public static int getScreenWidth(Context context)
    {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context)
    {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static String getString(Context context, String s)
    {
        String s1 = (new StringBuilder()).append("/").append(ShareUtils.class.getName().replace('.', '/')).toString();
        s1 = s1.substring(0, s1.length() - 1);
        return getString(context, s1, s);
    }

    public static String getString(Context context, String s, String s1)
    {
        if(strings == null)
            initString(s);
        String s2 = null;
        if(strings != null)
            s2 = (String)strings.get(s1);
        if(s2 == null)
        {
            int i = context.getResources().getIdentifier(s1, "string", context.getPackageName());
            if(i > 0)
                s2 = context.getResources().getString(i);
        }
        return s2 != null ? s2 : "";
    }

    private static void initString(String s)
    {
        try
        {
            strings = new HashMap();
            XmlPullParserFactory xmlpullparserfactory = XmlPullParserFactory.newInstance();
            xmlpullparserfactory.setNamespaceAware(true);
            XmlPullParser xmlpullparser = xmlpullparserfactory.newPullParser();
            InputStream inputstream = ShareUtils.class.getResourceAsStream((new StringBuilder()).append(s).append("strings.xml").toString());
            xmlpullparser.setInput(inputstream, "utf-8");
            for(int i = xmlpullparser.getEventType(); i != 1; i = xmlpullparser.next())
            {
                if(i != 2 || !"string".equals(xmlpullparser.getName()))
                    continue;
                String s1 = xmlpullparser.getAttributeValue(0);
                i = xmlpullparser.next();
                String s2 = null;
                if(i == 4)
                    s2 = xmlpullparser.getText();
                strings.put(s1, s2);
            }

            inputstream.close();
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
            strings = null;
        }
    }

    public static Bitmap getBitmap(Context context, String s)
    {
        String s1 = (new StringBuilder()).append("/").append(ShareUtils.class.getName().replace('.', '/')).toString();
        s1 = s1.substring(0, s1.length() - 1);
        return getBitmap(context, s1, s);
    }

    public static Bitmap getBitmap(Context context, String s, String s1)
    {
        try
        {
            boolean flag = false;
            InputStream inputstream = ShareUtils.class.getResourceAsStream((new StringBuilder()).append(s).append(s1).append(".png").toString());
            if(inputstream == null)
            {
                inputstream = ShareUtils.class.getResourceAsStream((new StringBuilder()).append(s).append(s1).append(".9.png").toString());
                flag = true;
            }
            if(inputstream == null)
            {
                flag = false;
                inputstream = ShareUtils.class.getResourceAsStream((new StringBuilder()).append(s).append(s1).append(".jpg").toString());
            }
            if(inputstream != null)
            {
                Bitmap bitmap;
                if(flag)
                    bitmap = NinePatchTool.decodeFromStream(inputstream);
                else
                    bitmap = getBitmap(inputstream, 1);
                inputstream.close();
                return bitmap;
            }
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
        return null;
    }

    private static Bitmap getBitmap(InputStream inputstream, int i)
    {
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inPreferredConfig = android.graphics.Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = i;
        return BitmapFactory.decodeStream(inputstream, null, options);
    }

    public static Bitmap getBitmap(File file)
        throws Throwable
    {
        return getBitmap(file, 1);
    }

    public static Bitmap getBitmap(File file, int i)
        throws Throwable
    {
        FileInputStream fileinputstream = new FileInputStream(file);
        Bitmap bitmap = getBitmap(((InputStream) (fileinputstream)), i);
        fileinputstream.close();
        return bitmap;
    }

    public static Bitmap getBitmap(String s)
        throws Throwable
    {
        return getBitmap(s, 1);
    }

    public static Bitmap getBitmap(String s, int i)
        throws Throwable
    {
        return getBitmap(new File(s), i);
    }

    public static Drawable getDrawable(Context context, String s)
    {
        String s1 = (new StringBuilder()).append("/").append(ShareUtils.class.getName().replace('.', '/')).toString();
        s1 = s1.substring(0, s1.length() - 1);
        return getDrawable(context, s1, s);
    }

    public static Drawable getDrawable(Context context, String s, String s1)
    {
        Bitmap bitmap = getBitmap(context, s, s1);
        if(bitmap == null)
        {
            int i = context.getResources().getIdentifier(s1, "drawable", context.getPackageName());
            if(i > 0)
                return context.getResources().getDrawable(i);
            else
                return null;
        }
        if(bitmap.isRecycled())
            return null;
        if(bitmap.getNinePatchChunk() == null)
        {
            return new BitmapDrawable(bitmap);
        } else
        {
            Rect rect = new Rect();
            NinePatchTool.readPaddingFromChunk(bitmap.getNinePatchChunk(), rect);
            NinePatchDrawable ninepatchdrawable = new NinePatchDrawable(context.getResources(), bitmap, bitmap.getNinePatchChunk(), rect, null);
            return ninepatchdrawable;
        }
    }
}
