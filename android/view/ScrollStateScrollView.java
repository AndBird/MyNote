package com.example.test;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

//�������غ͹���״̬����
public class ScrollStateScrollView extends ScrollView{
	private static final String TAG = ScrollStateScrollView.class.getName();
	
	private OnScrollChangedListener scrollChangedListener = null;
	private ScrollStateListener mScrollStateListener = null;
	
	
	/** 
	 * ���������Ƿ�ֹͣ
     * ��Ҫ�������û���ָ�뿪MyScrollView��MyScrollView���ڼ���������������������Y�ľ��룬Ȼ�����Ƚ� 
     */  
    private int lastScrollY;  
    private boolean fingerUp = false;//��ָ�Ƿ��Ѿ�up 

	
	 /** 
     * �����û���ָ�뿪MyScrollView
     */  
    private Handler handler = new Handler() {  
  
        public void handleMessage(android.os.Message msg) {  
        	try {
        		if(!needListenerScrollState()){
        			//����Ҫ��������״̬
        			return ;
        		}
        		int scrollY = ScrollStateScrollView.this.getScrollY();  
        		switch (msg.what) {
				case MotionEvent.ACTION_UP:
					//DebugUtils.printInfo(TAG, "up:scrollY=" + scrollY);
					//��ʱ�ľ���ͼ�¼�µľ��벻��ȣ��ڸ�5�����handler������Ϣ   
	        		if(lastScrollY != scrollY){  
	        			lastScrollY = scrollY;  
	        			if(fingerUp){
	        				handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_UP), 15); 
	        			}
	        		}else{
	        			//����ֹͣ
	        			if(mScrollStateListener != null){
	        				mScrollStateListener.stopScroll();
	        			}
	        		}
					break;
				case MotionEvent.ACTION_DOWN:
					//DebugUtils.printInfo(TAG, "down:scrollY=" + scrollY);
					if(lastScrollY != scrollY){  
						if(mScrollStateListener != null){
							mScrollStateListener.startScroll();
						}
					}else{ 
						if(!fingerUp){
							handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_DOWN), 5);
						}
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        };  
  
    };   
  

	
	public ScrollStateScrollView(Context context) {
		super(context);
		init();
	}

	public ScrollStateScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ScrollStateScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
	}
	
	public void setOnScrollChangedListener(OnScrollChangedListener listener){
		this.scrollChangedListener = listener;
	}

	public interface OnScrollChangedListener{
		public void onScrollChanged();
	}
	
	public void setScrollStateListener(ScrollStateListener scrollStateListener) {
		this.mScrollStateListener = scrollStateListener;
	}  
	
	//����״̬����
	public interface ScrollStateListener{
		public void startScroll();
		public void stopScroll();
	}
	
	//�������ײ���������һҳ
	public interface OnFooterListener{
		public void onFooterLoad();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(scrollChangedListener != null){
			scrollChangedListener.onScrollChanged();
		}
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(needListenerScrollState()){
			 switch(ev.getAction()){  
		        case MotionEvent.ACTION_UP:  
		        	//DebugUtils.printInfo(TAG, "onInterceptTouchEvent up");
		        	 fingerUp = true;
		        	 handler.removeMessages(MotionEvent.ACTION_DOWN);
		             handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_UP), 5);    
		            break;  
		        case MotionEvent.ACTION_DOWN:
		        	//DebugUtils.printInfo(TAG, "onInterceptTouchEvent down");
		        	fingerUp = false;
		        	lastScrollY = ScrollStateScrollView.this.getScrollY();
		        	handler.removeMessages(MotionEvent.ACTION_UP);
		        	handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_DOWN), 5);    
		        	break;
		    }  
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	//��ʱ�ղ���down�¼�
	@Override  
    public boolean onTouchEvent(MotionEvent ev){  
		if(needListenerScrollState()){
	        switch(ev.getAction()){  
	        case MotionEvent.ACTION_UP:  
	        	 //DebugUtils.printInfo(TAG, "onTouchEvent up");
	        	 fingerUp = true;
	        	 handler.removeMessages(MotionEvent.ACTION_DOWN);
	             handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_UP), 5);    
	            break;  
	        case MotionEvent.ACTION_DOWN:
	        	 //DebugUtils.printInfo(TAG, "onTouchEvent down");
	        	 fingerUp = false;
	        	 lastScrollY = ScrollStateScrollView.this.getScrollY();
	        	 handler.removeMessages(MotionEvent.ACTION_UP);
	        	 handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_DOWN), 5);    
	        	break;
	        }  
		}
        return super.onTouchEvent(ev);  
    }

	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
	}
	
	//ֹͣ��������״̬
	public void stopScrollStateLinstener(){
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}
	
	//�Ƿ���Ҫ��������״̬
	private boolean needListenerScrollState(){
		return mScrollStateListener != null;
	}
	
	private static void printLog(String TAG, String msg){
		Log.e(TAG, msg);
	}
}
