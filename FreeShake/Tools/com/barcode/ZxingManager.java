/**
 * @Title: ZxingManager.java
 * @Package com.barcode
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 7, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.barcode;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceHolder;

import com.barcode.camera.CameraManager;
import com.barcode.core.CaptureActivityHandler;
import com.barcode.core.InactivityTimer;
import com.barcode.core.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * @ClassName: ZxingManager
 * @Descritpion: ZXing管理类
 * @author evil
 * @date May 7, 2014
 */
public class ZxingManager {
	/**
	 * TAG
	 */
	private static final String TAG = "ZxingManager";
	/**
	 * 扫描框
	 */
	private ViewfinderView mViewfinderView;
	/**
	 * 扫描消息Handler
	 */
	private CaptureActivityHandler mCaptureActivityHandler;
	/**
	 * 相机管理
	 */
	private CameraManager cameraManager;
	/**
	 * 编码
	 */
	private Vector<BarcodeFormat> decodeFormats;
	/**
	 * 字符集
	 */
	private String characterSet;
	/**
	 * Activity生命管理
	 */
	private InactivityTimer inactivityTimer;
	/**
	 * Activity实例
	 */
	private Activity mActivity;
	/**
	 * 扫描监听器
	 */
	private OnZxingScanListener listener;

	enum IntentSource {
		ZXING_LINK, NONE
	}

	public ZxingManager(Activity activity, ViewfinderView viewfinderView) {
		this.mActivity = activity;
		inactivityTimer = new InactivityTimer(mActivity);
		CameraManager.init(activity);
		this.cameraManager = CameraManager.get();
		this.mViewfinderView = viewfinderView;
		mViewfinderView.setCameraManager(cameraManager);
	}

	public CaptureActivityHandler getHandler() {
		return mCaptureActivityHandler;
	}

	public void setViewfinderView(ViewfinderView view) {
		this.mViewfinderView = view;
	}

	public ViewfinderView getViewfinderView() {
		return mViewfinderView;
	}

	/**
	 * 解析二维码
	 * 
	 * @param result
	 * @param barBitmap
	 */
	public void handleDecode(Result rawResult, Bitmap barBitmap) {

		inactivityTimer.onActivity();
		// ResultHandler resultHandler = new
		// ResultHandler(parseResult(rawResult));

		if (barBitmap == null) {
			android.util.Log.i("steven",
					"rawResult.getBarcodeFormat().toString():"
							+ rawResult.getBarcodeFormat().toString());
		} else {
			// 对扫到的二维码进行解析
			// parseBarCode(resultHandler.getDisplayContents().toString());
		}

	}

	public void drawViewFinder() {
		mViewfinderView.drawViewfinder();
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public void initCamera(SurfaceHolder surfaceHolder) {

		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		// if (cameraManager.isOpen()) {
		// Log.w(TAG,
		// "initCamera() while already open -- late SurfaceView callback?");
		// return;
		// }
		try {
			cameraManager.openDriver(surfaceHolder);
			if (mCaptureActivityHandler == null) {
				mCaptureActivityHandler = new CaptureActivityHandler(this,
						decodeFormats, characterSet);
			}
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
		} catch (RuntimeException e) {
			Log.w(TAG, "Unexpected error initializing camera", e);
		}

	}

	/**
	 * 关闭相机
	 */
	public void closeDriver() {
		if (mCaptureActivityHandler != null) {
			mCaptureActivityHandler.quitSynchronously();
			mCaptureActivityHandler = null;
		}
		CameraManager.get().closeDriver();
	}

	/**
	 * 关闭Acitivity
	 */
	public void shutdown() {
		inactivityTimer.shutdown();
	}

	/**
	 * 注册监听器
	 * 
	 * @param listener
	 */
	public void setOnZxingScanListener(OnZxingScanListener listener) {
		this.listener = listener;
	}

	public interface OnZxingScanListener {

	}
}
