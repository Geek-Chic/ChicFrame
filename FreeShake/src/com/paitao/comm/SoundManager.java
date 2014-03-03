package com.paitao.comm;

import java.util.HashMap;
import java.util.Map;

import android.R.plurals;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool mSoundPool;
	private AudioManager mAudioManager;
	private Context mContext;
	private Map mSoundPoolMap;
	public SoundManager(){
		mSoundPoolMap=new HashMap<Integer, Integer>();
	}
	public  void initSounds(Context pContext){
		mContext=pContext;
		mSoundPool=new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mAudioManager=(AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}
    public void addSound(int pIndex,int pSoundID){
    	int soundLoadedID=mSoundPool.load(mContext, pSoundID,1);
    	mSoundPoolMap.put(pIndex,soundLoadedID);
    }
    public void playSound(int pIndex,boolean pLoop){
    	float curStreamVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    	float maxStreamVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	float streamVolume=curStreamVolume/maxStreamVolume;
    	int curSoundID=(Integer) mSoundPoolMap.get(pIndex);
    	int loop=pLoop?-1:0;
    	mSoundPool.play( curSoundID, streamVolume, streamVolume, 1, loop,1f);
    }
    
}
