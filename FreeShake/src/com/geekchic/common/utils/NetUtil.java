package com.geekchic.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.geekchic.common.log.Logger;

/**
 * @ClassName: NetUtil
 * @Descritpion: 网络工具类
 * @author evil
 * @date May 16, 2014
 */
public class NetUtil
{
	/**
	 * TAG
	 */
    private static final String TAG="NetUtil";
    public final static char RCHAR = '\r';
    public final static char NCHAR = '\n';
    public final static char SPACECHAR = ' ';
    /**
     * inputStream类UTF8转String
     * @param inputStream
     * @return
     */
    public static String decodeUTF8String(InputStream inputStream) {
        try {
            if (inputStream == null) {
                return "";
            }

            if (inputStream.available() <= 0) {
                return "";
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buff = new byte[1024];

            int len = 0;
            while ((len = inputStream.read(buff, 0, buff.length)) > 0) {

                baos.write(buff, 0, len);
            }

            String str = new String(baos.toByteArray(), "utf-8");

            return str;

        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }
    /**
     * String转UTF8
     * @param str
     * @return
     */
    public static String toUtf_8String(String str) {
        try {
            byte[] buff = str.getBytes("utf-8");

            return new String(buff, "utf-8");

        } catch (Exception e) {
            // TODO: handle exception
            Logger.e(TAG,e.getMessage());
        }
        return str;

    }
    /**
     * 获取utf8型byte数组
     * @param str
     * @return
     */
    public static byte[] getUtf_8bytes(String str) {
        try {
            byte[] buff = str.getBytes("utf-8");

            return buff;
        } catch (Exception e) {
            // TODO: handle exception
            Logger.e(TAG,e.getMessage());
        }
        return null;
    }
    /**
     * 编码URL
     * @param url
     * @return
     */
    public static String urlEncode(String url ) {
        try {
            String str = URLEncoder.encode(url, "UTF-8");

            if (str.indexOf("+") > -1) {
                str = str.replace("+", "%20");
            }

            return str;

        } catch (Exception e) {
            // TODO: handle exception
            Logger.e(TAG,e.getMessage());
            return "";
        }
    }
    /**
     * URL解码
     * @param src
     * @return
     */
    public static String urlDeocde(String src) {
        try {
            if (src.indexOf("%20") > -1) {
                src = src.replace("%20", "+");
            }

            String str = URLDecoder.decode(src, "UTF-8");

            return str;
        } catch (Exception e) {
            // TODO: handle exception
            Logger.e(TAG,e.getMessage());
            return "";
        }
    }
    
    public static String getMaxChunked(char[] content,String[] headstring) {
        String contentStr = new String(content);
        int len = content.length;
        int index = len-1;
        for (int i = len-1; i >=0; i--) {
            char c = content[i];
            if (c ==RCHAR||c == NCHAR) {
                if (i<len-1) {
                    index = i+1;
                }
                index = i+1;
                break;
            }
        }
        headstring[0] = contentStr.substring(index,len);
        
        return contentStr.substring(0, index);
    }
    
    public static boolean isEmpty(String string) {
        if (string==null||string.length() == 0) {
            return true;
        }
        return false;
    }
    /**
     * 网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetConnected(Context context) {
		boolean isNetConnected;
		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
//			String name = info.getTypeName();
//			L.i("当前网络名称：" + name);
			isNetConnected = true;
		} else {
			Logger.i(TAG,"没有可用网络");
			isNetConnected = false;
		}
		return isNetConnected;
	}
    
}
