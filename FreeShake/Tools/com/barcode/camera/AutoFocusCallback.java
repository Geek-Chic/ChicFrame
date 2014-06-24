package com.barcode.camera;

import android.hardware.Camera;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AutoFocusCallback implements Camera.AutoFocusCallback {
	/**
	 * TAG
	 */
	private static final String TAG = "AutoFocusCallback";
	/**
	 * auto focus interval
	 */
	private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
	/**
	 * autofocus handler
	 */
	private Handler mAutoFocusHandler;
	/**
	 * autofocus  message id
	 */
	private int mAutoFocusMessage;

	/**
	 * set message handler
	 * 
	 * @param autoFocusHandler
	 * @param autoFocusMessage
	 */
	public void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
		this.mAutoFocusHandler = autoFocusHandler;
		this.mAutoFocusMessage = autoFocusMessage;
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (mAutoFocusHandler != null) {
			Message message = mAutoFocusHandler.obtainMessage(
					mAutoFocusMessage, success);
			mAutoFocusHandler
					.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
			mAutoFocusHandler = null;
		} else {
			Log.d(TAG, "Got auto-focus callback, but no handler for it");
		}
	}

}
