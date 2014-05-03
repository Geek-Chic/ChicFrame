/**
 * @Title: Request.java
 * @Package com.geekchic.framework.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: Request
 * @Descritpion: 请求JavaBean
 * @author evil
 * @date May 2, 2014
 */
public class Request implements Parcelable {
	/**
	 * 是否缓存
	 */
	private boolean mMemoryCacheDataEnabled = false;
	/**
	 * 请求码
	 */
	private int mRequestType = -1;
	/**
	 * Request构造函数
	 * @param requestType
	 */
    public Request(int requestType) {
        mRequestType = requestType;
    }
	/**
	 * Request构造函数
	 * 
	 * @param in
	 */
	private Request(final Parcel in) {
		mRequestType = in.readInt();
		mMemoryCacheDataEnabled = in.readInt() == 1;
	}

	public boolean isMemoryCacheDataEnabled() {
		return mMemoryCacheDataEnabled;
	}

	public void setMemoryCacheDataEnabled(boolean mMemoryCacheDataEnabled) {
		this.mMemoryCacheDataEnabled = mMemoryCacheDataEnabled;
	}

	public int getRequestType() {
		return mRequestType;
	}

	public void setRequestType(int mRequestType) {
		this.mRequestType = mRequestType;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
         dest.writeInt(mRequestType);
         dest.writeInt(mMemoryCacheDataEnabled ? 1 : 0);
	}

	public static final Creator<Request> CREATOR = new Creator<Request>() {
		public Request createFromParcel(final Parcel in) {
			return new Request(in);
		}

		public Request[] newArray(final int size) {
			return new Request[size];
		}
	};
}
