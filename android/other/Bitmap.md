
* 备忘
```Java
1.BitmapFactory.Options类
	boolean inJustDecodeBounds	是否只扫描轮廓
	int inSample	采样率
	Bitmap.Config inPreferredConfig	格式，色彩模式
	int outWidth  bitmap的宽
	int outHeight  bitmap的高
	boolean inDither  防抖动，默认false
	int inDensity  图片本身的像素密度（其实就是图片资源所在的哪个密度文件夹下，如果在asstes、手机内存／sd卡下，默认是160）
	inTargetDensity：图片最终在bitmap里的像素密度，如果没有赋值，会将inTargetDensity设置成inScreenDensity；
	inScreenDensity：手机本身的屏幕密度，scale = inTargetDensity／inDensity。
	boolean inScaled  是否可以缩放，默认true
	boolean inMutable 是否可变，设为ture，decode转换方法返回的结果全部可改变

2.BitmapFactory.decodeResource
图片的缩放倍数是根据scale = inTargetDensity/inDensity来计算得到的,其中inDensity与资源文件density相关,inTargetDensity与屏幕相关。
下面是安卓源码:
//如果资源密度不为0，手机屏幕密度也不为0, 资源的密度与屏幕密度不相等时，图片缩放比例=屏幕密度/资源密度，内存占用比例为scale * scale
if (density != 0 && targetDensity != 0 && density != screenDensity) {
    scale = (float) targetDensity / density;
}

原文：https://blog.csdn.net/smileiam/article/details/68946182 

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


/**获取图片字节大小*/
public static int getBitmapSize(Bitmap bitmap){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){     //API 19
        return bitmap.getAllocationByteCount();
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
        return bitmap.getByteCount();
    } else {
        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }
}


```

