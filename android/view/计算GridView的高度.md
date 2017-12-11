* 计算GridView的高度(ScrollView嵌套)
 
 注：计算高度，特别注意 item布局的root节点必须要是LinearLayout，否则会报空指针
  
  ```Java
   public void setGridViewHeight(GridView gridView){
        try {
            ListAdapter listAdapter = gridView.getAdapter();
            if (listAdapter == null) {
                Log.e(TAG, "listAdapter is null, return");
                return;
            }

            int totalHeight = 0;
            int columns = gridView.getNumColumns();
            //int columns = 3;
            Log.e(TAG, "gridView columns=" + columns);
            int cout = listAdapter.getCount();
            if(columns > 0 && cout > 0){
                cout = cout / columns + (cout % columns > 0 ? 1 : 0); //获取行数
                Log.e(TAG, "行数:" + cout);
                View listItem = listAdapter.getView(0, null, gridView);
                listItem.measure(0, 0);
                int height =  listItem.getMeasuredHeight();
                totalHeight = cout * height;
                
//                for (int i = 0; i < cout; i++) {
//                    View listItem = listAdapter.getView(i, null, gridView);
//                    listItem.measure(0, 0);
//                    totalHeight += listItem.getMeasuredHeight();
//                }

                ViewGroup.LayoutParams params = gridView.getLayoutParams();
                //params.height = totalHeight + (gridView.getVerticalSpacing() * (cout - 1));//getVerticalSpacing方法部分手机上不存在
                totalHeight = totalHeight + (convertDpToPx(gridView.getContext(), 10) * (cout - 1));//计算高度
                //params.height = totalHeight + convertDpToPx(gridView.getContext(), 25);//计算高度，上下margin
                params.height = totalHeight;//计算高度，上下margin
                Log.e("gridview height", "" + params.height);
                gridView.setLayoutParams(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int convertDpToPx(Context context, int dp) {
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
	}
  
  ```
