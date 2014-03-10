package com.geekchic.base.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.geekchic.base.http.CommDownloadTask.CommDownloadTaskListener;
import com.geekchic.common.LogUtil;
import com.geekchic.common.MD5;
import com.geekchic.common.NetStringUtil;

public class CommHttpRequest
{
    public static final String TAG="CommHttpRequest";
    public static final String CACHE_ROOT="commhttprequest_cache";
    public static final int BUFFER_SIZE=4*1024;
    public static final int DEFAULT_THREAD_POOL_SIZE=10;
    private  ThreadPoolExecutor  executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    private static Handler mHandler=null;
    private Context mContext;
    private CommHttpRequestLinstener callBack;
    private CommHttpLoadImageCallBack imageLoadCallBack;
    private CommHttpLoadTextCallBack textLoadCallBack;
    private CommHttpURL commHttpURL;
    private ArrayList<Header> httpHeaders;
    private boolean cacheEnable=true;
    private boolean writeToCache=true;
    public static CommHttpRequest requestWithURL(Context context,String baseUrl,NameValuePair... params){
        CommHttpRequest request = null;
        try
        {
            String url=baseUrl+concatParams(params);
            request = new CommHttpRequest(context,url);
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return request;
    }
    public static CommHttpRequest requestWithURL(Context context,String url){
        CommHttpRequest request=new CommHttpRequest(context,url);
        return request;
    }
    public static CommHttpRequest requestWithURL(Context context,String url,Header... headers){
        CommHttpRequest request=new CommHttpRequest(context,url);
        ArrayList<Header> headerLists=new ArrayList<Header>();
        for(Header header:headerLists){
            headerLists.add(header);
        }
        request.setHttpHeaders(headerLists);
        return request;
    }
    
    private static String concatParams(NameValuePair[] params) throws UnsupportedEncodingException {
        if (params == null||params.length<=0) {
                return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
                NameValuePair param = params[i];
                if (i == 0) {
                        sb.append("?");
                        sb.append(URLEncoder.encode(param.getName(),"UTF-8")+"="+URLEncoder.encode(param.getValue(),"UTF-8"));
                } else {
                        sb.append("&");
                        sb.append(URLEncoder.encode(param.getName(),"UTF-8")+"="+URLEncoder.encode(param.getValue(),"UTF-8"));
                }
        }
        return sb.toString();
}   
    static void checkHandler() {
    try {
        if (mHandler == null) {
                mHandler = new Handler();
        }
} catch (Exception e) {
        LogUtil.e(new Throwable().getStackTrace()[0].toString()
                        + " Exception ", e);
        mHandler = null;
}
}
     public CommHttpRequest(Context context,String url){
         this.mContext=context;
         initComURL(url);
     }
    private void initComURL(String url)
    {
         commHttpURL=CommHttpInfoDBImpl.getINSTANCE(mContext).getUrl(url);
         if(commHttpURL==null){
             commHttpURL=new CommHttpURL();
             commHttpURL.setUrl(url);
         }
    }
    public void setPostValueForKey(String key,String value){
        if(commHttpURL!=null){
            commHttpURL.getPostData().put(key, value);
        }
    }
    /**
     * @Title: startAsynchronous
     * @Description:异步请求
     * @param 传入参数名字
     * @return void 返回类型
     * @date 2012-3-23 上午10:37:38
     * @throw
     */
    public void startAsynchronous() {
            checkHandler();
            executor.execute(new Runnable() {
                    @Override
                    public void run() {
                            HttpResponse response = requestHttp(true, true);
                            if (callBack == null) {
                                    return;
                            }
                            if (response == null) {
                                    callBack.loadFailed(response, null);
                                    return;
                            }
                            try {
                                    int statusCode = response.getStatusLine().getStatusCode();
                                    switch (statusCode) {
                                    case 200: {
                                            InputStream is = getISFromRespone(response);
                                            callBack.loadFinished(is, false);
                                            break;
                                    }
                                    case 304: {
                                            InputStream is = getISFromCache();
                                            if (is != null) {
                                                    callBack.loadFinished(is, true);
                                            } else {
                                                    HttpResponse strickResponse = requestHttp(false,
                                                                    false);
                                                    is = getISFromRespone(strickResponse);
                                                    callBack.loadFinished(is, false);
                                            }
                                            break;
                                    }
                                    default: {
                                            if (!cacheEnable) {
                                                    return;
                                            }
                                            try {
                                                    BufferedInputStream bis = new BufferedInputStream(
                                                                    new FileInputStream(commHttpURL.getLocalData()));
                                                    callBack.loadFailed(response, bis);
                                            } catch (Exception e) {
                                                    callBack.loadFailed(response, null);
                                            }
                                            break;
                                    }
                                    }
                            } catch (Exception e) {
                                    LogUtil.e(new Throwable().getStackTrace()[0].toString()
                                                    + " Exception ", e);
                            }
                    }
            });
    }

    public void startAsynRequestString(CommHttpLoadTextCallBack callBack) {
            checkHandler();
            setTextLoadCallBack(callBack);
            executor.execute(new Runnable() {
                    @Override
                    public void run() {
                            final String content = startSyncRequestString();
                            if (textLoadCallBack!=null&&content!=null) {
                                    if (mHandler!=null) {
                                            mHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                            textLoadCallBack.textLoaded(content);
                                                    }
                                            });
                                    } else {
                                            textLoadCallBack.textLoaded(content);
                                    }
                            }
                    }
            });
    };
    public void startAsynRequestBitmap(CommHttpLoadImageCallBack callBack) {
            checkHandler();
            setImageLoadCallBack(callBack);
            executor.execute(new Runnable() {
                    @Override
                    public void run() {
                            final Bitmap bitmap = startSyncRequestBitmap();
                            if (imageLoadCallBack!=null&&bitmap!=null) {
                                    if (mHandler!=null) {
                                            mHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                            imageLoadCallBack.imageLoaded(bitmap);
                                                    }
                                            });
                                    } else {
                                            imageLoadCallBack.imageLoaded(bitmap);
                                    }
                            }
                    }
            });
    };
    
    
    /**
     * @Title: startSynchronous
     * @Description:同步请求，返回的是有缓冲的InputStream
     * @param @return 传入参数名字
     * @return InputStream 返回类型
     * @date 2012-3-23 上午10:29:10
     */
    public InputStream startSynchronous() {
            HttpResponse response = requestHttp(true, true);
            if (response == null) {
                    return null;
            }
            try {
                    int statusCode = response.getStatusLine().getStatusCode();
                    switch (statusCode) {
                    case 200: {
                            InputStream is = getISFromRespone(response);
                            return is;
                    }
                    case 304: {
                            InputStream is = getISFromCache();
                            if (is != null) {
                                    return is;
                            } else {
                                    response = requestHttp(false, false);
                                    is = getISFromRespone(response);
                                    return is;
                            }
                    }
                    default:
                            if (!cacheEnable) {
                                    return null;
                            }
                            try {
                                    InputStream is = getISFromCache();
                                    return is;
                            } catch (Exception e) {
                                    return null;
                            }
                    }
            } catch (Exception e) {
                    LogUtil.e(new Throwable().getStackTrace()[0].toString()
                                    + " Exception ", e);
            }
            return null;
    }

    /**   
    * @Title: startDownLoadFile   
    * @Description:必须在主线程调用
    * @param @param context
    * @param @param params
    * @param @param listener 传入参数名字
    * @return void 返回类型   
    * @date 2012-3-29 下午2:40:47
    * @throw
    */ 
    public static void startDownLoadFile(Context context,CommDownLoadParams params,CommDownloadTaskListener listener) {
            if (listener == null) {
                    listener = new SimpleDownloadLinstener(context);
            }
            CommDownloadTask task = new CommDownloadTask(context, listener);
            task.execute(params);
    }
    
    /**   
    * @Title: startGetStringSynchronous   
    * @Description:同步请求String
    * @param @return 传入参数名字
    * @return String 返回类型   
    * @date 2012-3-23 下午4:26:31
    * @throw
    */ 
    public String startSyncRequestString() {
            InputStream is = startSynchronous();
            if (is == null) {
                    return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            try {
                    while ((len = is.read(buffer))!=-1) {
                            baos.write(buffer, 0, len);
                    }
                    is.close();
            } catch (Exception e) {
                    LogUtil.e(new Throwable().getStackTrace()[0].toString()
                                    + " Exception ", e);
            }
            return baos.toString();
    }
    
    
    
    /**   
    * @Title: startGetBitmapSynchronous   
    * @Description:同步请求图片
    * @param @return 传入参数名字
    * @return Bitmap 返回类型   
    * @date 2012-3-23 下午4:25:43
    * @throw
    */ 
    public Bitmap startSyncRequestBitmap() {
            Bitmap cache = getBitmapFromCache();
            if (cache!=null) {
                    return cache;
            }
            InputStream is = startSynchronous();
            if (is == null) {
                    return null;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
    }
    
    
    public CommHttpURL getGalurl() {
            return commHttpURL;
    }

    private HttpResponse requestHttp(boolean haveLastModified, boolean haveEtag) {
            if (commHttpURL == null || NetStringUtil.isEmpty(commHttpURL.getUrl())||"null".equals(commHttpURL.getUrl())) {
                    LogUtil.i("commHttpURL 为空");
                    return null;
            }
            HttpResponse response = null;
            try {
                    if (commHttpURL.getPostData().size() > 0) {
                            HttpPost request = new HttpPost(commHttpURL.getUrl());
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            HashMap<String, String> params = commHttpURL.getPostData();
                            Set<String> keyset = params.keySet();
                            for (String key : keyset) {
                                    String value = params.get(key);
                                    nameValuePairs.add(new BasicNameValuePair(key, value));
                            }
                            if (!NetStringUtil.isEmpty(commHttpURL.getLastModified())
                                            && haveLastModified) {
                                    request.addHeader("If-Modified-Since",
                                                    commHttpURL.getLastModified());
                            }
                            if (!NetStringUtil.isEmpty(commHttpURL.getEtag()) && haveEtag) {
                                    request.addHeader("If-None-Match", commHttpURL.getEtag());
                            }
                            if (httpHeaders!=null) {
                                    for (Header header: httpHeaders) {
                                            request.addHeader(header);
                                    }
                            }
                            
                            request.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                                            "UTF-8"));
                            response = CommHttpClient.execute(mContext, request);
                    } else {
                            HttpGet request = new HttpGet(commHttpURL.getUrl());
                            if (!NetStringUtil.isEmpty(commHttpURL.getLastModified())) {
                                    request.addHeader("If-Modified-Since",
                                                    commHttpURL.getLastModified());
                            }
                            if (!NetStringUtil.isEmpty(commHttpURL.getEtag())) {
                                    request.addHeader("If-None-Match", commHttpURL.getEtag());
                            }
                            response = CommHttpClient.execute(mContext, request);
                    }
            } catch (Exception e) {
                    LogUtil.e(new Throwable().getStackTrace()[0].toString()
                                    + " Exception ", e);
            }
            return response;
    }

    private InputStream getISFromRespone(HttpResponse response) {
            try {
                    if (writeToCache) {
                            String filepath = writeInputSteamToCache(response.getEntity()
                                            .getContent());
                            if (!NetStringUtil.isEmpty(filepath)) {
                                    String lastModified = getHeader(response, "Last-Modified");
                                    String etag = getHeader(response, "ETag");
                                    commHttpURL.setLastModified(lastModified);
                                    commHttpURL.setEtag(etag);
                                    commHttpURL.setLocalData(filepath);
                                    if (CommHttpInfoDBImpl.getINSTANCE(mContext).existURL(commHttpURL.getUrl())) {
                                        CommHttpInfoDBImpl.getINSTANCE(mContext).updateURL(commHttpURL);
                                    } else {
                                        CommHttpInfoDBImpl.getINSTANCE(mContext).insertURL(commHttpURL);
                                    }
                                    BufferedInputStream bis = new BufferedInputStream(
                                                    new FileInputStream(commHttpURL.getLocalData()));
                                    return bis;
                            }
                    } else {
                            return new BufferedInputStream(response.getEntity().getContent());
                    }
            } catch (Exception e) {
                    LogUtil.e(new Throwable().getStackTrace()[0].toString()
                                    + " Exception ", e);
                    return null;
            }
            return null;
    }

    private Bitmap getBitmapFromCache() {
            if (commHttpURL == null || NetStringUtil.isEmpty(commHttpURL.getLocalData())) {
                    return null;
            }
            try {
                    Bitmap bitmap = BitmapFactory.decodeFile(commHttpURL.getLocalData());
                    return bitmap;
            } catch (Exception e) {
                    return null;
            }
    }
    private InputStream getISFromCache() {
            if (commHttpURL == null || NetStringUtil.isEmpty(commHttpURL.getLocalData())) {
                    return null;
            }
            File cache = new File(commHttpURL.getLocalData());
            try {
                    return new BufferedInputStream(new FileInputStream(cache));
            } catch (Exception e) {
                    return null;
            }
    }

    private String writeInputSteamToCache(InputStream is) {
            try {
                    File cachedir = mContext.getDir(CACHE_ROOT, 0);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    final String fileName = MD5.encodeMD5String(commHttpURL.getUrl());
                    File file = new File(cachedir, fileName);
                    if (file.exists()) {
                            file.delete();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(
                                    new FileOutputStream(file));
                    int len = 0;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((len = bis.read(buffer)) != -1) {
                            bos.write(buffer, 0, len);
                    }
                    bos.close();
                    bis.close();
                    return file.getAbsolutePath();
            } catch (Exception e) {
                    LogUtil.e(new Throwable().getStackTrace()[0].toString()
                                    + " Exception ", e);
                    return null;
            }
    }

    private String getHeader(HttpResponse responese, String headerName) {
            if (NetStringUtil.isEmpty(headerName) || responese == null) {
                    return null;
            }
            Header[] headers = responese.getHeaders(headerName);
            if (headers != null && headers.length > 0) {
                    return headers[0].getValue();
            }
            return null;
    }
   
    public Context getmContext()
    {
        return mContext;
    }
    public void setmContext(Context mContext)
    {
        this.mContext = mContext;
    }
    public CommHttpRequestLinstener getCallBack()
    {
        return callBack;
    }
    public void setCallBack(CommHttpRequestLinstener callBack)
    {
        this.callBack = callBack;
    }
    public CommHttpLoadImageCallBack getImageLoadCallBack()
    {
        return imageLoadCallBack;
    }
    public void setImageLoadCallBack(CommHttpLoadImageCallBack imageLoadCallBack)
    {
        this.imageLoadCallBack = imageLoadCallBack;
    }
    public CommHttpLoadTextCallBack getTextLoadCallBack()
    {
        return textLoadCallBack;
    }
    public void setTextLoadCallBack(CommHttpLoadTextCallBack textLoadCallBack)
    {
        this.textLoadCallBack = textLoadCallBack;
    }
    public ArrayList<Header> getHttpHeaders()
    {
        return httpHeaders;
    }
    public void setHttpHeaders(ArrayList<Header> httpHeaders)
    {
        this.httpHeaders = httpHeaders;
    }
    public boolean isWriteToCache()
    {
        return writeToCache;
    }
    public void setWriteToCache(boolean writeToCache)
    {
        this.writeToCache = writeToCache;
    }

    public interface CommHttpRequestLinstener{
        public void loadFinished(InputStream ins,boolean fromcache);
        public void loadFailed(HttpResponse response,InputStream cacheInputStream);    
    }
    public interface CommHttpLoadImageCallBack{
        public void imageLoaded(Bitmap bitmap);
    }
    public interface CommHttpLoadTextCallBack{
        public void textLoaded(String text);
    }
}
