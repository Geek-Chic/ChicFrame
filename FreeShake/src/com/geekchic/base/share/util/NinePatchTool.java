/**
 * @Title: NinePatchTool.java
 * @Package com.geekchic.base.share.util
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

/**
 * @ClassName: NinePatchTool
 * @Descritpion: 常用图片操作类
 * @author Administrator
 * @date 2014-6-26
 */
public class NinePatchTool
{
    private NinePatchTool()
    {
    }

    public static Drawable decodeDrawableFromAsset(Context context, String s)
    {
        Bitmap bitmap = decodeFromAsset(context, s);
        if(null == bitmap.getNinePatchChunk())
        {
            BitmapDrawable bitmapdrawable = new BitmapDrawable(bitmap);
            return bitmapdrawable;
        } else
        {
            Rect rect = new Rect();
            readPaddingFromChunk(bitmap.getNinePatchChunk(), rect);
            NinePatchDrawable ninepatchdrawable = new NinePatchDrawable(context.getResources(), bitmap, bitmap.getNinePatchChunk(), rect, null);
            return ninepatchdrawable;
        }
    }

    public static Bitmap decodeFromStream(InputStream inputstream)
    {
        Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
        byte abyte0[] = readChunk(bitmap);
        boolean flag = NinePatch.isNinePatchChunk(abyte0);
        if(flag)
        {
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 1, 1, bitmap.getWidth() - 2, bitmap.getHeight() - 2);
            bitmap.recycle();
            Field field;
            try
            {
                field = bitmap1.getClass().getDeclaredField("mNinePatchChunk");
                field.setAccessible(true);
                field.set(bitmap1, abyte0);
            }
            catch (NoSuchFieldException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return bitmap1;
        } else
        {
            return bitmap;
        }
    }

