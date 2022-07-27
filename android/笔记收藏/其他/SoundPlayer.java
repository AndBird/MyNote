package net.laizi.cartoon;

import java.util.HashMap;

import net.laizi.tool.CartoonTool;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer implements MediaPlayer.OnCompletionListener{
	//声音资源编码 保存在本地记录
	private static SoundPlayer instance;
	private Context context;
	private AudioManager am;		//通过媒体管理来设置音量
	private byte currentVol,maxVol;	//当前背景音量和游戏音量和最大音量   最大音量15
	private MediaPlayer mp;			//背景音乐播放对象
	private int musicnum = 4;
	private int musicMap[] = new int[musicnum];	//MediaPlayer对应的资源文件
	private boolean m_startLoad;             //开始加载标记
	private boolean m_loadComplete;          //加载完成
	
	private SoundPool soundPool;
	private HashMap<String,Integer> resMap;	//soundPool对应的资源文件
	
	private boolean i_startLoad;             //开始加载标记
	private boolean i_loadComplete;          //加载完成
	private int curBackGroundMusicId;		//记录当前背景音乐的id  用于可以更换背景音乐
	private boolean curFlag;				//记录当前是否循环播放背景音乐
	
	private boolean backGroundMusicFlag;	//背景音乐开关	true--开背景音乐  false---关背景音乐
	private boolean playSoundFlag;			//打牌效果开关       true--开音效  	false----关背景音效
	private short[] keys = new short[53];	//存放声音的下标
	
	public static SoundPlayer getInstance(Context context){
		if(null == instance){
			instance = new SoundPlayer(context);
		}
		return instance;
	}
	
	public SoundPlayer(Context context){
		this.context = context;
		
		this.am = (AudioManager)this.context.getSystemService(Context.AUDIO_SERVICE);
		this.maxVol = (byte)am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		this.currentVol = (byte)am.getStreamVolume(AudioManager.STREAM_MUSIC);
		this.i_startLoad = false;
		this.i_loadComplete = false;
		this.backGroundMusicFlag = true;
		this.playSoundFlag = true;
		
		this.m_startLoad = false;
		this.m_loadComplete = false;
		
		this.curBackGroundMusicId = 0;
	}
	//初始化声音的下标
	private void initSoundIndex(){
		
		for(byte i = 0;i < 53;i++)   //
		{
			keys[i] = i;
		}
	}
	//加载声音资源文件
	public void loadPool(){
//		try{
			if((!this.i_startLoad) && (!this.i_loadComplete)){//没有加载且没加载完
				this.i_startLoad = true;
				//this.initSoundIndex();
				this.soundPool = new SoundPool(16,AudioManager.STREAM_MUSIC,0);
				if(this.soundPool == null){
					System.out.println("soundPool is null");
				}else{
					System.out.println("soundPool is not null");
				}
				if(null == resMap){
					resMap = new HashMap<String,Integer>();
				}
				if(null != this.soundPool){
					resMap = CartoonTool.getSoundIds(context,4,this.soundPool);
				}
			}
			this.i_loadComplete = true;	//声音文件加载完毕
//		}catch(Exception e){
//			Debugs.debug("Exception in loadPool");
//		}
	}
	
	
	//加载背景音乐资源文件
		public void loadMusic(){
//			try{
				if((!this.m_startLoad) && (!this.m_loadComplete)){//没有加载且没加载完
					this.m_startLoad = true;
					musicMap =  CartoonTool.getMusicIds(context,musicnum);					
				}
				this.m_loadComplete = true;	//声音文件加载完毕
//			}catch(Exception e){
//				Debugs.debug("Exception in loadPool");
//			}
		}
	
	
	
	//根据文件名来播放声音
	public void playSound(String soundname){
		if(!this.playSoundFlag){
			System.out.println("播放音效开关关闭了!");
			return;
		}
		while(!this.i_loadComplete){
			Thread.yield();   //暂停当前正在执行的线程对象
		}
		if(null == this.soundPool){
			return;
		}
		
		System.out.println("soundPool name = " + soundname);
		int m = this.resMap.get(soundname);
		this.soundPool.play(m,1, 1, 0, 0, 1);
	}
	
	//停止声音播放
	private void releasePool(){
		if(null != this.soundPool){
			this.soundPool.release();
		}
		if(null != this.resMap){
			this.resMap.clear();
			this.resMap = null;
		}
		this.i_loadComplete = false;
		this.i_startLoad = false;
	} 
	
	//b为true时循环播放，false时顺序播放
	public void startBackGroundSound(boolean b){
		if(!this.backGroundMusicFlag){
			System.out.println("背景音乐开关关闭了!");
			return;
		}
		while(!this.m_loadComplete){
			Thread.yield();   //暂停当前正在执行的线程对象
		}
		System.out.println("music index = " +this.curBackGroundMusicId );
		
		if(null == this.mp  ){
			this.mp = MediaPlayer.create(this.context, musicMap[this.curBackGroundMusicId]);//从0开始播放
			this.mp.setOnCompletionListener(this);
		}
		mp.setLooping(b);//循环播放
		//this.mp.setVolume(currentVol, currentVol);		//设置当前左右声道
		this.mp.start();
		curFlag = b;                //播放方式
	}
	
	//停止背景音乐
	public void stopBackGroundSound(){
		if(null != this.mp){
			this.mp.release();
			this.mp = null;
		}
	}
	//暂停
	public void pauseBackGroundSound(){
		if(null != mp && mp.isPlaying()){
			mp.pause();
		}
	}
	public void resumeBackGroundSound(){
		try{
			mp.stop();
			mp.prepare();
			mp.start();
		}catch(Exception e){
			System.out.println("resumeBackGroundSound err: " + e.toString());
		}
	}
	
	//设置当前音量
	public void setCurrentVol(byte vol){
		System.out.println("调节音量   vol = " + vol);
		this.currentVol = vol;
		if(null != am){
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, AudioManager.FLAG_PLAY_SOUND);
		}
	}
	public void setBackGroundMusicFlag(boolean b){
		System.out.println("设置背景音乐开关! result = " + b); 
		this.backGroundMusicFlag = b;
		if(this.backGroundMusicFlag){
			this.stopBackGroundSound();
			this.startBackGroundSound(curFlag);
		}else{
			this.stopBackGroundSound();
		}
	}
	
	public void setPlaySoundFlag(boolean b){
		System.out.println("设置音效开关! result = " + b);
		this.playSoundFlag = b;
	}
	
	public boolean getPlaySoundFlag(){
		return this.playSoundFlag;
	}
	public boolean getBackGroundMusicFlag(){
		return this.backGroundMusicFlag;
	}
	public byte getCurrentVol(){
		return this.currentVol;
	}
	public void exitSoundPlayer(){
		this.stopBackGroundSound();
		//this.releasePool();
	}
	public void releaseResource(){
		this.stopBackGroundSound();
		this.releasePool();
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		this.curBackGroundMusicId ++;
		if(this.curBackGroundMusicId >= musicnum){
			this.curBackGroundMusicId = 0;
		}
		this.mp = MediaPlayer.create(context, musicMap[this.curBackGroundMusicId]);
		this.mp.setOnCompletionListener(this);
		this.mp.start();
	}
}
