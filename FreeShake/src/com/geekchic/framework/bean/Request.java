/**
 * @Title: Request.java
 * @Package com.geekchic.framework.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
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
	 * 存储数据
	 */
	private Bundle mBundle = new Bundle();

	/**
	 * Request构造函数
	 * 
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
	@SuppressWarnings("unchecked")
	private Request(final Parcel in) {
		mRequestType = in.readInt();
		mMemoryCacheDataEnabled = in.readInt() == 1;
		mBundle = in.readBundle();

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

	/**
	 * 存储Boolean值
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, boolean value) {
		removeFromRequestData(name);
		mBundle.putBoolean(name, value);
		return this;
	}

	/**
	 * 存储byte值
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, byte value) {
		removeFromRequestData(name);
		mBundle.putByte(name, value);
		return this;
	}

	/**
	 * 存储char值
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, char value) {
		removeFromRequestData(name);
		mBundle.putChar(name, value);
		return this;
	}

	/**
	 * 存储short
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, short value) {
		removeFromRequestData(name);
		mBundle.putShort(name, value);
		return this;
	}

	/**
	 * 存储int
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, int value) {
		removeFromRequestData(name);
		mBundle.putInt(name, value);
		return this;
	}

	/**
	 * 存储long
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, long value) {
		removeFromRequestData(name);
		mBundle.putLong(name, value);
		return this;
	}

	/**
	 * 存储float
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, float value) {
		removeFromRequestData(name);
		mBundle.putFloat(name, value);
		return this;
	}

	/**
	 * 存储double
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, double value) {
		removeFromRequestData(name);
		mBundle.putDouble(name, value);
		return this;
	}

	/**
	 * 存储String
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, String value) {
		removeFromRequestData(name);
		mBundle.putString(name, value);
		return this;
	}

	/**
	 * 存储CharSequence
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, CharSequence value) {
		removeFromRequestData(name);
		mBundle.putCharSequence(name, value);
		return this;
	}
	public Request putByteArray(String name,byte[] bytes){
		removeFromRequestData(name);
		mBundle.putByteArray(name, bytes);
		return this;
	}

	public Request putList(String name, ArrayList<? extends Parcelable> value) {
		removeFromRequestData(name);
		mBundle.putParcelableArrayList(name, value);
		return this;
	}

	/**
	 * 存储Parcelable
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Request put(String name, Parcelable value) {
		removeFromRequestData(name);
		mBundle.putParcelable(name, value);
		return this;
	}

	/**
	 * 去除重复
	 * 
	 * @param name
	 */
	private void removeFromRequestData(String name) {
		if (mBundle.containsKey(name)) {
			mBundle.remove(name);
		}
	}

	public boolean getBoolean(String name) {
		return mBundle.getBoolean(name);
	}

	/**
	 * Returns the value associated with the given name transformed as "1" if
	 * true or "0" if false. If no mapping of the desired type exists for the
	 * given name, "0" is returned.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The int String representation of the boolean value.
	 */
	public String getBooleanAsIntString(String name) {
		boolean value = getBoolean(name);
		return value ? "1" : "0";
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(boolean)}), or "false" if no mapping of the
	 * desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getBooleanAsString(String name) {
		boolean value = getBoolean(name);
		return String.valueOf(value);
	}

	/**
	 * Returns the value associated with the given name, or (byte) 0 if no
	 * mapping of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A byte value.
	 */
	public byte getByte(String name) {
		return mBundle.getByte(name);
	}

	/**
	 * Returns the value associated with the given name, or (char) 0 if no
	 * mapping of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A char value.
	 */
	public char getChar(String name) {
		return mBundle.getChar(name);
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(char)}), or "0" if no mapping of the desired
	 * type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getCharAsString(String name) {
		char value = getChar(name);
		return String.valueOf(value);
	}

	/**
	 * Returns the value associated with the given name, or (short) 0 if no
	 * mapping of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A short value.
	 */
	public short getShort(String name) {
		return mBundle.getShort(name);
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(int)}), or "0" if no mapping of the desired
	 * type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getShortAsString(String name) {
		short value = getShort(name);
		return String.valueOf(value);
	}

	/**
	 * Returns the value associated with the given name, or 0 if no mapping of
	 * the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return An int value.
	 */
	public int getInt(String name) {
		return mBundle.getInt(name);
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(int)}), or "0" if no mapping of the desired
	 * type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getIntAsString(String name) {
		int value = getInt(name);
		return String.valueOf(value);
	}

	/**
	 * Returns the value associated with the given name, or 0L if no mapping of
	 * the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A long value.
	 */
	public long getLong(String name) {
		return mBundle.getLong(name);
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(long)}), or "0" if no mapping of the desired
	 * type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getLongAsString(String name) {
		long value = getLong(name);
		return String.valueOf(value);
	}

	/**
	 * Returns the value associated with the given name, or 0.0f if no mapping
	 * of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A float value.
	 */
	public float getFloat(String name) {
		return mBundle.getFloat(name);
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(float)}), or "0" if no mapping of the
	 * desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getFloatAsString(String name) {
		float value = getFloat(name);
		return String.valueOf(value);
	}

	/**
	 * Returns the value associated with the given name, or 0.0 if no mapping of
	 * the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A double value.
	 */
	public double getDouble(String name) {
		return mBundle.getDouble(name);
	}

	/**
	 * Returns the value associated with the given name transformed as a String
	 * (using {@link String#valueOf(double)}), or "0" if no mapping of the
	 * desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return The String representation of the boolean value.
	 */
	public String getDoubleAsString(String name) {
		double value = getDouble(name);
		return String.valueOf(value);
	}

	/**
	 * 获取Arraylist型数据
	 * 
	 * @param name
	 * @return
	 */
	public<T extends Parcelable>  ArrayList<T> getArrayList(String name) {
		return mBundle.getParcelableArrayList(name);
	}

	/**
	 * Returns the value associated with the given name, or null if no mapping
	 * of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A String value.
	 */
	public String getString(String name) {
		return mBundle.getString(name);
	}

	/**
	 * Returns the value associated with the given name, or null if no mapping
	 * of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A CharSequence value.
	 */
	public CharSequence getCharSequence(String name) {
		return mBundle.getCharSequence(name);
	}
      public byte[] getByteArray(String name){
    	  return mBundle.getByteArray(name);
      }
	/**
	 * Returns the value associated with the given name, or null if no mapping
	 * of the desired type exists for the given name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return A Parcelable value.
	 */
	public Parcelable getParcelable(String name) {
		return mBundle.getParcelable(name);
	}

	/**
	 * Sets the ClassLoader to use by the underlying Bundle when getting
	 * Parcelable objects.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use by the underlying Bundle when getting
	 *            Parcelable objects.
	 */
	public void setClassLoader(ClassLoader classLoader) {
		mBundle.setClassLoader(classLoader);
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
		dest.writeBundle(mBundle);
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
