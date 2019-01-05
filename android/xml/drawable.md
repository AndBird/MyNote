# Drawable

*笔记
```Java
1. res目录下不同的drawable目录内的图片的缩放:
缩放比例=屏幕的真实densityDpi/资源文件所在文件夹的density

BitmapFactory.decodeResource
图片的缩放倍数是根据inTargetDensity/inDensity来计算得到的,其中inDensity与资源文件density相关,inTargetDensity与屏幕相关

注：图片放在低density目录下，在高分辨率手机中会导致内存消耗更多
drawable目录: density=0, 缩放比例=屏幕的真实densityDpi/160
drawable-nodpi: density=65535，
drawable-ldpi: density=120，
drawable-mdpi: density=160，
drawable-hdpi: density=240，
drawable-xhdpi: density=320，
drawable-xxhdpi: density=480，
drawable-xxxdpi: density=640，
如果图片在asstes、手机内存／sd卡下，density默认是160）

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
[imageDensity](https://github.com/AndBird/Demo/blob/master/ImageDensity.zip)        [截图](https://github.com/AndBird/Demo/blob/master/screen%20capture/image_density.png)



```
