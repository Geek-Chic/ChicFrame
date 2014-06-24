/**
 * @Title: QrScanFragment.java
 * @Package com.geekchic.wuyou.ui.tools
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 6, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.tools;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.barcode.ZxingManager;
import com.barcode.core.FinishListener;
import com.barcode.core.ViewfinderView;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;

/**
 * @ClassName: QrScanFragment
 * @Descritpion: 二维码扫描Fragment
 * @author evil
 * @date May 6, 2014
 */
public class QrScanFragment extends BaseFrameFragment  implements SurfaceHolder.Callback{
	/**
	 * TAG
	 */
	private static final String TAG = "QrScanFragment";
	private SurfaceView surfaceView;
	/**
	 * 是否已创建SurfaceView
	 */
	private boolean hasSurface;
	private ViewfinderView mViewfinderView;
	private ZxingManager mZxingManager;
	//private Button from_gallery;
	private final int from_photo = 010;
	static final int PARSE_BARCODE_SUC = 3035;
	static final int PARSE_BARCODE_FAIL = 3036;
	String photoPath;
	ProgressDialog mProgress;

	enum IntentSource {
		ZXING_LINK, NONE
	}

	Handler barHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PARSE_BARCODE_SUC:
				// 解析二维码
//				parseBarCode((String) msg.obj);
				break;
			case PARSE_BARCODE_FAIL:
				//showDialog((String) msg.obj);
				if (mProgress != null && mProgress.isShowing()) {
					mProgress.dismiss();
				}
				new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("扫描失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
				break;
			}
			super.handleMessage(msg);
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.tools_zxing_layout, null);
		surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);
		mViewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);
		mZxingManager=new ZxingManager(getActivity(), mViewfinderView);
		mZxingManager.setViewfinderView(mViewfinderView);
		hasSurface = false;
		return view;
	}
//	/**
//	 * 解析本地的二维
//	 * @param path
//	 * @return
//	 */
//	public String parsLocalPic(String path) {
//		String parseOk = null;
//		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//		hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
//
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true; // 先获取原大小
//		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
//		options.inJustDecodeBounds = false; // 获取新的大小
//		// 缩放比
//		int be = (int) (options.outHeight / (float) 200);
//		if (be <= 0)
//			be = 1;
//		options.inSampleSize = be;
//		bitmap = BitmapFactory.decodeFile(path, options);
//		int w = bitmap.getWidth();
//		int h = bitmap.getHeight();
//		System.out.println(w + "   " + h);
//		RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
//		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//		QRCodeReader reader2 = new QRCodeReader();
//		Result result;
//		try {
//			result = reader2.decode(bitmap1, hints);
//			android.util.Log.i("steven", "result:" + result);
//			parseOk = result.getText();
//
//		} catch (NotFoundException e) {
//			parseOk = null;
//		} catch (ChecksumException e) {
//			parseOk = null;
//		} catch (FormatException e) {
//			parseOk = null;
//		}
//		return parseOk;
//	}
//	
	

	// 创建预览
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	

	// 初始化照相机，CaptureActivityHandler解码
	private void initCamera(SurfaceHolder surfaceHolder) {
		if(null!=mZxingManager){
			mZxingManager.initCamera(surfaceHolder);
		}
	}
	
	/**
	 * 初始化照相机失败显示窗口
	 */
	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(R.string.zxing_camera_error));
		builder.setPositiveButton("确定", new FinishListener(getActivity()));
		builder.setOnCancelListener(new FinishListener(getActivity()));
		builder.show();
	}

  @Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	SurfaceHolder surfaceHolder = surfaceView.getHolder();
	if (hasSurface)
	{
		initCamera(surfaceHolder);
	}
	else
	{
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
}
   @Override
public void onPause() {
	super.onPause();
	mZxingManager.closeDriver();
}
	@Override
	public void onDestroy() {
		mZxingManager.shutdown();
		super.onDestroy();
		
	}
	
}
