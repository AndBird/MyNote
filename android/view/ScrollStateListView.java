package com.example.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

//监听ListView的滚动状态
public class ScrollStateListView extends ListView implements OnScrollListener{
	private static final String TAG = ScrollStateListView.class.getSimpleName();
	
	private ScrollStateListener mScrollStateListener = null;
    private boolean fingerUp = false;//手指是否已经up 
    
    public interface ScrollStateListener{
		public void startScroll();
		public void scrolling(boolean fingerUp);
		public void stopScroll();
	}
    
    
   
	public ScrollStateListView(Context context) {
		super(context);
		init(context);
	}
	
	public ScrollStateListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public ScrollStateListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public ScrollStateListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}
	
	private void init(Context context){
		setOnScrollListener(this);
	}


	public void setScrollStateListener(ScrollStateListener scrollStateListener) {
		this.mScrollStateListener = scrollStateListener;
	}  

	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		 switch (scrollState) {
         case OnScrollListener.SCROLL_STATE_IDLE:
             Log.e(TAG, "SCROLL_STATE_IDLE........");
             if(mScrollStateListener != null){
            	 mScrollStateListener.stopScroll();
             }
           break;
         case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
        	 Log.e(TAG, "SCROLL_STATE_TOUCH_SCROLL........");
        	 fingerUp = false;
        	 if(mScrollStateListener != null){
            	 mScrollStateListener.startScroll();
             }
        	 break;
         case OnScrollListener.SCROLL_STATE_FLING:
        	 Log.e(TAG, "SCROLL_STATE_FLING........");
        	 fingerUp = true;
         break;
     }
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		 //Log.e(TAG, "firstVisibleItem=" + firstVisibleItem + ",visibleItemCount=" + visibleItemCount);
		 if(mScrollStateListener != null){
			 mScrollStateListener.scrolling(fingerUp);
		 }
	} 
}
