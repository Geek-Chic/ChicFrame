/**
 * @Title: ZXingActivity.java
 * @Package com.geekchic.wuyou.ui.tools
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 6, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.tools;

import java.io.IOException;
import java.util.Collection;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.barcode.camera.CameraManager;
import com.barcode.core.CaptureActivityHandler;
import com.barcode.core.InactivityTimer;
import com.barcode.core.ViewfinderView;
import com.barcode.executor.ResultHandler;
import com.geekchic.common.log.Logger;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

/**
 * @ClassName: ZXingActivity
 * @Descritpion: 扫描二维码
 * @author evil
 * @date May 6, 2014
 */
public class ZXingActivity extends BaseTitleBarActivity implements SurfaceHolder.Callback {
    /**
     * TAG
     */
	private static final String TAG="ZXingActivity";
	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private ImageView back;
	private Result lastResult;
	private boolean hasSurface;
	private IntentSource source;
	private Collection<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	// private Button from_gallery;
	private final int from_photo = 010;
	static final int PARSE_BARCODE_SUC = 3035;
	static final int PARSE_BARCODE_FAIL = 3036;
	String photoPath;
	ProgressDialog mProgress;

	enum IntentSource {
		ZXING_LINK, NONE
	}
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		cameraManager = new CameraManager(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);
    }
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getCaptureHandler() {
		return getHandler();
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	// 解析二维码
	public void handleDecode(Result rawResult, Bitmap barcode) {
		inactivityTimer.onActivity();
		lastResult = rawResult;
		ResultHandler resultHandler = new ResultHandler(parseResult(rawResult));

		if (barcode == null) {
			android.util.Log.i("steven",
					"rawResult.getBarcodeFormat().toString():"
							+ rawResult.getBarcodeFormat().toString());
			android.util.Log.i("steven", "resultHandler.getType().toString():"
					+ resultHandler.getType().toString());
			android.util.Log.i("steven", "resultHandler.getDisplayContents():"
					+ resultHandler.getDisplayContents());
		} else {
			// 对扫到的二维码进行解析
			parseBarCode(resultHandler.getDisplayContents().toString());
		}
	}

	// 解析二维码
	private void parseBarCode(String msg) {
		// // 手机震动
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(100);
		// mProgress = ProgressDialog.show(ZXingActivity.this, null,
		// "已扫描，正在处理···",true,true);
		// mProgress.setOnDismissListener(new
		// DialogInterface.OnDismissListener() {
		// public void onDismiss(DialogInterface dialog) {
		// restartPreviewAfterDelay(1l);
		// }
		// });
		//
		// // 判断是否符合基本的json格式
		// if (!msg.matches("^\\{.*")) {
		// showDialog(msg);
		// } else {
		// try {
		// Barcode barcode = Barcode.parse(msg);
		// Log.i(TAG, barcode.toString());
		// int type = barcode.getType();
		// if (barcode.isRequireLogin()) {
		// if (!ac.isLogin()) {
		// UIHelper.showLoginDialog(Capture.this);
		// return;
		// }
		// }
		// switch (type) {
		// case Barcode.SIGN_IN:// 签到
		// signin(barcode);
		// break;
		// default:
		// break;
		// }
		// } catch (AppException e) {
		// UIHelper.ToastMessage(this, "json数据解析异常");
		// mProgress.dismiss();
		// }
		// }
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	private static ParsedResult parseResult(Result rawResult) {
		return ResultParser.parseResult(rawResult);
	}
	@Override
	protected void onResume() {
		super.onResume();
		handler = null;
		lastResult = null;
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		inactivityTimer.onResume();
		source = IntentSource.NONE;
		decodeFormats = null;
	}
	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		if (mProgress!= null && mProgress.isShowing()) {
			mProgress.dismiss();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		if (mProgress!= null) {
			mProgress.dismiss();
		}
		super.onDestroy();
	}
	// 初始化照相机，CaptureActivityHandler解码
		private void initCamera(SurfaceHolder surfaceHolder) {
			if (surfaceHolder == null) {
				throw new IllegalStateException("No SurfaceHolder provided");
			}
			if (cameraManager.isOpen()) {
				Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
				return;
			}
			try {
				cameraManager.openDriver(surfaceHolder);
				if (handler == null) {
					handler = new CaptureActivityHandler(this, decodeFormats, characterSet, cameraManager);
				}
			} catch (IOException ioe) {
				Logger.w(TAG, ioe.getMessage());
			} catch (RuntimeException e) {
				Logger.w(TAG, "Unexpected error initializing camera", e);
			}
		}
	@Override
	public int getLayoutId() {
		return R.layout.tools_zxing_layout;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("扫描二维码");
		setLeftButton(R.drawable.btn_common_tab_back_selector,
				mBackClickListener);
		return true;
	}
  
	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
   
	protected void handleStateMessage(Message msg) {
		switch (msg.what) {
		case PARSE_BARCODE_SUC:
			// 解析二维码
			// parseBarCode((String) msg.obj);
			break;
		case PARSE_BARCODE_FAIL:
			// showDialog((String) msg.obj);
			if (mProgress != null && mProgress.isShowing()) {
				mProgress.dismiss();
			}
			new AlertDialog.Builder(ZXingActivity.this)
					.setTitle("提示")
					.setMessage("扫描失败！")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
			break;
		}
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Logger.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	};
}
