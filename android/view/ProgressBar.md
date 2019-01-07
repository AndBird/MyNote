
# ProgressBar

* 进度条
``` xml
//色值
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android" >
    <item android:id="@android:id/background">
        <shape>
            <corners android:radius="300dp" />
            <solid android:color="#e0e0e0" />
        </shape>
    </item>

    <item android:id="@android:id/secondaryProgress">
        <clip>
            <shape>
                <corners android:radius="300dp" />
                <solid android:color="#e0e0e0" />
            </shape>
        </clip>
    </item>
    <item android:id="@android:id/progress">
        <clip>
            <shape>
                <corners android:radius="300dp" />
                <solid android:color="@color/head_text_color_press" />
                <!--<gradient
                    android:startColor="#000000"
                    android:endColor="#ffffff"
                    android:angle="0"/>-->
            </shape>
        </clip>
    </item>
</layer-list>


//图片
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android" >

    <item android:id="@android:id/background">
        <clip android:drawable="@drawable/bg1">

        </clip>
    </item>
    <item android:id="@android:id/progress">
        <clip android:drawable="@drawable/bg2">

        </clip>
    </item>

</layer-list>

//
<?xml version="1.0" encoding="utf-8"?>
<layer-list
  xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@android:id/background" android:drawable="@drawable/bg3" />
    <item android:id="@android:id/secondaryProgress">
        <clip>
            <nine-patch android:src="@drawable/bg2" />
        </clip>
    </item>
    <item android:id="@android:id/progress">
        <clip>
            <nine-patch android:src="@drawable/bg1" />
        </clip>
    </item>
</layer-list>


```
