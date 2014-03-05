package com.geekchic.base.http;


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

import com.geekchic.common.LogUtil;

public class CommHttpClient
{
    private static final int TIMEOUT=10000;
    private static final int TIMEOUT_SOCKET=15000;
    
    public static final String CTWAP="ctwap";
    public static final String CMWAP="cmwap";
    public static final String WAP_3G="3gwap";
    public static final String UNIWAP="uniwap";
    
    /** @Fields TYPE_NET_WORK_DISABLED : ���粻���� */
    public static final int TYPE_NET_WORK_DISABLED = 0;
    /** @Fields TYPE_CM_CU_WAP : �ƶ���ͨwap10.0.0.172 */
    public static final int TYPE_CM_CU_WAP = 4;
    /** @Fields TYPE_CT_WAP : ����wap 10.0.0.200 */
    public static final int TYPE_CT_WAP = 5;
    /** @Fields TYPE_OTHER_NET : ����,�ƶ�,��ͨ,wifi ��net���� */
    public static final int TYPE_OTHER_NET = 6;
    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    private CommHttpClient(){}
    public static HttpClient getNewInstance(Context context){
        HttpClient newInstance;
        HttpParams params=new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        //�������ӵȴ�ʱ��
        ConnManagerParams.setTimeout(params, 5000);
        //��ʱʱ��
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        //socket��ʱʱ��
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
                 LogUtil.v("��������Ϊcm_cu_wap,���ô���10.0.0.200����www");
                break;
            
            case TYPE_CM_CU_WAP:
                HttpHost proxy=new HttpHost("10.0.0.172",80,"http");
                newInstance.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
                LogUtil.v("��������Ϊcm_cu_wap,���ô���10.0.0.172����www");
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
     * �ж�Network�ľ������ͣ���ͨ�ƶ�wap������wap������net��
     * @param context
     * @return Netwrok���ͱ�ʶ
     */
    public static int checkNetworkType(Context context){
        try
        {
            final ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo ==null || !networkInfo.isAvailable()){
                 //NewworkInfoΪ�ջ��߲����õ�ʱ��������ǵ�ǰû�п�������
                 //������Щ���Ż������Կ���������
                //����Ҫ��Ȼ�����������磬Ȼ����socket�в�׽�쳣�����ж����ж����û���ʾ
                 return TYPE_NET_WORK_DISABLED;
            }else{
                int netType=networkInfo.getType();
                if(netType==ConnectivityManager.TYPE_WIFI){
                    LogUtil.i("wifi����");
                }else if(netType==ConnectivityManager.TYPE_MOBILE){
                    //�ж��Ƿ����wap���˴�����ͨ��getExtraInfo��ȡ��������ж�����
                    //��ΪĿǰ���Ŷ��ֻ��ͽ������ƴ�Ϊ#777������null
                    //���Ż���wap�������Ҫ���ƶ���ͨwap����������һ���û�������룬���Կ���ͨ����������ж�
                    final Cursor cursor=context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
                    if(cursor!=null){
                        cursor.moveToFirst();
                        final String userString=cursor.getString(cursor.getColumnIndex("user"));
                            if(!TextUtils.isEmpty(userString)){
                              LogUtil.i("����"+cursor.getString(cursor.getColumnIndex("proxy")));
                                if(userString.startsWith(CTWAP)){
                                    LogUtil.i("��������");
                                    return TYPE_CT_WAP;
                                }
                              
                            }
                    }
                    cursor.close();
                    //�ж��ƶ���ͨwap��
                    //����ͨ��getString(cursor.getColumnIndex("proxy"))��ȡ������Ϣ
                    //���жϽ���㣬10.0.0.172�����ƶ���ͨ��10.0.0.200���ǵ���
                    //ʵ�ʿ�������в��������л������ܻ�ȡ�������Ĵ�����Ϣ����������M9
                   //���Բ���getExtraInfo����ȡ����������ж�
                    String netMode=networkInfo.getExtraInfo();
                    LogUtil.i("netmode"+netMode);
                    if(netMode!=null){
                        netMode=netMode.toLowerCase();
                        if(netMode.equals(CMWAP)||netMode.equals(CMWAP)){
                            LogUtil.i("�ƶ���ͨwap����");
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

