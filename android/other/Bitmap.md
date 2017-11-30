
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
