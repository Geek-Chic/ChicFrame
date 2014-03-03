package com.geekchic.freeshake.http;


import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.geekchic.comm.LogUtil;

public class CommHttpClient
{
    private static final int TIMEOUT=10000;
    private static final int TIMEOUT_SOCKET=15000;
    
    public static final String CTWAP="ctwap";
    public static final String CMWAP="cmwap";
    public static final String WAP_3G="3gwap";
    public static final String UNIWAP="uniwap";
    
    /** @Fields TYPE_NET_WORK_DISABLED : 网络不可用 */
    public static final int TYPE_NET_WORK_DISABLED = 0;
    /** @Fields TYPE_CM_CU_WAP : 移动联通wap10.0.0.172 */
    public static final int TYPE_CM_CU_WAP = 4;
    /** @Fields TYPE_CT_WAP : 电信wap 10.0.0.200 */
    public static final int TYPE_CT_WAP = 5;
    /** @Fields TYPE_OTHER_NET : 电信,移动,联通,wifi 等net网络 */
    public static final int TYPE_OTHER_NET = 6;
    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    private CommHttpClient(){}
    public static HttpClient getNewInstance(Context context){
        HttpClient newInstance;
        HttpParams params=new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        //尝试连接等待时间
        ConnManagerParams.setTimeout(params, 5000);
        //超时时间
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        //socket超时时间
        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SOCKET);
        
        SchemeRegistry schemeRegistry=new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager connectionManager=new ThreadSafeClientConnManager(params, schemeRegistry);
        newInstance=new DefaultHttpClient(connectionManager, params);
        switch (checkNetworkType(context))
        {
            case TYPE_CT_WAP:
                 HttpHost proxyHost=new HttpHost("10.0.0.200",80,"http");
                 newInstance.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxyHost);
                 LogUtil.v("网络类型为cm_cu_wap,设置代理10.0.0.200访问www");
                break;
            
            case TYPE_CM_CU_WAP:
                HttpHost proxy=new HttpHost("10.0.0.172",80,"http");
                newInstance.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
                LogUtil.v("网络类型为cm_cu_wap,设置代理10.0.0.172访问www");
                break;
        }
        return newInstance;
    }
    public static HttpResponse execute(Context context,HttpUriRequest paramHttpUriRequest) throws ClientProtocolException, IOException{
        HttpResponse response=getNewInstance(context).execute(paramHttpUriRequest);
//        org.apache.http.Header[] dateHeaders=response.getHeaders("Date");
//        if(dateHeaders !=null && dateHeaders.length>0){
//            String value=dateHeaders[0].getValue();
//            Date date=DateParser.parseRFC822(value);
//            
//        }
        return response;
        
    }
    /**
     * 判断Network的具体类型（联通称动wap，电信wap，其他net）
     * @param context
     * @return Netwrok类型标识
     */
    public static int checkNetworkType(Context context){
        try
        {
            final ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo ==null || !networkInfo.isAvailable()){
                 //NewworkInfo为空或者不可用的时候，正常情况是当前没有可用网络
                 //但是有些电信机器，仍可以正常联网
                //所以要依然尝试连接网络，然后在socket中捕捉异常，进行二次判断与用户提示
                 return TYPE_NET_WORK_DISABLED;
            }else{
                int netType=networkInfo.getType();
                if(netType==ConnectivityManager.TYPE_WIFI){
                    LogUtil.i("wifi网络");
                }else if(netType==ConnectivityManager.TYPE_MOBILE){
                    //判断是否电信wap，此处不能通过getExtraInfo获取接入点来判断类型
                    //因为目前电信多种机型接入点名称大都为#777或者是null
                    //电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码，所以可以通过这个进行判断
                    final Cursor cursor=context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
                    if(cursor!=null){
                        cursor.moveToFirst();
                        final String userString=cursor.getString(cursor.getColumnIndex("user"));
                            if(!TextUtils.isEmpty(userString)){
                              LogUtil.i("代理"+cursor.getString(cursor.getColumnIndex("proxy")));
                                if(userString.startsWith(CTWAP)){
                                    LogUtil.i("电信网络");
                                    return TYPE_CT_WAP;
                                }
                              
                            }
                    }
                    cursor.close();
                    //判断移动联通wap：
                    //可以通过getString(cursor.getColumnIndex("proxy"))获取代理信息
                    //来判断接入点，10.0.0.172就是移动联通，10.0.0.200就是电信
                    //实际开发过程中并不是所有机器都能获取到接入点的代理信息，例如魅族M9
                   //所以采用getExtraInfo来获取接入点名字判断
                    String netMode=networkInfo.getExtraInfo();
                    LogUtil.i("netmode"+netMode);
                    if(netMode!=null){
                        netMode=netMode.toLowerCase();
                        if(netMode.equals(CMWAP)||netMode.equals(CMWAP)){
                            LogUtil.i("移动联通wap网络");
                            return TYPE_CM_CU_WAP;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return TYPE_OTHER_NET;
    }
}

