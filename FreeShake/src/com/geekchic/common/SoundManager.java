package com.geekchic.common;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
/**
 * @ClassName: SoundManager
 * @Descritpion: 音频管理 
 * @author evil
 * @date May 7, 2014
 */
public class SoundManager {
	/**
	 * TAG
	 */
	private static final String TAG="SoundManager";
	/**
	 * 单例
	 */
	private SoundManager sInstance=null;
	/**
	 * SoundPool
	 */
	private SoundPool mSoundPool;
	/**
	 * 系统音频管理
	 */
	private AudioManager mAudioManager;
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 音频数据源
	 */
	private Map mSoundPoolMap;
	/**
	 * SoundManager构造函数
	 */
	private SoundManager(){
		mSoundPoolMap=new HashMap<Integer, Integer>();
	}
	/**
	 * 单例
	 * @return
	 */
	public synchronized SoundManager getInstance(){
		if(sInstance==null){
			sInstance=new SoundManager();
		}
		return sInstance;
	}
	/**
	 * 初始化
	 * @param pContext
	 */
	public  void initSounds(Context pContext){
		mContext=pContext;
		mSoundPool=new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mAudioManager=(AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}
	/**
	 * 添加音频
	 * @param pIndex
	 * @param pSoundID
	 */
    public void addSound(int pIndex,int pSoundID){
    	int soundLoadedID=mSoundPool.load(mContext, pSoundID,1);
    	mSoundPoolMap.put(pIndex,soundLoadedID);
    }
    /**
     * 播放音频
     * @param pIndex
     * @param pLoop
     */
    public void playSound(int pIndex,boolean pLoop){
    	float curStreamVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    	float maxStreamVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	float streamVolume=curStreamVolume/maxStreamVolume;
    	int curSoundID=(Integer) mSoundPoolMap.get(pIndex);
    	int loop=pLoop?-1:0;
    	mSoundPool.play( curSoundID, streamVolume, streamVolume, 1, loop,1f);
    }
    
}
