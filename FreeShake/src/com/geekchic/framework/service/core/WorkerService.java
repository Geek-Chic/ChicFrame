/**
 * @Title: WorkerService.java
 * @Package com.geekchic.framework.service
 * @Description: 工作Service，用于网络，数据库请求处理
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service.core;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

/**
 * @ClassName: WorkerService
 * @Descritpion: 工作Service，用于网络，数据库请求处理
 * @author evil
 * @date May 1, 2014
 */
public abstract class WorkerService extends Service {
	private static final long STOP_SELF_DELAY = 30000L;
	/**
	 * 工作线程池
	 */
	private ExecutorService mThreadPool;
	/**
	 * 如果onHandleIntent返回之前进程死掉，是否重投
	 */
	private boolean mRedelivery;
	/**
	 * 线程消息队列
	 */
	private volatile Looper mServiceLooper;
	/**
	 * 线程控制Handler
	 */
	private volatile Handler mServiceHandler;
	/**
	 * 线程池回调队列
	 */
	private ArrayList<Future<?>> mFutureList;

	/**
	 * 停止服务
	 */
	private final Runnable mStopSelfRunnable = new Runnable() {
		@Override
		public void run() {
			stopSelf();
		}
	};
	/**
	 * 工作线程回调监听处理
	 */
	private final Runnable mWorkDoneRunnable = new Runnable() {

		@Override
		public void run() {
			if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
				throw new IllegalStateException(
						"This runnable can only be called in the Main thread!");
			}
			final ArrayList<Future<?>> futureList = mFutureList;
			for (int i = 0; i < futureList.size(); i++) {
				if (futureList.get(i).isDone()) {
					futureList.remove(i);
					i--;
				}
			}
			/**
			 * 如果无操作请求，则STOP_SELF_DELAY后停止服务
			 */
			if (futureList.isEmpty()) {
				mServiceHandler.postDelayed(mStopSelfRunnable, STOP_SELF_DELAY);
			}
		}
	};
	   private class WorkerRunnable implements Runnable {
	        private final Intent mIntent;

	        public WorkerRunnable(Intent intent) {
	            mIntent = intent;
	        }

	        public void run() {
	            onHandleIntent(mIntent);
	            mServiceHandler.removeCallbacks(mWorkDoneRunnable);
	            mServiceHandler.post(mWorkDoneRunnable);
	        }
	    }
	@Override
	public void onCreate() {
		super.onCreate();
		int maximumNumberOfThreads = getMaximumNumberOfThreads();
		if (maximumNumberOfThreads <= 0) {
			throw new IllegalArgumentException(
					"Maximum number of threads must be " + "strictly positive");
		}
		mThreadPool = Executors.newFixedThreadPool(maximumNumberOfThreads);
		mServiceHandler = new Handler();
		mFutureList = new ArrayList<Future<?>>();

	}

	protected int getMaximumNumberOfThreads() {
		return 1;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		mServiceHandler.removeCallbacks(mStopSelfRunnable);
		Future<?> future=mThreadPool.submit(new WorkerRunnable(intent));
		mFutureList.add(future);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		onStart(intent, startId);
		return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mThreadPool.shutdown();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 是否重传
	 * 
	 * @param enabled
	 */
	public void setIntentRedelivery(boolean enabled) {
		mRedelivery = enabled;
	}

	/**
	 * 处理传入的请求
	 * 
	 * @param intent
	 */
	protected abstract void onHandleIntent(Intent intent);
}
