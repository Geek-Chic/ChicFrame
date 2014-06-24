/**
 * @Title: HttpManager.java
 * @Package com.geekchic.base.share.http
 * @Description: 分享类Http请求核心类 
 * @author: jp
 * @date: 2014-7-1
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;

import com.geekchic.base.share.BasePairs;

/**
 * @ClassName: HttpManager
 * @Descritpion: 分享类Http请求核心类 
 * @author jp
 * @date 2014-7-1
 */
public class HttpManager
{
    public String openUrl(String url, ArrayList arrayList,
            ArrayList headerLists, ArrayList paramsLists)
    {
        if (null != arrayList)
        {
            LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
            BasePairs basePairs;
            for (Iterator iterator = arrayList.iterator(); iterator.hasNext(); linkedList.add(new BasicNameValuePair(
                    basePairs.key, (String) basePairs.value)))
            {
                basePairs = (BasePairs) iterator.next();
            }
            String formatUrl = URLEncodedUtils.format(linkedList, "UTF-8");
            if (formatUrl.length() > 0)
            {
                url = (new StringBuilder()).append(url)
                        .append("?")
                        .append(formatUrl)
                        .toString();
            }
        }
        HttpGet httpGet = new HttpGet(url);
        if (null != headerLists)
        {
            BasePairs headBasePairs;
            for (Iterator iterator2 = headerLists.iterator(); iterator2.hasNext(); httpGet.setHeader(headBasePairs.key,
                    (String) headBasePairs.value))
            {
                headBasePairs = (BasePairs) iterator2.next();
            }
        }
        if (null != paramsLists)
        {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            BasePairs paramPairs;
            for (Iterator iterator = paramsLists.iterator(); iterator.hasNext(); basicHttpParams.setParameter(paramPairs.key,
                    (String) paramPairs.value))
            {
                paramPairs = (BasePairs) iterator.next();
            }
            httpGet.setParams(basicHttpParams);
        }
        Object obj = null;
        if (url.startsWith("https://"))
        {
            obj = getNewHttpClient();
        }
        else
        {
            obj = new DefaultHttpClient();
        }
        HttpResponse httpResponse;
        try
        {
            httpResponse = ((HttpClient) (obj)).execute(httpGet);
            int requestCode = httpResponse.getStatusLine().getStatusCode();
            String resString = null;
            if (200 == requestCode)
            {
                resString = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");
            }
            else
            {
                String error = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");
                throw new Throwable(
                        new StringBuilder().append("Network Error:message=")
                                .append(error)
                                .append(", http_status = ")
                                .append(requestCode)
                                .toString());
            }
            ((HttpClient) (obj)).getConnectionManager().shutdown();
            return resString;
            
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Throwable e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public String a(String url, ArrayList arraylist, BasePairs a1,
            ArrayList arraylist1, ArrayList arraylist2)
    {
        try
        {
            HttpPost httppost;
            if (a1 != null && a1.value != null
                    && (new File((String) a1.value)).exists())
                httppost = getNewHttpPost(url, arraylist, a1);
            else
                httppost = getNewHttpPost(url, arraylist);
            if (arraylist1 != null)
            {
                BasePairs a2;
                for (Iterator iterator = arraylist1.iterator(); iterator.hasNext(); httppost.setHeader(a2.key,
                        (String) a2.value))
                    a2 = (BasePairs) iterator.next();
                
            }
            if (arraylist2 != null)
            {
                BasicHttpParams basichttpparams = new BasicHttpParams();
                BasePairs a3;
                for (Iterator iterator1 = arraylist2.iterator(); iterator1.hasNext(); basichttpparams.setParameter(a3.key,
                        a3.value))
                    a3 = (BasePairs) iterator1.next();
                
                httppost.setParams(basichttpparams);
            }
            Object obj = null;
            if (url.startsWith("https://"))
                obj = getNewHttpClient();
            else
                obj = new DefaultHttpClient();
            HttpResponse httpresponse = ((HttpClient) (obj)).execute(httppost);
            int i = httpresponse.getStatusLine().getStatusCode();
            String s1 = null;
            if (i == 200)
            {
                s1 = EntityUtils.toString(httpresponse.getEntity(), "utf-8");
            }
            else
            {
                String s2 = EntityUtils.toString(httpresponse.getEntity(),
                        "utf-8");
                throw new Throwable(
                        (new StringBuilder()).append("Network Error: message = ")
                                .append(s2)
                                .append(", http_status = ")
                                .append(i)
                                .toString());
            }
            return s1;
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        catch (Throwable e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    private HttpPost getNewHttpPost(String url, ArrayList paramsLists,
            BasePairs basePairs)
    {
        try
        {
            HttpPost httppost = new HttpPost(url);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            String s1 = UUID.randomUUID().toString();
            if (null != paramsLists)
            {
                StringBuilder stringBuilder;
                for (Iterator iterator = paramsLists.iterator(); iterator.hasNext(); bytearrayoutputstream.write(stringBuilder.toString()
                        .getBytes("utf-8")))
                {
                    BasePairs a2 = (BasePairs) iterator.next();
                    BasicNameValuePair basicnamevaluepair = new BasicNameValuePair(
                            a2.key, (String) a2.value);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("--").append(s1).append("\r\n");
                    stringBuilder.append("content-disposition: form-data; name=\"")
                            .append(basicnamevaluepair.getName())
                            .append("\"\r\n\r\n");
                    stringBuilder.append(basicnamevaluepair.getValue())
                            .append("\r\n");
                }
            }
            httppost.setHeader("Content-Type",
                    (new StringBuilder()).append("multipart/form-data; boundary=")
                            .append(s1)
                            .toString());
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("--").append(s1).append("\r\n");
            File file = new File((String) basePairs.value);
            stringbuilder.append("Content-Disposition: form-data; name=\"")
                    .append(basePairs.key)
                    .append("\"; filename=\"")
                    .append(file.getName())
                    .append("\"\r\n");
            String s2 = URLConnection.getFileNameMap()
                    .getContentTypeFor((String) basePairs.value);
            if (s2 == null || s2.length() <= 0)
                if (((String) basePairs.value).toLowerCase().endsWith("jpg")
                        || ((String) basePairs.value).toLowerCase()
                                .endsWith("jepg"))
                    s2 = "image/jpeg";
                else if (((String) basePairs.value).toLowerCase()
                        .endsWith("png"))
                    s2 = "image/png";
                else if (((String) basePairs.value).toLowerCase()
                        .endsWith("gif"))
                    s2 = "image/gif";
                else
                    s2 = "application/octet-stream";
            stringbuilder.append("Content-Type: ")
                    .append(s2)
                    .append("\r\n\r\n");
            bytearrayoutputstream.write(stringbuilder.toString()
                    .getBytes("utf-8"));
            FileInputStream fileinputstream = new FileInputStream(
                    (String) basePairs.value);
            byte abyte0[] = new byte[1024];
            for (int i = fileinputstream.read(abyte0); i > 0; i = fileinputstream.read(abyte0))
                bytearrayoutputstream.write(abyte0, 0, i);
            
            String s3 = (new StringBuilder()).append("\r\n--")
                    .append(s1)
                    .append("--\r\n")
                    .toString();
            bytearrayoutputstream.write(s3.getBytes("utf-8"));
            bytearrayoutputstream.flush();
            fileinputstream.close();
            bytearrayoutputstream.close();
            ByteArrayEntity bytearrayentity = new ByteArrayEntity(
                    bytearrayoutputstream.toByteArray());
            httppost.setEntity(bytearrayentity);
            return httppost;
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    private HttpPost getNewHttpPost(String url, ArrayList arraylist)
    {
        try
        {
            HttpPost httppost = new HttpPost(url);
            if (arraylist != null)
            {
                ArrayList arraylist1 = new ArrayList();
                BasePairs a1;
                for (Iterator iterator = arraylist.iterator(); iterator.hasNext(); arraylist1.add(new BasicNameValuePair(
                        a1.key, (String) a1.value)))
                    a1 = (BasePairs) iterator.next();
                
                UrlEncodedFormEntity urlencodedformentity = new UrlEncodedFormEntity(
                        arraylist1, "utf-8");
                httppost.setEntity(urlencodedformentity);
            }
            return httppost;
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return null;
    }
    
    private static HttpClient getNewHttpClient()
    {
        KeyStore trustStore;
        try
        {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http",
                    PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            org.apache.http.conn.ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 20000);
            HttpClient client = new DefaultHttpClient(ccm, params);
            return client;
        }
        catch (Exception e)
        {
            return new DefaultHttpClient();
        }
        
    }
    
    private static class MySSLSocketFactory extends SSLSocketFactory
    {
        private SSLContext sslContext;
        
        public MySSLSocketFactory(KeyStore trustStore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException
        {
            super(trustStore);
            sslContext = SSLContext.getInstance("TLS");
            TrustManager tm = new X509TrustManager()
            {
                
                @Override
                public X509Certificate[] getAcceptedIssuers()
                {
                    // TODO Auto-generated method stub
                    return null;
                }
                
                @Override
                public void checkServerTrusted(X509Certificate[] arg0,
                        String arg1) throws CertificateException
                {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void checkClientTrusted(X509Certificate[] arg0,
                        String arg1) throws CertificateException
                {
                    // TODO Auto-generated method stub
                    
                }
            };
            sslContext.init(null, new TrustManager[] { tm }, null);
        }
        
        public Socket createSocket(Socket socket, String host, int port,
                boolean autoClose) throws IOException, UnknownHostException
        {
            return sslContext.getSocketFactory().createSocket(socket,
                    host,
                    port,
                    autoClose);
        }
        
        public Socket createSocket() throws IOException
        {
            return sslContext.getSocketFactory().createSocket();
        }
    }
    //    public Bundle EncapUrl(String urlStr){
    //        urlStr=urlStr.replace("rrconnect", "http");
    //        urlStr=urlStr.replace("weiboconnect", "http");
    //        urlStr=urlStr.replace("fbconnect", "http");
    //        try
    //        {
    //            URL url=new URL(urlStr);
    //            Bundle bundle=
    //        }
    //        catch (Exception e)
    //        {
    //            // TODO: handle exception
    //        }
    //    }
}
