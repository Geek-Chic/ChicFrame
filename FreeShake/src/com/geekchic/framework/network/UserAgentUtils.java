/**
 * @Title: UserAgentUtils.java
 * @Package com.geekchic.framework.network
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.network;

import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;

/**
 * @ClassName: UserAgentUtils
 * @Descritpion: 用户代理工具类
 * @author evil
 * @date May 2, 2014
 */
public class UserAgentUtils {

	private static String sUserAgent;

	private UserAgentUtils() {
		// No public constructor
	}

	/**
	 * Get the User-Agent with the following syntax:
	 * <p>
	 * Mozilla/5.0 (Linux; U; Android {Build.VERSION.RELEASE};
	 * {locale.toString()}[; {Build.MODEL}] [; Build/{Build.ID}])
	 * {getPackageName()}/{getVersionCode()}
	 * 
	 * @param context
	 *            The context to use to generate the User-Agent.
	 * @return The User-Agent.
	 */
	public synchronized static String get(Context context) {
		if (context == null) {
			throw new NullPointerException("Context cannot be null");
		}
		if (sUserAgent == null) {
			sUserAgent = generate(context);
		}
		return sUserAgent;
	}

	private static String generate(Context context) {
		StringBuilder sb = new StringBuilder();

		sb.append("Mozilla/5.0 (Linux; U; Android");
		sb.append(Build.VERSION.RELEASE);
		sb.append("; ");
		sb.append(Locale.getDefault().toString());

		String model = Build.MODEL;
		if (!TextUtils.isEmpty(model)) {
			sb.append("; ");
			sb.append(model);
		}

		String buildId = Build.ID;
		if (!TextUtils.isEmpty(buildId)) {
			sb.append("; Build/");
			sb.append(buildId);
		}

		sb.append(") ");

		int versionCode = 0;
		String packageName = context.getPackageName();
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(packageName, 0);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// Keep the versionCode 0 as default.
		}

		sb.append(packageName);
		sb.append("/");
		sb.append(versionCode);

		return sb.toString();
	}

}
