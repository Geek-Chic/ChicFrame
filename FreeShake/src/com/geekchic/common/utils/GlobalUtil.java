package com.geekchic.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;

public class GlobalUtil
{
    public final static char RCHAR='\r';
    public final static char NCHAR='\n';
    public final static char SPACECHAR=' ';
    public final static String CHARSET_UTF8="utf-8";
    public static final DisplayMetrics getScreenResolution(Context context){
        DisplayMetrics dm=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
        }
    public static final int[] getViewOnScreen(View view){
        int[] locations=new int[2];
        view.getLocationOnScreen(locations);
        return locations;
    }
    public static String decodeUTF8String(InputStream inputStream){
        try
        {
            if(inputStream==null || inputStream.available()<=0){
                return "";
            }
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while((len=inputStream.read(buffer,0,buffer.length))>0){
                baos.write(buffer,0,len);
            }
            String str=new String(baos.toByteArray(),CHARSET_UTF8);
            return str;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtil.e(e.getMessage());
        }
        return null;
    }
    /**
     * �ַ�ת��ΪUTF-8�� 
     * @param str
     * @return
     */
    public static String toUtf8String(String str){
        try
        {
          byte[] buffer=str.getBytes(CHARSET_UTF8);  
          return new String(buffer,CHARSET_UTF8);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtil.e(e.getMessage());
        }
        return str;
    }
    /**
     * ��ȡ�ַ�utf8�͵��ַ�
     * @param str
     * @return
     */
     public static byte[] getUTF8Bytes(String str){
         try
        {
            byte[] buff=str.getBytes(CHARSET_UTF8);
            return buff;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtil.e(e.getMessage());
        }
        return null;
     }
     /**
      * url����
      * @param src
      * @return
      */
     public static String urlEncode(String src){
         try
        {
            String url=URLEncoder.encode(src,CHARSET_UTF8);
            if(url.indexOf("+")>-1){
                url=url.replace("+", "%20");
            }
            return url;
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return "";
     }
     /**
      * url����
      * @param src
      * @return
      */
     public static String urlDecode(String src){
         try
        {
            if(src.indexOf("%20")>-1){
                src=src.replace("%20", "+");
            }
            String str=URLDecoder.decode(src,CHARSET_UTF8);
            return str;
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return src;
     }
     /**
      * ��ȡSD���ļ�·��
      * @return SD��·������������SD�����򷵻ؿ�
      */
     public static String getSDPath(){
         File sdDir=null;
         boolean isSDCardExist=Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
         if(isSDCardExist){
             sdDir=Environment.getExternalStorageDirectory();
             return sdDir.toString();
         }else {
            return null;
        }
     }
     /**
      * �����ļ��У������ڻᴴ��
      * @param strFolder
      * @return �ļ��Ƿ����
      */
     public static boolean createFolder(String strFolder){
         File file=new File(strFolder);
         if(!file.exists()){
             if(file.mkdirs()){
                 return true;
             }else {
                return false;
            }
         }
        return true;
     }
     /**
      * �����ļ���Ŀ���ַ����֧���ļ��С�
      * @param context
      * @param srcFilePath �ļ�Դ��ַ
      * @param dictFileName �ļ�Ŀ���ַ
      * @return �������
      * @throws IOException
      */
     public static boolean copyFileToMemory(Context context,String srcFilePath,String dictFileName) throws IOException{
         File srcFile=new File(srcFilePath);
         if(!srcFile.exists()||srcFile.isDirectory()){
             return false;
         }
         BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(srcFile));
         FileOutputStream fos=context.openFileOutput(dictFileName, 0);
         BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fos);
         byte[] buff=new byte[1024*4];
         int len;
         while((len=bufferedInputStream.read(buff))!=-1){
             bufferedOutputStream.write(buff,0, len);
             bufferedOutputStream.flush();
         }
         bufferedInputStream.close();
         bufferedOutputStream.close();
         return true;
     }
     /**
      * ��ȡ�ļ����·��
      * @param context
      * @param dicFileName
      * @return
      */
     public static File getFileOutputStream(Context context,String dicFileName){
         File fosFile=context.getFileStreamPath(dicFileName);
         return fosFile;
     }
     public static void copyAssetsFile(Context context,String fileName,String targetName) throws IOException{
         File targetFile=new File(targetName);
         if(targetFile.exists()){
             LogUtil.i("Ŀ���ļ��Ѵ���");
             return;
         }
         AssetManager assetManager=context.getAssets();
         InputStream inputStream=assetManager.open(fileName);
         BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
         targetFile.createNewFile();
         FileOutputStream outputStream=new FileOutputStream(targetFile);
         BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(outputStream);
         byte[] buff=new byte[1024*4];
         int len;
         while((len=bufferedInputStream.read(buff))!=-1){
             bufferedOutputStream.write(buff, 0, len);
         }
         bufferedOutputStream.flush();
         bufferedInputStream.close();
         bufferedOutputStream.close();
         outputStream.close();
         inputStream.close();
     }
     /**
      * ������תʱ��
      * @param dateTaken
      * @return ��׼ʱ���ʽ�ַ�yyyy/MM/dd kk:mm:ss
      */
     public static String timeString(long dateTaken) {
         return DateFormat.format("yyyy/MM/dd kk:mm:ss", dateTaken).toString();
     }
     public static ArrayList<String> splitToLines(char[] content) {
         ArrayList<String> lines = new ArrayList<String>();
         int len = content.length;
         
         StringBuffer line = new StringBuffer();
         for (int i = 0; i < len;i++) {
                         char c = content[i];
                         line.append(c);
                         //����\rҪ�ж��Ƿ������\n
                         if (c ==RCHAR) {
                                 char nc = content[i+1];
                                 if (nc == NCHAR) {
                                         line.append(nc);
                                         i++;
                                 }
                                 lines.add(line.toString());
                                 line.delete(0, line.length());
                         } else if( c == NCHAR) {
                                 //����\nֱ�ӻ���
                                 lines.add(line.toString());
                                 line.delete(0, line.length());
                         }  else {
                                 if (i == len-1) {
                                         lines.add(line.toString());
                                 }
                         }
                 }
         return lines;
     }
     
     /**�õ�\n��\r���е�����*/
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
