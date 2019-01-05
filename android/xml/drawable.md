# Drawable

*笔记
```Java
1. res目录下不同的drawable目录内的图片的缩放:
缩放比例=屏幕的真实densityDpi/资源文件所在文件夹的density

BitmapFactory.decodeResource
图片的缩放倍数是根据inTargetDensity/inDensity来计算得到的,其中inDensity与资源文件density相关,inTargetDensity与屏幕相关。
只有当资源密度不为0，手机屏幕密度也不为0, 资源的密度与屏幕密度不相等时，才会缩放图片(此处查看安卓源码)。图片缩放比例=屏幕密度/资源密度，内存占用比例为scale * scale。


注：图片放在低density目录下，在高分辨率手机中会导致内存消耗更多
drawable目录: density=0, 缩放比例=屏幕的真实densityDpi/160
drawable-nodpi: density=65535，不缩放
drawable-ldpi: density=120，
drawable-mdpi: density=160，
drawable-hdpi: density=240，
drawable-xhdpi: density=320，
drawable-xxhdpi: density=480，
drawable-xxxdpi: density=640，

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
  
2.BitmapFactory.decodeStream
如果图片在asstes、手机内存／sd卡下或者网络，density默认是160，默认是不会缩放图片的，占用内存不变）
options里的参数只提取了sampleSize、optionsJustBounds，但是没有见到inDensity，inTargetDensity，inScreenDensity等参数的提取。
如果想通过设置Options里的inDensity或是inTargetDensity参数来调整图片的缩放比，必须两个参数均设置才能起作用，只设置一个，不会起作用。
如果在加载流前，设置ops.inDensity和ops.inTargetDensity的参数分别为240和480，图片占用内存大小放大到原来的4倍。


3.
(1)一张图片占用内存=图片长 * 图片宽 ／ （资源图片文件密度/手机屏幕密度）^2 * 每一象素占用字节数，所以图片占用内存跟图片本身大小、手机屏幕密度、图片所在的文件夹密度，图片编码的色彩格式有关
(2)drawable和mipmap文件夹存放图片的区别，首先图片放在drawable-xhdpi和mipmap-xhdpi下，两者占用的内存是一样的， 
Mipmaps早在Android2.2+就可以用了，但是直到4.3 google才强烈建议使用。把图片放到mipmaps可以提高系统渲染图片的速度，提高图片质量，减少GPU压力,其他并没有什么区别.
(3)png、jpg、webP
a. png有透明通道，而jpg没有 
b. png是无损压缩的，而jpg是有损压缩，因此png中存储的信息会很多，体积自然就大了 
c. 手机对png情有独钟，会对其进行硬件加速，所以同样一张背景图，png虽然体积大，但是加载速度更快
d. 对于app包中的图片，我们都使用png格式的，而对于要从网络上加载的图片，考虑到流量以及下载上速度，则使用jpg格式的，因为它有较高的压缩率，体积更小。 
e. 对于背景图、引导页，这种大尺寸的图片，我们还是倾向于jpg格式的，虽然加载慢一些吗，但是体积小，减少了包的体积 
f. Google后来发布了一种新的图片格式，WebP，它的压缩率比jpg更好，已经在慢慢普及




```
[imageDensity](https://github.com/AndBird/Demo/blob/master/ImageDensity.zip)        [截图](https://github.com/AndBird/Demo/blob/master/screen%20capture/image_density.png)





