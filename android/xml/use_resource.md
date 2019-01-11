# 资源使用

## 1.assets
```Java
public String getAssetsWebUrl(String relativeAssetFilePath) {
     return "file:///android_asset" + File.separator + relativeAssetFilePath;
}


 private InputStream inputStreamForAndroidResource(String url) {
    final String ANDROID_ASSET = "file:///android_asset/";

    if (url.contains(ANDROID_ASSET)) {
      url = url.replaceFirst(ANDROID_ASSET, "");
        try {
          AssetManager assets = getAssets();
          Uri uri = Uri.parse(url);
          return assets.open(uri.getPath(), AssetManager.ACCESS_STREAMING);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
      return null;
  }

  public static Uri getUriFromAssets(String relativeAssetFilePath) {
     return Uri.fromFile(new File("//assets" + File.separator + relativeAssetFilePath));
  }
```

## 2. Raw
```Java
String uriString = "android.resource://" + getPackageName() + "/" + R.raw.my_video_file;
Uri uri = Uri.parse(uriString);

```

## 3.Resource
```Java
 public static Uri getUriFromResource(Context context, int resource) {
       return Uri.parse("android.resource://" + context.getPackageName() + File.separator + resource);
 }
 
 /**获取资源id的名字
 * 如R.id.save的返回值为save
 */
public static Uri getResourceName(Context context, int resId) {
      if(resId == View.NO_ID){
          return null;
      }
      return context.getResources().getResourceEntryName(resId);
     }
```

