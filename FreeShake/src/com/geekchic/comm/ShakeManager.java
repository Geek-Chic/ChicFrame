package com.geekchic.comm;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.provider.ContactsContract.CommonDataKinds.Event;

public class ShakeManager implements SensorEventListener{
	//speed threshold
	private static final int SPEED_SHAKE_THRESHOLD=3000;
	//detect interval
	private static final int UPDATE_INTERVAL_TIME=70;
	private SensorManager mSensorManager;
    private Sensor mSensor;
    private Vibrator mVibrator;
    private ShakeListener mOnShakeLinstener;
    private Context mContext;
    private float lastX;
    private float lastY;
    private float lastZ;
    private long mLastUpdateTime;
    public ShakeManager(Context pContext){
    	mContext=pContext;
    	
    }
    public void start(){
    	mSensorManager=(SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
    	mVibrator=(Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
    	if(mSensorManager!=null){
    		mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	}
    	if(mSensor!=null){
    		mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_GAME);
    	}
    }
    public void stop(){
    	mSensorManager.unregisterListener(this);
    }
    public void setOnShakeListener(ShakeListener pShakeListener){
    	this.mOnShakeLinstener=pShakeListener;
    }
    public void startVibrate(int pVibrateTime){
    	if(pVibrateTime>0){
    		mVibrator.vibrate(pVibrateTime);
    	}
    }
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		long curUpdateTime=System.currentTimeMillis();
		long timeInterval=curUpdateTime-mLastUpdateTime;
		if(timeInterval<UPDATE_INTERVAL_TIME){
			return;
		}
		mLastUpdateTime=curUpdateTime;
		float x=event.values[0];
		float y=event.values[1];
		float z=event.values[2];
		
		float deltaX=x-lastX;
		float deltaY=y-lastY;
		float deltaZ=z-lastZ;
		
		lastX=x;
		lastY=y;
		lastZ=z;
		
		double speed=Math.sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ)/timeInterval*10000;
		if(speed>=SPEED_SHAKE_THRESHOLD){
			mOnShakeLinstener.onShakeListener();
			
		}

	}
	   public interface ShakeListener {
           public void onShakeListener();
       }

}
