* 计算ListView的高度(ScrollView嵌套)
 
 注： 计算listview的高度，特别注意：item布局的root节点必须要是LinearLayout，否则会报空指针
  
  * ListView item的高度定
    
    ```Java
     public void setListViewHeight(ListView listView) {
        try {
        	 ListAdapter listAdapter = listView.getAdapter();
             if (listAdapter == null) {
                 return ;
             }
             int totalHeight = 0;
             int count = listAdapter.getCount();
             Log.e(TAG, "listAdapter.getCount(): " + count);
             int itemHeight = 0;
             for (int i = 0; i < count && i < 1; i++) {  
	             View listItem = listAdapter.getView(i, null, listView);
	             listItem.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),  
	                     MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
	             itemHeight =  listItem.getMeasuredHeight();
	             //优化
	             //totalHeight = totalHeight + height;
             }
             totalHeight = itemHeight * count;
             
             ViewGroup.LayoutParams params = listView.getLayoutParams();
             Log.e(TAG, "listView.getDividerHeight(): " + listView.getDividerHeight());
             params.height = totalHeight + (listView.getDividerHeight() * (count - 1));
             Log.e(TAG, "初始化时listviewParams.height" + params.height);
             listView.setLayoutParams(params);
		} catch (Exception e){
			e.printStackTrace();
		}
     }	
    ```

  * ListView item高度不定
  
  ```Java
  public void setListViewHeight(ListView listView) {
        try {
        	 ListAdapter listAdapter = listView.getAdapter();
             if (listAdapter == null) {
                 return ;
             }
             DisplayMetrics dm = listView.getResources().getDisplayMetrics();
             //int screenHeight = dm.heightPixels;
             int screenWidth = dm.widthPixels;
             int totalHeight = 0;
             int count = listAdapter.getCount();
             Log.e(TAG, "listAdapter.getCount(): " + count);
             int listViewWidth = screenWidth;//TlistView在布局时的宽度 (需减边距)
             int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);  
             for (int i = 0; i < count; i++) {  
	             View listItem = listAdapter.getView(i, null, listView);
	             listItem.measure(widthSpec, 0);
	             //定高计算
//	             listItem.measure(  
//	                     MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),  
//	                     MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
	             int height =  listItem.getMeasuredHeight();
	             totalHeight = totalHeight + height;
             }
             ViewGroup.LayoutParams params = listView.getLayoutParams();
             Log.e(TAG, "listView.getDividerHeight(): " + listView.getDividerHeight());
             params.height = totalHeight + (listView.getDividerHeight() * (count - 1));
             Log.e(TAG, "初始化时listviewParams.height" + params.height);
             listView.setLayoutParams(params);
      } catch (Exception e){
        e.printStackTrace();
      }
   }	
  ``
