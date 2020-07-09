# 其它笔记

```Java
1.设置壁纸
public static void setWallpaper(Context context){
    try {
        WallpaperManager wpm = (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
        wpm.setResource(R.drawable.ic_launcher);
    } catch (Exception e) {
      e.printStackTrace();
    }
}


```
