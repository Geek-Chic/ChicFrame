package com.geekchic.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.geekchic.common.log.Logger;



public class NetStringUtil
{
    private static final String TAG=NetStringUtil.class.getName();
    public final static char RCHAR = '\r';
    public final static char NCHAR = '\n';
    public final static char SPACECHAR = ' ';
    //���������ж����ַ���װΪUTF-8��ʽ���ַ�
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
    //ת��ΪUTF-8����
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
     //��ȡUTF-8�ַ�ı�������
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
     //url����
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
     //���� 
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
    
}
