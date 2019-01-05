
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