    public static Bitmap decodeFromFile(String s)
    {
        FileInputStream fileinputstream=null;
        Bitmap bitmap=null;
        try
        {
            fileinputstream = new FileInputStream(s);
            bitmap = decodeFromStream(fileinputstream);
            fileinputstream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap decodeFromAsset(Context context, String s)
    {
        InputStream inputstream=null;
        Bitmap bitmap = null;
        try
        {
            inputstream = context.getAssets().open(s);
             bitmap = decodeFromStream(inputstream);
            inputstream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void readPaddingFromChunk(byte abyte0[], Rect rect)
    {
        rect.left = getInt(abyte0, 12);
        rect.right = getInt(abyte0, 16);
        rect.top = getInt(abyte0, 20);
        rect.bottom = getInt(abyte0, 24);
    }

    public static byte[] readChunk(Bitmap bitmap)
    {
        int i = bitmap.getWidth();
        int j = bitmap.getHeight();
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        for(int k1 = 0; k1 < 32; k1++)
            bytearrayoutputstream.write(0);

        int ai[] = new int[i - 2];
        bitmap.getPixels(ai, 0, i, 1, 0, i - 2, 1);
        boolean flag = ai[0] == -16777216;
        boolean flag1 = ai[ai.length - 1] == -16777216;
        int i2 = 0;
        int j2 = 0;
        for(int k2 = ai.length; j2 < k2; j2++)
            if(i2 != ai[j2])
            {
                k++;
                writeInt(bytearrayoutputstream, j2);
                i2 = ai[j2];
            }

        if(flag1)
        {
            k++;
            writeInt(bytearrayoutputstream, ai.length);
        }
        i1 = k + 1;
        if(flag)
            i1--;
        if(flag1)
            i1--;
        ai = new int[j - 2];
        bitmap.getPixels(ai, 0, 1, 0, 1, 1, j - 2);
        flag = ai[0] == -16777216;
        flag1 = ai[ai.length - 1] == -16777216;
        i2 = 0;
        j2 = 0;
        for(int l2 = ai.length; j2 < l2; j2++)
            if(i2 != ai[j2])
            {
                l++;
                writeInt(bytearrayoutputstream, j2);
                i2 = ai[j2];
            }

        if(flag1)
        {
            l++;
            writeInt(bytearrayoutputstream, ai.length);
        }
        j1 = l + 1;
        if(flag)
            j1--;
        if(flag1)
            j1--;
        for(int l1 = 0; l1 < i1 * j1; l1++)
            writeInt(bytearrayoutputstream, 1);

        byte[] l1 = bytearrayoutputstream.toByteArray();
        l1[0] = 1;
        l1[1] = (byte)k;
        l1[2] = (byte)l;
        l1[3] = (byte)(i1 * j1);
        dealPaddingInfo(bitmap, l1);
        return l1;
    }

    private static void dealPaddingInfo(Bitmap bitmap, byte abyte0[])
    {
        int ai[] = new int[bitmap.getWidth() - 2];
        bitmap.getPixels(ai, 0, ai.length, 1, bitmap.getHeight() - 1, ai.length, 1);
        int i = 0;
        do
        {
            if(i >= ai.length)
                break;
            if(-16777216 == ai[i])
            {
                writeInt(abyte0, 12, i);
                break;
            }
            i++;
        } while(true);
        i = ai.length - 1;
        do
        {
            if(i < 0)
                break;
            if(-16777216 == ai[i])
            {
                writeInt(abyte0, 16, ai.length - i - 2);
                break;
            }
            i--;
        } while(true);
        ai = new int[bitmap.getHeight() - 2];
        bitmap.getPixels(ai, 0, 1, bitmap.getWidth() - 1, 0, 1, ai.length);
        i = 0;
        do
        {
            if(i >= ai.length)
                break;
            if(-16777216 == ai[i])
            {
                writeInt(abyte0, 20, i);
                break;
            }
            i++;
        } while(true);
        i = ai.length - 1;
        do
        {
            if(i < 0)
                break;
            if(-16777216 == ai[i])
            {
                writeInt(abyte0, 24, ai.length - i - 2);
                break;
            }
            i--;
        } while(true);
    }

    private static void writeInt(OutputStream outputstream, int i)
    {
        try
        {
            outputstream.write(i >> 0 & 255);
            outputstream.write(i >> 8 & 255);
            outputstream.write(i >> 16 & 255);
            outputstream.write(i >> 24 & 255);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void writeInt(byte abyte0[], int i, int j)
    {
        abyte0[i + 0] = (byte)(j >> 0);
        abyte0[i + 1] = (byte)(j >> 8);
        abyte0[i + 2] = (byte)(j >> 16);
        abyte0[i + 3] = (byte)(j >> 24);
    }

    private static int getInt(byte abyte0[], int i)
    {
        byte byte0 = abyte0[i + 0];
        byte byte1 = abyte0[i + 1];
        byte byte2 = abyte0[i + 2];
        byte byte3 = abyte0[i + 3];
        int j = byte0 | byte1 << 8 | byte2 << 16 | byte3 << 24;
        return j;
    }

    public static void printChunkInfo(Bitmap bitmap)
    {
        byte abyte0[] = bitmap.getNinePatchChunk();
        if(null == abyte0)
        {
            System.out.println((new StringBuilder()).append("can't find chunk info from this bitmap(").append(bitmap).append(")").toString());
            return;
        }
        byte byte0 = abyte0[1];
        byte byte1 = abyte0[2];
        byte byte2 = abyte0[3];
        StringBuilder stringbuilder = new StringBuilder();
        int i = getInt(abyte0, 12);
        int j = getInt(abyte0, 16);
        int k = getInt(abyte0, 20);
        int l = getInt(abyte0, 24);
        stringbuilder.append((new StringBuilder()).append("peddingLeft=").append(i).toString());
        stringbuilder.append("\r\n");
        stringbuilder.append((new StringBuilder()).append("paddingRight=").append(j).toString());
        stringbuilder.append("\r\n");
        stringbuilder.append((new StringBuilder()).append("paddingTop=").append(k).toString());
        stringbuilder.append("\r\n");
        stringbuilder.append((new StringBuilder()).append("paddingBottom=").append(l).toString());
        stringbuilder.append("\r\n");
        stringbuilder.append("x info=");
        for(int i1 = 0; i1 < byte0; i1++)
        {
            int l1 = getInt(abyte0, 32 + i1 * 4);
            stringbuilder.append((new StringBuilder()).append(",").append(l1).toString());
        }

        stringbuilder.append("\r\n");
        stringbuilder.append("y info=");
        for(int j1 = 0; j1 < byte1; j1++)
        {
            int i2 = getInt(abyte0, byte0 * 4 + 32 + j1 * 4);
            stringbuilder.append((new StringBuilder()).append(",").append(i2).toString());
        }

        stringbuilder.append("\r\n");
        stringbuilder.append("color info=");
        for(int k1 = 0; k1 < byte2; k1++)
        {
            int j2 = getInt(abyte0, byte0 * 4 + byte1 * 4 + 32 + k1 * 4);
            stringbuilder.append((new StringBuilder()).append(",").append(j2).toString());
        }

        System.err.println((new StringBuilder()).append("").append(stringbuilder).toString());
    }

    private static final int NO_COLOR = 1;
}


