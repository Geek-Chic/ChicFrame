/**
 * @Title: UpdateManager.java
 * @Package com.geekchic.base.update
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 13, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geekchic.common.log.Logger;

/**
 * @ClassName: UpdateManager
 * @Descritpion: 更新管理类
 * @author jp
 * @date Apr 13, 2014
 */
public class UpdateManager {
	private static final String TAG=UpdateManager.class.getName();
	private Context mContext;
	public UpdateManager(Context context){
		this.mContext=context;
	}
	public void check(Context context,UpdateOptions options){
		RequestQueue requestQueue=Volley.newRequestQueue(context);
		Logger.d(TAG, "fuck");
		StringRequest stringRequest=new StringRequest(Request.Method.GET,options.getCheckURL(), new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
//				Logger.d(TAG, response);
				UpdateXmlParser updateXmlParser=new UpdateXmlParser();
				UpdateInfo updateInfo=updateXmlParser.parse(response);
				Logger.d(TAG, updateInfo.getApkUrl());
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Logger.d(TAG, error.getMessage());
			}
		});
		requestQueue.add(stringRequest);
	}
	
	/**
    * 安装APK<BR>
    * @param context
    * @param filePath
    */
   private static void install(Context context, String filePath) {
       Intent i = new Intent(Intent.ACTION_VIEW);
       i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
       i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       context.startActivity(i);
   }
}
