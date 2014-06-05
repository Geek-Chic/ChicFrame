/**
 * @Title: HttpUpload.java
 * @Package com.geekchic.framework.network
 * @Description: 通过上传类
 * @author: evil
 * @date: May 31, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.DataException;

/**
 * @ClassName: HttpUpload
 * @Descritpion: 通过上传类
 * @author evil
 * @date May 31, 2014
 */
public class HttpUpload {
	private static final String TAG="HttpUpload";
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String KEEP_ALIVE = "Keep-Alive";
	public static ConnectionResult upLoadFile(HttpRequestBean requestBean){
	      String end = "\r\n";  
	        String twoHyphens = "--";  
	        String boundary = "******";  
	        String filePath=requestBean.getParameterList().get(0).getValue();
	        HttpURLConnection connection = null;
			try {
				URL url = new URL(requestBean.getUrl());
				connection = (HttpURLConnection) url  
						.openConnection();  
				connection.setChunkedStreamingMode(128 * 1024);// 128K  
				// 允许输入输出流  
				connection.setDoInput(true);  
				connection.setDoOutput(true);  
				connection.setUseCaches(false);  
				// 使用POST方法  
				connection.setRequestMethod("POST");  
				connection.setRequestProperty("Connection",KEEP_ALIVE);  
				connection.setRequestProperty("Charset", UTF8_CHARSET);  
				connection.setRequestProperty("Content-Type",  
						"multipart/form-data;boundary=" + boundary);  
				
				DataOutputStream dos = new DataOutputStream(  
						connection.getOutputStream());  
				dos.writeBytes(twoHyphens + boundary + end);  
				dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""  
						+ filePath.substring(filePath.lastIndexOf("/") + 1) + "\"" + end);  
				dos.writeBytes(end);  
				
				FileInputStream fis = new FileInputStream(filePath);  
				byte[] buffer = new byte[8192]; // 8k  
				int count = 0;  
				// 读取文件  
				while ((count = fis.read(buffer)) != -1) {  
					dos.write(buffer, 0, count);  
				}  
				fis.close();  
				dos.writeBytes(end);  
				dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
				dos.flush();  
				int responseCode=connection.getResponseCode();
				Logger.d(TAG, "Response code:"+responseCode);
				if(responseCode==200){
					InputStream is = connection.getInputStream();  
					InputStreamReader isr = new InputStreamReader(is, UTF8_CHARSET);  
					BufferedReader br = new BufferedReader(isr);  
					String result = br.readLine();  
					Logger.i(TAG, result);  
					dos.close();  
					is.close();  
					ConnectionResult res=new ConnectionResult(connection.getHeaderFields(), result);
					return res;
				}else {
					throw new DataException("return error");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DataException e) {
				e.printStackTrace();
			}finally{
				if(null!=connection){
					connection.disconnect();
				}
			}
			return null;  
	}
}
