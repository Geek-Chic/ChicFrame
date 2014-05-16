/**
 * @Title: SimpleAsyncTask.java
 * @Package com.geekchic.base.mutitask
 * @Description: 简单异部操作封装
 * @author: evil
 * @date: May 16, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.mutitask;

import java.lang.reflect.Field;
import java.util.concurrent.Executor;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.bean.Request;

/**
 * @ClassName: SimpleAsyncTask
 * @Descritpion: 简单异部操作封装
 * @author evil
 * @date May 16, 2014
 */
public abstract class SimpleAsyncTask<Progress, Result> extends
		AsyncTask<Request, Progress, Result> {
	protected TaskListener<Progress, Result> mTaskListener = null;
    /**
     * SimpleAsyncTask构造函数
     */
	public SimpleAsyncTask() {
		super();
	}
	/**
	 * SimpleAsyncTask构造函数
	 * @param listener
	 */
	public SimpleAsyncTask(TaskListener<Progress, Result> listener) {
		super();
		this.mTaskListener = listener;
	}
	/**
	 * 添加监听器
	 * @param listener
	 */
	public void addOnAsyncTaskLinstener(TaskListener<Progress, Result> listener) {
		this.mTaskListener = listener;
	}
    /**
     * 获取监听器
     * @return
     */
	public TaskListener<Progress, Result> getTaskLinstener() {
		return mTaskListener;
	}
    
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCancelled(Result result) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			super.onCancelled(result);
		} else {
			super.onCancelled();
		}
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (mTaskListener != null) {
			if (result != null) {
				mTaskListener.onSuccess(result);
			}

			mTaskListener.onFinish();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mTaskListener != null) {
			mTaskListener.onStart();
		}
	}

	@Override
	protected void onProgressUpdate(Progress... values) {
		super.onProgressUpdate(values);
		if (mTaskListener != null) {
			mTaskListener.onProgressUpdate(values);
		}
	}
	/**
     * 设置默认的Execotor
     *
     * @param executor
     */
    public static void setDefaultExecutor(Executor executor) {
        Class<?> c = null;
        Field field = null;
        try {
            c = Class.forName("android.os.AsyncTask");
            field = c.getDeclaredField("sDefaultExecutor");
            field.setAccessible(true);
            field.set(null, executor);
            field.setAccessible(false);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Logger.e("IllegalArgumentException",e.toString(), e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e("ClassNotFoundException", e.toString(),e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logger.e("NoSuchFieldException", e.toString(),e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e("IllegalAccessException",e.toString(), e);
        }
    }

    /**
     * 获取默认的Executor
     *
     * @return the default Executor
     */
    public static Executor getDefaultExecutor() {
        Executor exec = null;

        Class<?> c = null;
        Field field = null;
        try {
            c = Class.forName("android.os.AsyncTask");
            field = c.getDeclaredField("sDefaultExecutor");
            field.setAccessible(true);
            exec = (Executor) field.get(null);
            field.setAccessible(false);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Logger.e("IllegalArgumentException",e.toString() ,e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e("ClassNotFoundException",e.toString() ,e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logger.e("NoSuchFieldException",e.toString(),e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e("IllegalAccessException", e.toString(),e);
        }

        return exec;
    }
	protected abstract Result doInBackground(Request... params);
}
