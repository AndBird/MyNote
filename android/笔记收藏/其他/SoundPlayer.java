package net.laizi.cartoon;

import java.util.HashMap;

import net.laizi.tool.CartoonTool;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer implements MediaPlayer.OnCompletionListener{
	//������Դ���� �����ڱ��ؼ�¼
	private static SoundPlayer instance;
	private Context context;
	private AudioManager am;		//ͨ��ý���������������
	private byte currentVol,maxVol;	//��ǰ������������Ϸ�������������   �������15
	private MediaPlayer mp;			//�������ֲ��Ŷ���
	private int musicnum = 4;
	private int musicMap[] = new int[musicnum];	//MediaPlayer��Ӧ����Դ�ļ�
	private boolean m_startLoad;             //��ʼ���ر��
	private boolean m_loadComplete;          //�������
	
	private SoundPool soundPool;
	private HashMap<String,Integer> resMap;	//soundPool��Ӧ����Դ�ļ�
	
	private boolean i_startLoad;             //��ʼ���ر��
	private boolean i_loadComplete;          //�������
	private int curBackGroundMusicId;		//��¼��ǰ�������ֵ�id  ���ڿ��Ը�����������
	private boolean curFlag;				//��¼��ǰ�Ƿ�ѭ�����ű�������
	
	private boolean backGroundMusicFlag;	//�������ֿ���	true--����������  false---�ر�������
	private boolean playSoundFlag;			//����Ч������       true--����Ч  	false----�ر�����Ч
	private short[] keys = new short[53];	//����������±�
	
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
	//��ʼ���������±�
	private void initSoundIndex(){
		
		for(byte i = 0;i < 53;i++)   //
		{
			keys[i] = i;
		}
	}
	//����������Դ�ļ�
	public void loadPool(){
//		try{
			if((!this.i_startLoad) && (!this.i_loadComplete)){//û�м�����û������
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
			this.i_loadComplete = true;	//�����ļ��������
//		}catch(Exception e){
//			Debugs.debug("Exception in loadPool");
//		}
	}
	
	
	//���ر���������Դ�ļ�
		public void loadMusic(){
//			try{
				if((!this.m_startLoad) && (!this.m_loadComplete)){//û�м�����û������
					this.m_startLoad = true;
					musicMap =  CartoonTool.getMusicIds(context,musicnum);					
				}
				this.m_loadComplete = true;	//�����ļ��������
//			}catch(Exception e){
//				Debugs.debug("Exception in loadPool");
//			}
		}
	
	
	
	//�����ļ�������������
	public void playSound(String soundname){
		if(!this.playSoundFlag){
			System.out.println("������Ч���عر���!");
			return;
		}
		while(!this.i_loadComplete){
			Thread.yield();   //��ͣ��ǰ����ִ�е��̶߳���
		}
		if(null == this.soundPool){
			return;
		}
		
		System.out.println("soundPool name = " + soundname);
		int m = this.resMap.get(soundname);
		this.soundPool.play(m,1, 1, 0, 0, 1);
	}
	
	//ֹͣ��������
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
	
	//bΪtrueʱѭ�����ţ�falseʱ˳�򲥷�
	public void startBackGroundSound(boolean b){
		if(!this.backGroundMusicFlag){
			System.out.println("�������ֿ��عر���!");
			return;
		}
		while(!this.m_loadComplete){
			Thread.yield();   //��ͣ��ǰ����ִ�е��̶߳���
		}
		System.out.println("music index = " +this.curBackGroundMusicId );
		
		if(null == this.mp  ){
			this.mp = MediaPlayer.create(this.context, musicMap[this.curBackGroundMusicId]);//��0��ʼ����
			this.mp.setOnCompletionListener(this);
		}
		mp.setLooping(b);//ѭ������
		//this.mp.setVolume(currentVol, currentVol);		//���õ�ǰ��������
		this.mp.start();
		curFlag = b;                //���ŷ�ʽ
	}
	
	//ֹͣ��������
	public void stopBackGroundSound(){
		if(null != this.mp){
			this.mp.release();
			this.mp = null;
		}
	}
	//��ͣ
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
	
	//���õ�ǰ����
	public void setCurrentVol(byte vol){
		System.out.println("��������   vol = " + vol);
		this.currentVol = vol;
		if(null != am){
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, AudioManager.FLAG_PLAY_SOUND);
		}
	}
	public void setBackGroundMusicFlag(boolean b){
		System.out.println("���ñ������ֿ���! result = " + b); 
		this.backGroundMusicFlag = b;
		if(this.backGroundMusicFlag){
			this.stopBackGroundSound();
			this.startBackGroundSound(curFlag);
		}else{
			this.stopBackGroundSound();
		}
	}
	
	public void setPlaySoundFlag(boolean b){
		System.out.println("������Ч����! result = " + b);
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
