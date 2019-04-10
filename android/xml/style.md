# style
1.白色底板对话框
```Java
api 11:
   <style name="AppBaseTheme" parent="android:Theme.Holo.Light">
        <!-- API 11 theme customizations can go here. -->
    </style>
    
api 14:
    <style name="AppBaseTheme" parent="android:Theme.Holo.Light.DarkActionBar">
        <!-- API 14 theme customizations can go here. -->
    </style>


```

2.星星评分条
```java
(1) style
<style name="myRatingBarStyle" parent="@android:style/Widget.RatingBar">
        <item name="android:progressDrawable">@drawable/pingfen_bar_bg</item>
        <item name="android:minHeight">13dip</item>
        <item name="android:maxHeight">13dip</item>
</style>
   
   
(2) bar bg
pingfen_bar_bg.xml:
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@android:id/background" android:drawable="@drawable/star_bg1"></item> <span></span>              
    <item android:id="@android:id/secondaryProgress" android:drawable="@drawable/star_bg1"></item>
    <item android:id="@android:id/progress"  android:drawable="@drawable/star_bg2"></item>
</layer-list>

(3) RatingBar view
RatingBar:
 <RatingBar  
    android:id="@+id/ratingBar"  
    android:layout_width="wrap_content" 
    android:layout_height="wrap_content"  	
    style="@style/myRatingBarStyle"
    android:numStars="5"  
    android:isIndicator="true"
    android:stepSize="0.5"/>  
                
```
