/**
 * @Title: Utility.java
 * @Package com.geekchic.base.share.util
 * @Description: 分享工具类 
 * @author: evil
 * @date: 2014-7-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.geekchic.base.share.dao.ShareParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.BitmapHelper;
import com.sina.weibo.sdk.utils.MD5;

/**
 * @ClassName: Utility
 * @Descritpion: 分享工具类 
 * @author evil
 * @date 2014-7-14
 */
public class Utility
{

    public static final class UploadImageUtils
    {

        private static void revitionImageSizeHD(String picfile, int size, int quality)
            throws IOException
        {
            if(size <= 0)
                throw new IllegalArgumentException("size must be greater than 0!");
            if(!Utility.doesExisted(picfile))
                throw new FileNotFoundException(picfile != null ? picfile : "null");
            if(!BitmapHelper.verifyBitmap(picfile))
                throw new IOException("");
            int photoSizesOrg = 2 * size;
            FileInputStream input = new FileInputStream(picfile);
            android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, opts);
            try
            {
                input.close();
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }
            int rate = 0;
            int i = 0;
            do
            {
                if(opts.outWidth >> i <= photoSizesOrg && opts.outHeight >> i <= photoSizesOrg)
                {
                    rate = i;
                    break;
                }
                i++;
            } while(true);
            opts.inSampleSize = (int)Math.pow(2D, rate);
            opts.inJustDecodeBounds = false;
            Bitmap temp = safeDecodeBimtapFile(picfile, opts);
            if(temp == null)
                throw new IOException("Bitmap decode error!");
            Utility.deleteDependon(picfile);
            Utility.makesureFileExist(picfile);
            int org = temp.getWidth() <= temp.getHeight() ? temp.getHeight() : temp.getWidth();
            float rateOutPut = (float)size / (float)org;
            if(rateOutPut < 1.0F)
            {
                Bitmap outputBitmap;
                do
                    try
                    {
                        outputBitmap = Bitmap.createBitmap((int)((float)temp.getWidth() * rateOutPut), (int)((float)temp.getHeight() * rateOutPut), android.graphics.Bitmap.Config.ARGB_8888);
                        break;
                    }
                    catch(OutOfMemoryError e)
                    {
                        System.gc();
                        rateOutPut = (float)((double)rateOutPut * 0.80000000000000004D);
                    }
                while(true);
                if(outputBitmap == null)
                    temp.recycle();
                Canvas canvas = new Canvas(outputBitmap);
                Matrix matrix = new Matrix();
                matrix.setScale(rateOutPut, rateOutPut);
                canvas.drawBitmap(temp, matrix, new Paint());
                temp.recycle();
                temp = outputBitmap;
            }
            FileOutputStream output = new FileOutputStream(picfile);
            if(opts != null && opts.outMimeType != null && opts.outMimeType.contains("png"))
                temp.compress(android.graphics.Bitmap.CompressFormat.PNG, quality, output);
            else
                temp.compress(android.graphics.Bitmap.CompressFormat.JPEG, quality, output);
            try
            {
                output.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            temp.recycle();
        }

