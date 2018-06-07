# ImageLoader

## 1.初始化
```Java
 public static void initImageLoader(Context context){
    try {
        if(ImageLoader.getInstance().isInited()){
          ImageLoader.getInstance().destroy();
        }
           ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                   .threadPoolSize(3)//设置线程池内加载数量
               .threadPriority(Thread.NORM_PRIORITY - 2)
                   .denyCacheImageMultipleSizesInMemory()
                   .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                   //.discCacheFileNameGenerator(new Md5FileNameGenerator())
                   .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值  
                   .tasksProcessingOrder(QueueProcessingType.LIFO)
                   .build();
           ImageLoader.getInstance().init(config);
    } catch (Exception e) {
      e.printStackTrace();
    }
 }
```

## 2.加载资源
```Java

String imageUri = "drawable://" + R.drawable.image;
private DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.app_image_default_h)
		.showImageOnFail(R.drawable.app_image_default_h)
		.cacheInMemory(false).cacheOnDisk(false)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
 ImageLoader.getInstance().displayImage(imageUri, imageview, options);
```
