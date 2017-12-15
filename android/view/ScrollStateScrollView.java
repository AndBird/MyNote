package com.example.test;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

//上拉加载和滚动状态监听
public class ScrollStateScrollView extends ScrollView{
	private static final String TAG = ScrollStateScrollView.class.getName();
	
	private OnScrollChangedListener scrollChangedListener = null;
	private ScrollStateListener mScrollStateListener = null;
	
	
	/** 
	 * 监听滚动是否停止
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较 
     */  
    private int lastScrollY;  
    private boolean fingerUp = false;//手指是否已经up 

	
	 /** 
     * 用于用户手指离开MyScrollView
     */  
    private Handler handler = new Handler() {  
  
        public void handleMessage(android.os.Message msg) {  
        	try {
        		if(!needListenerScrollState()){
        			//不需要监听滚动状态
        			return ;
        		}
        		int scrollY = ScrollStateScrollView.this.getScrollY();  
        		switch (msg.what) {
				case MotionEvent.ACTION_UP:
					//DebugUtils.printInfo(TAG, "up:scrollY=" + scrollY);
					//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息   
	        		if(lastScrollY != scrollY){  
	        			lastScrollY = scrollY;  
	        			if(fingerUp){
	        				handler.sendMessageDelayed(handler.obtainMessage(MotionEvent.ACTION_UP), 15); 
	        			}
	        		}else{
	        			//滚动停止
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
	
	//滚动状态监听
	public interface ScrollStateListener{
		public void startScroll();
		public void stopScroll();
	}
	
	//滑动到底部，加载下一页
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
	
	//有时收不到down事件
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
	
	//停止监听滚动状态
	public void stopScrollStateLinstener(){
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}
	
	//是否需要监听滚动状态
	private boolean needListenerScrollState(){
		return mScrollStateListener != null;
	}
	
	private static void printLog(String TAG, String msg){
		Log.e(TAG, msg);
	}
}
