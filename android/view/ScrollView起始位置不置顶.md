* ScrollView起始位置不置顶问题
  
  * 解决办法1
  ```Java
  在initView中，对ScrollView的父布局rootLay，做如下设置
  
  rootLay.setFocusable(true);
  rootLay.setFocusableInTouchMode(true);
  rootLay.requestFocus();
  
  ```
  
  * 解决办法2
  
  ```Java
  对ScrollView的子view加入
  android:focusable="true"
  android:focusableInTouchMode="true"
  
   <ScrollView 
		         android:layout_width="match_parent"
		         android:layout_height="match_parent"
		         android:id="@+id/scrollView"
		         android:fadingEdge="none"
		         android:scrollbars="none"
		         android:overScrollMode="never">
		    		<LinearLayout 
		    		    android:layout_width="match_parent"
		    		    android:layout_height="wrap_content"
		    		    android:focusable="true"
		         		android:focusableInTouchMode="true"
		    		    android:orientation="vertical">
           </LinearLayout>	
   </ScrollView>
   
   
  ```
  
  * 解决办法3
  ```Java
   使用scrollview滚动到顶部
  // scrollView.fullScroll(ScrollView.FOCUS_UP);//滚动到顶部
	scrollView.scrollTo(0, 0);
  ```
