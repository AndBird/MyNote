
* 备忘
```Java
1.BitmapFactory.Options类
	boolean inJustDecodeBounds	是否只扫描轮廓
	int inSample	采样率
	Bitmap.Config inPreferredConfig	格式，色彩模式
	int outWidth  bitmap的宽
	int outHeight  bitmap的高
	boolean inDither  防抖动，默认false
	int inDensity  像素密度
	boolean inScaled  是否可以缩放，默认true
	boolean inMutable 是否可变，设为ture，decode转换方法返回的结果全部可改变


```

* 旋转Bitmap
```Java
 /**
     * 旋转Bitmap(顺时针)
     * @param srcBitmap : 源bitmap
     * @param rotate : 旋转角度
     * 
     * */
    public Bitmap rotateBitMap(Bitmap srcBitmap, float rotate){
    	Bitmap bitmap = null;
    	if(srcBitmap != null && !srcBitmap.isRecycled()){
			Matrix matrix = new Matrix();  
	        matrix.postRotate(rotate);
	        int width = srcBitmap.getWidth();  
	        int height = srcBitmap.getHeight();
	        
	        bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);  
		}
    	return bitmap;
    }
```

* 
```Java
 /**解析图片所在文件夹的属性*/
 private TypedValue getTargetDensityByResource(Resources resources, int res_id) {
	 TypedValue value = new TypedValue();
	 resources.openRawResource(res_id, value);
	 Log.e(TAG, "value.density: " + value.density + "," + value.string);
	 return value;
 }
	 
  /**解析图片，让图片在不同密度的手机上不会被缩放*/
  private Bitmap decodeResourceNoScale(Resources resources, int res_id) {
	  TypedValue value = new TypedValue();
	  resources.openRawResource(res_id, value);
	  BitmapFactory.Options opts = new BitmapFactory.Options();
	  opts.inTargetDensity = value.density;
	  return BitmapFactory.decodeResource(resources, res_id, opts);
  }

```