        private static void revitionImageSize(String picfile, int size, int quality)
            throws IOException
        {
            if(size <= 0)
                throw new IllegalArgumentException("size must be greater than 0!");
            if(!Utility.doesExisted(picfile))
                throw new FileNotFoundException(picfile != null ? picfile : "null");
            if(!BitmapHelper.verifyBitmap(picfile))
                throw new IOException("");
            FileInputStream input = new FileInputStream(picfile);
            android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, opts);
            try
            {
                input.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            int rate = 0;
            int i = 0;
            do
            {
                if(opts.outWidth >> i <= size && opts.outHeight >> i <= size)
                {
                    rate = i;
                    break;
                }
                i++;
            } while(true);
            opts.inSampleSize = (int)Math.pow(2D, rate);
            opts.inJustDecodeBounds = false;
            Bitmap temp = safeDecodeBimtapFile(picfile, opts);
            if(temp == null)
                throw new IOException("Bitmap decode error!");
            Utility.deleteDependon(picfile);
            Utility.makesureFileExist(picfile);
            FileOutputStream output = new FileOutputStream(picfile);
            if(opts != null && opts.outMimeType != null && opts.outMimeType.contains("png"))
                temp.compress(android.graphics.Bitmap.CompressFormat.PNG, quality, output);
            else
                temp.compress(android.graphics.Bitmap.CompressFormat.JPEG, quality, output);
            try
            {
                output.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            temp.recycle();
        }

        public static boolean revitionPostImageSize(String picfile)
        {
            try
            {
//                if(isWifi())
//                    revitionImageSizeHD(picfile, 1600, 75);
//                else
                    revitionImageSize(picfile, 1024, 75);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        private static Bitmap safeDecodeBimtapFile(String bmpFile, android.graphics.BitmapFactory.Options opts)
        {
            android.graphics.BitmapFactory.Options optsTmp = opts;
            if(optsTmp == null)
            {
                optsTmp = new android.graphics.BitmapFactory.Options();
                optsTmp.inSampleSize = 1;
            }
            Bitmap bmp = null;
            FileInputStream input = null;
            int MAX_TRIAL = 5;
            for(int i = 0; i < 5; i++)
            {
                try
                {
                    input = new FileInputStream(bmpFile);
                    bmp = BitmapFactory.decodeStream(input, null, opts);
                    try
                    {
                        input.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
                catch(OutOfMemoryError e)
                {
                    e.printStackTrace();
                    optsTmp.inSampleSize *= 2;
                    try
                    {
                        input.close();
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    continue;
                }
                catch(FileNotFoundException e) { }
                break;
            }

            return bmp;
        }

        public UploadImageUtils()
        {
        }
    }


    public Utility()
    {
    }

    public static Bundle parseUrl(String url)
    {
        try
        {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        }
        catch(MalformedURLException e)
        {
            return new Bundle();
        }
    }

    public static void showToast(String content, Context ct)
    {
        Toast.makeText(ct, content, 1).show();
    }

    public static Bundle decodeUrl(String s)
    {
        Bundle params = new Bundle();
        if(s != null)
        {
            String array[] = s.split("&");
            String as[];
            int j = (as = array).length;
            for(int i = 0; i < j; i++)
            {
                String parameter = as[i];
                String v[] = parameter.split("=");
                params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
            }

        }
        return params;
    }

    public static String encodeUrl(ShareParams parameters)
    {
        if(parameters == null)
            return "";
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(int loc = 0; loc < parameters.size(); loc++)
        {
            if(first)
                first = false;
            else
                sb.append("&");
            String _key = parameters.getKey(loc);
            String _value = parameters.getValue(_key);
            if(_value == null)
                Log.i("encodeUrl", (new StringBuilder("key:")).append(_key).append(" 's value is null").toString());
            else
                sb.append((new StringBuilder(String.valueOf(URLEncoder.encode(parameters.getKey(loc))))).append("=").append(URLEncoder.encode(parameters.getValue(loc))).toString());
            Log.i("encodeUrl", sb.toString());
        }

        return sb.toString();
    }

    public static String encodeParameters(ShareParams httpParams)
    {
        if(httpParams == null || isBundleEmpty(httpParams))
            return "";
        StringBuilder buf = new StringBuilder();
        int j = 0;
        for(int loc = 0; loc < httpParams.size(); loc++)
        {
            String key = httpParams.getKey(loc);
            if(j != 0)
                buf.append("&");
            try
            {
                buf.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(httpParams.getValue(key), "UTF-8"));
            }
            catch(UnsupportedEncodingException unsupportedencodingexception) { }
            j++;
        }

        return buf.toString();
    }

    public static Bundle formBundle(Oauth2AccessToken oat)
    {
        Bundle params = new Bundle();
        params.putString("access_token", oat.getToken());
        params.putString("refresh_token", oat.getRefreshToken());
        params.putString("expires_in", (new StringBuilder(String.valueOf(oat.getExpiresTime()))).toString());
        return params;
    }

    public static Bundle formErrorBundle(Exception e)
    {
        Bundle params = new Bundle();
        params.putString("error", e.getMessage());
        return params;
    }

    public static void showAlert(Context context, String title, String text)
    {
        android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(text);
        alertBuilder.create().show();
    }

    private static boolean isBundleEmpty(ShareParams bundle)
    {
        return bundle == null || bundle.size() == 0;
    }

    public static String encodeBase62(byte data[])
    {
        StringBuffer sb = new StringBuffer(data.length * 2);
        int pos = 0;
        int val = 0;
        for(int i = 0; i < data.length; i++)
        {
            val = val << 8 | data[i] & 255;
            for(pos += 8; pos > 5;)
            {
                char c = encodes[val >> (pos -= 6)];
                sb.append(c != 'i' ? c != '+' ? c != '/' ? ((Object) (Character.valueOf(c))) : "ic" : "ib" : "ia");
                val &= (1 << pos) - 1;
            }

        }

        if(pos > 0)
        {
            char c = encodes[val << 6 - pos];
            sb.append(c != 'i' ? c != '+' ? c != '/' ? ((Object) (Character.valueOf(c))) : "ic" : "ib" : "ia");
        }
        return sb.toString();
    }

    public static byte[] decodeBase62(String string)
    {
        if(string == null)
            return null;
        char data[] = string.toCharArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(string.toCharArray().length);
        int pos = 0;
        int val = 0;
        for(int i = 0; i < data.length; i++)
        {
            char c = data[i];
            if(c == 'i')
            {
                c = data[++i];
                c = c != 'a' ? c != 'b' ? c != 'c' ? data[--i] : '/' : '+' : 'i';
            }
            val = val << 6 | decodes[c];
            for(pos += 6; pos > 7;)
            {
                baos.write(val >> (pos -= 8));
                val &= (1 << pos) - 1;
            }

        }

        return baos.toByteArray();
    }

    private static boolean deleteDependon(File file, int maxRetryCount)
    {
        int retryCount = 1;
        maxRetryCount = maxRetryCount >= 1 ? maxRetryCount : 5;
        boolean isDeleted = false;
        if(file != null)
            while(!isDeleted && retryCount <= maxRetryCount && file.isFile() && file.exists()) 
                if(!(isDeleted = file.delete()))
                    retryCount++;
        return isDeleted;
    }

    private static void mkdirs(File dir_)
    {
        if(dir_ == null)
            return;
        if(!dir_.exists() && !dir_.mkdirs())
            throw new RuntimeException((new StringBuilder("fail to make ")).append(dir_.getAbsolutePath()).toString());
        else
            return;
    }

    private static void createNewFile(File file_)
    {
        if(file_ == null)
            return;
        if(!__createNewFile(file_))
            throw new RuntimeException((new StringBuilder(String.valueOf(file_.getAbsolutePath()))).append(" doesn't be created!").toString());
        else
            return;
    }

    private static void delete(File f)
    {
        if(f != null && f.exists() && !f.delete())
            throw new RuntimeException((new StringBuilder(String.valueOf(f.getAbsolutePath()))).append(" doesn't be deleted!").toString());
        else
            return;
    }

    private static boolean __createNewFile(File file_)
    {
        if(file_ == null)
            return false;
        makesureParentExist(file_);
        if(file_.exists())
            delete(file_);
        try
        {
            return file_.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteDependon(String filepath, int maxRetryCount)
    {
        if(TextUtils.isEmpty(filepath))
            return false;
        else
            return deleteDependon(new File(filepath), maxRetryCount);
    }

    private static boolean deleteDependon(String filepath)
    {
        return deleteDependon(filepath, 0);
    }

    private static boolean doesExisted(File file)
    {
        return file != null && file.exists();
    }

    private static boolean doesExisted(String filepath)
    {
        if(TextUtils.isEmpty(filepath))
            return false;
        else
            return doesExisted(new File(filepath));
    }

    private static void makesureParentExist(File file_)
    {
        if(file_ == null)
            return;
        File parent = file_.getParentFile();
        if(parent != null && !parent.exists())
            mkdirs(parent);
    }

    private static void makesureFileExist(File file)
    {
        if(file == null)
            return;
        if(!file.exists())
        {
            makesureParentExist(file);
            createNewFile(file);
        }
    }

    private static void makesureFileExist(String filePath_)
    {
        if(filePath_ == null)
        {
            return;
        } else
        {
            makesureFileExist(new File(filePath_));
            return;
        }
    }

    public static boolean isWifi(Context mContext)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public static Bundle errorSAX(String responsetext)
    {
        Bundle mErrorBun = new Bundle();
        if(responsetext != null && responsetext.indexOf("{") >= 0)
            try
            {
                JSONObject json = new JSONObject(responsetext);
                mErrorBun.putString("error", json.optString("error"));
                mErrorBun.putString("error_code", json.optString("error_code"));
                mErrorBun.putString("error_description", json.optString("error_description"));
            }
            catch(JSONException e)
            {
                mErrorBun.putString("error", "JSONExceptionerror");
            }
        return mErrorBun;
    }

    public static boolean isNetworkAvailable(Context ct)
    {
        ConnectivityManager connectivity = (ConnectivityManager)ct.getSystemService("connectivity");
        if(connectivity == null)
            return false;
        NetworkInfo info[] = connectivity.getAllNetworkInfo();
        if(info != null)
        {
            NetworkInfo anetworkinfo[];
            int j = (anetworkinfo = info).length;
            for(int i = 0; i < j; i++)
            {
                NetworkInfo name = anetworkinfo[i];
                if(android.net.NetworkInfo.State.CONNECTED == name.getState())
                    return true;
            }

        }
        return false;
    }

    public static String getSign(Context context, String pkgName)
    {
        PackageInfo packageInfo;
        try
        {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 64);
        }
        catch(android.content.pm.PackageManager.NameNotFoundException localNameNotFoundException)
        {
            return null;
        }
        for(int j = 0; j < packageInfo.signatures.length; j++)
        {
            byte str[] = packageInfo.signatures[j].toByteArray();
            if(str != null)
                return MD5.hexdigest(str);
        }

        return null;
    }

    private static char encodes[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static byte decodes[] = new byte[256];





}
