# ProgressBar

* 星星大小
```
<RatingBar
	android:id="@+id/ratingBar"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	style="@style/myRatingBarStyle"
	android:numStars="5"
	android:isIndicator="true"
	android:stepSize="0.5"/>
	

<style name="myRatingBarStyle" parent="@android:style/Widget.RatingBar">
        <item name="android:progressDrawable">@drawable/gamelist_pingfen_bar_bg</item>
        <item name="android:minHeight">16dip</item>
        <item name="android:maxHeight">16dip</item>
</style>


<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@android:id/background" android:drawable="@drawable/star_bg1"></item>          
    <item android:id="@android:id/secondaryProgress" android:drawable="@drawable/star_bg1"></item>
    <item android:id="@android:id/progress"  android:drawable="@drawable/star_bg2"></item>
</layer-list>	
	

```