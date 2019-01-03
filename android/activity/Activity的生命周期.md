# Activity的生命周期
```Java
本文所讲Activity说明:
MainActivity ->A
SecondeActivity ->B
DialogActivity ->C

A跳转到B或者A跳转到C，探讨  
<item name="android:windowFullscreen">true</item>
和<item name="android:windowIsTranslucent">true</item>对Activity的生命周期影响

A跳转到B,A不finish
比较B全屏和透明对A生命周期的影响:
(1)A非全屏非透明，B非全屏透明，跳转时A不Finish
05-11 16:53:55.604: E/MainActivity(8466): onCreate
05-11 16:53:55.630: E/MainActivity(8466): onStart
05-11 16:53:55.632: E/MainActivity(8466): onResume
05-11 16:53:57.790: E/MainActivity(8466): click jump
05-11 16:53:57.909: E/MainActivity(8466): onPause
05-11 16:53:57.922: E/SecondActivity(8466): onCreate
05-11 16:53:57.927: E/SecondActivity(8466): onStart
05-11 16:53:57.928: E/SecondActivity(8466): onResume
05-11 16:53:58.009: E/MainActivity(8466): onSaveInstanceState
05-11 16:54:00.968: E/SecondActivity(8466): onBackPressed
05-11 16:54:00.968: E/SecondActivity(8466): onPause
05-11 16:54:00.983: E/MainActivity(8466): onResume
05-11 16:54:01.011: E/SecondActivity(8466): onStop
05-11 16:54:01.012: E/SecondActivity(8466): onDestroy
(2)A非全屏非透明，B全屏透明，跳转时A不Finish
05-11 16:45:41.795: E/MainActivity(7951): onCreate
05-11 16:45:41.824: E/MainActivity(7951): onStart
05-11 16:45:41.828: E/MainActivity(7951): onResume
05-11 16:45:43.150: E/MainActivity(7951): click jump
05-11 16:45:43.263: E/MainActivity(7951): onPause
05-11 16:45:43.283: E/SecondActivity(7951): onCreate
05-11 16:45:43.285: E/SecondActivity(7951): onStart
05-11 16:45:43.285: E/SecondActivity(7951): onResume
05-11 16:45:43.359: E/MainActivity(7951): onSaveInstanceState
05-11 16:45:49.824: E/SecondActivity(7951): onBackPressed
05-11 16:45:49.824: E/SecondActivity(7951): onPause
05-11 16:45:49.840: E/MainActivity(7951): onResume
05-11 16:45:50.284: E/SecondActivity(7951): onStop
05-11 16:45:50.284: E/SecondActivity(7951): onDestroy
(3)A非全屏非透明，B非全屏非透明，跳转时A不Finish
05-11 16:59:34.899: E/MainActivity(8746): onCreate
05-11 16:59:34.958: E/MainActivity(8746): onStart
05-11 16:59:34.960: E/MainActivity(8746): onResume
05-11 16:59:36.988: E/MainActivity(8746): click jump
05-11 16:59:37.113: E/MainActivity(8746): onPause
05-11 16:59:37.136: E/SecondActivity(8746): onCreate
05-11 16:59:37.140: E/SecondActivity(8746): onStart
05-11 16:59:37.140: E/SecondActivity(8746): onResume
05-11 16:59:37.229: E/MainActivity(8746): onSaveInstanceState
05-11 16:59:37.229: E/MainActivity(8746): onStop
05-11 16:59:39.118: E/SecondActivity(8746): onBackPressed
05-11 16:59:39.119: E/SecondActivity(8746): onPause
05-11 16:59:39.128: E/MainActivity(8746): onRestart
05-11 16:59:39.128: E/MainActivity(8746): onStart
05-11 16:59:39.129: E/MainActivity(8746): onResume
05-11 16:59:39.194: E/SecondActivity(8746): onStop
05-11 16:59:39.195: E/SecondActivity(8746): onDestroy
(4)A非全屏非透明，B全屏非透明，跳转时A不Finish
05-11 17:05:19.850: E/MainActivity(9062): onPause
05-11 17:05:19.987: E/MainActivity(9062): onStop
05-11 17:05:19.987: E/MainActivity(9062): onDestroy
05-11 17:05:20.936: E/MainActivity(9062): onCreate
05-11 17:05:21.011: E/MainActivity(9062): onStart
05-11 17:05:21.014: E/MainActivity(9062): onResume
05-11 17:05:23.865: E/MainActivity(9062): click jump
05-11 17:05:23.984: E/MainActivity(9062): onPause
05-11 17:05:24.004: E/SecondActivity(9062): onCreate
05-11 17:05:24.007: E/SecondActivity(9062): onStart
05-11 17:05:24.007: E/SecondActivity(9062): onResume
05-11 17:05:24.092: E/MainActivity(9062): onSaveInstanceState
05-11 17:05:24.092: E/MainActivity(9062): onStop
05-11 17:05:25.110: E/SecondActivity(9062): onBackPressed
05-11 17:05:25.110: E/SecondActivity(9062): onPause
05-11 17:05:25.121: E/MainActivity(9062): onRestart
05-11 17:05:25.121: E/MainActivity(9062): onStart
05-11 17:05:25.121: E/MainActivity(9062): onResume
05-11 17:05:25.201: E/SecondActivity(9062): onStop
05-11 17:05:25.201: E/SecondActivity(9062): onDestroy
当A非全屏非透明时:
通过(1)与(2), (3)与(4)比较发现，B全屏与否对A的生命周期无影响
通过(1)与(3), (2)与(4)比较发现，B透明与否对A的生命周期有影响，影响A的onRestart和onStart 生命周期

(5)A全屏透明，B全屏非透明，跳转时A不Finish
05-11 17:08:39.030: E/MainActivity(9264): onCreate
05-11 17:08:39.077: E/MainActivity(9264): onStart
05-11 17:08:39.078: E/MainActivity(9264): onResume
05-11 17:08:40.944: E/MainActivity(9264): click jump
05-11 17:08:41.061: E/MainActivity(9264): onPause
05-11 17:08:41.082: E/SecondActivity(9264): onCreate
05-11 17:08:41.085: E/SecondActivity(9264): onStart
05-11 17:08:41.085: E/SecondActivity(9264): onResume
05-11 17:08:41.188: E/MainActivity(9264): onSaveInstanceState
05-11 17:08:41.191: E/MainActivity(9264): onStop
05-11 17:08:44.979: E/SecondActivity(9264): onBackPressed
05-11 17:08:44.979: E/SecondActivity(9264): onPause
05-11 17:08:44.988: E/MainActivity(9264): onRestart
05-11 17:08:44.989: E/MainActivity(9264): onStart
05-11 17:08:44.989: E/MainActivity(9264): onResume
05-11 17:08:45.089: E/SecondActivity(9264): onStop
05-11 17:08:45.089: E/SecondActivity(9264): onDestroy
(6)A全屏透明，B全屏透明，跳转时A不Finish
05-11 17:34:54.444: E/MainActivity(11955): onCreate
05-11 17:34:54.473: E/MainActivity(11955): onStart
05-11 17:34:54.474: E/MainActivity(11955): onResume
05-11 17:34:55.893: E/MainActivity(11955): click jump
05-11 17:34:56.002: E/MainActivity(11955): onPause
05-11 17:34:56.021: E/SecondActivity(11955): onCreate
05-11 17:34:56.027: E/SecondActivity(11955): onStart
05-11 17:34:56.027: E/SecondActivity(11955): onResume
05-11 17:34:56.149: E/MainActivity(11955): onSaveInstanceState
05-11 17:34:56.150: E/MainActivity(11955): onStop
05-11 17:34:57.944: E/SecondActivity(11955): onBackPressed
05-11 17:34:57.946: E/SecondActivity(11955): onPause
05-11 17:34:57.965: E/MainActivity(11955): onRestart
05-11 17:34:57.965: E/MainActivity(11955): onStart
05-11 17:34:57.966: E/MainActivity(11955): onResume
05-11 17:34:58.043: E/SecondActivity(11955): onStop
05-11 17:34:58.043: E/SecondActivity(11955): onDestroy
(7)A全屏透明，B非全屏非透明，跳转时A不Finish
05-11 17:30:21.578: E/MainActivity(9522): onCreate
05-11 17:30:21.605: E/MainActivity(9522): onStart
05-11 17:30:21.605: E/MainActivity(9522): onResume
05-11 17:30:34.586: E/MainActivity(9522): click jump
05-11 17:30:34.619: E/MainActivity(9522): onPause
05-11 17:30:34.655: E/SecondActivity(9522): onCreate
05-11 17:30:34.658: E/SecondActivity(9522): onStart
05-11 17:30:34.658: E/SecondActivity(9522): onResume
05-11 17:30:35.189: E/MainActivity(9522): onSaveInstanceState
05-11 17:30:35.190: E/MainActivity(9522): onStop
05-11 17:30:39.364: E/SecondActivity(9522): onBackPressed
05-11 17:30:39.364: E/SecondActivity(9522): onPause
05-11 17:30:39.372: E/MainActivity(9522): onRestart
05-11 17:30:39.372: E/MainActivity(9522): onStart
05-11 17:30:39.372: E/MainActivity(9522): onResume
05-11 17:30:39.526: E/SecondActivity(9522): onStop
05-11 17:30:39.526: E/SecondActivity(9522): onDestroy
(8)A全屏透明，B非全屏透明，跳转时A不Finish
05-11 17:38:00.005: E/MainActivity(12538): onCreate
05-11 17:38:00.057: E/MainActivity(12538): onStart
05-11 17:38:00.063: E/MainActivity(12538): onResume
05-11 17:38:01.407: E/MainActivity(12538): click jump
05-11 17:38:01.522: E/MainActivity(12538): onPause
05-11 17:38:01.546: E/SecondActivity(12538): onCreate
05-11 17:38:01.550: E/SecondActivity(12538): onStart
05-11 17:38:01.550: E/SecondActivity(12538): onResume
05-11 17:38:01.633: E/MainActivity(12538): onSaveInstanceState
05-11 17:38:01.633: E/MainActivity(12538): onStop
05-11 17:38:03.081: E/SecondActivity(12538): onBackPressed
05-11 17:38:03.082: E/SecondActivity(12538): onPause
05-11 17:38:03.094: E/MainActivity(12538): onRestart
05-11 17:38:03.094: E/MainActivity(12538): onStart
05-11 17:38:03.095: E/MainActivity(12538): onResume
05-11 17:38:03.165: E/SecondActivity(12538): onStop
05-11 17:38:03.165: E/SecondActivity(12538): onDestroy
(9)A非全屏透明，B非全屏透明，跳转时A不Finish
05-11 17:39:50.744: E/MainActivity(12940): onCreate
05-11 17:39:50.775: E/MainActivity(12940): onStart
05-11 17:39:50.775: E/MainActivity(12940): onResume
05-11 17:39:51.750: E/MainActivity(12940): click jump
05-11 17:39:51.862: E/MainActivity(12940): onPause
05-11 17:39:51.896: E/SecondActivity(12940): onCreate
05-11 17:39:51.899: E/SecondActivity(12940): onStart
05-11 17:39:51.899: E/SecondActivity(12940): onResume
05-11 17:39:51.997: E/MainActivity(12940): onSaveInstanceState
05-11 17:39:51.997: E/MainActivity(12940): onStop
05-11 17:39:55.030: E/SecondActivity(12940): onBackPressed
05-11 17:39:55.031: E/SecondActivity(12940): onPause
05-11 17:39:55.045: E/MainActivity(12940): onRestart
05-11 17:39:55.050: E/MainActivity(12940): onStart
05-11 17:39:55.050: E/MainActivity(12940): onResume
05-11 17:39:55.123: E/SecondActivity(12940): onStop
05-11 17:39:55.124: E/SecondActivity(12940): onDestroy
(10)A非全屏非透明，B非全屏透明，跳转时A不Finish
05-11 17:40:34.936: E/MainActivity(13152): onCreate
05-11 17:40:34.957: E/MainActivity(13152): onStart
05-11 17:40:34.960: E/MainActivity(13152): onResume
05-11 17:40:38.249: E/MainActivity(13152): click jump
05-11 17:40:38.366: E/MainActivity(13152): onPause
05-11 17:40:38.390: E/SecondActivity(13152): onCreate
05-11 17:40:38.394: E/SecondActivity(13152): onStart
05-11 17:40:38.394: E/SecondActivity(13152): onResume
05-11 17:40:38.466: E/MainActivity(13152): onSaveInstanceState
05-11 17:40:39.792: E/SecondActivity(13152): onBackPressed
05-11 17:40:39.792: E/SecondActivity(13152): onPause
05-11 17:40:39.804: E/MainActivity(13152): onResume
05-11 17:40:39.836: E/SecondActivity(13152): onStop
05-11 17:40:39.836: E/SecondActivity(13152): onDestroy
通过(2)(6)、(5)(6)或者(8)(9)(10)可以发现，当A也透明时，B透明对A的生命周期无影响


总结一下，全屏对A的生命周期无影响，当A透明时，B透明对A没影响，当A不透明时，B透明时，从B回到A时将影响A的onRestart和onStart两个生命周期.
另注:
1.对话框对Activity的生命周期无影响.
2.对话框风格的Activity对A的影响:跳转到C时，A只有onPause和onResume变化
05-11 17:58:40.753: E/MainActivity(13152): onCreate
05-11 17:58:40.785: E/MainActivity(13152): onStart
05-11 17:58:40.785: E/MainActivity(13152): onResume
05-11 17:58:42.449: E/MainActivity(13152): click jump
05-11 17:58:42.573: E/MainActivity(13152): onPause
05-11 17:58:42.587: E/DialogActivity(13152): onCreate
05-11 17:58:42.591: E/DialogActivity(13152): onStart
05-11 17:58:42.591: E/DialogActivity(13152): onResume
05-11 17:58:42.664: E/MainActivity(13152): onSaveInstanceState
05-11 17:58:44.396: E/DialogActivity(13152): onBackPressed
05-11 17:58:44.397: E/DialogActivity(13152): onPause
05-11 17:58:44.407: E/MainActivity(13152): onResume
05-11 17:58:44.424: E/DialogActivity(13152): onStop
05-11 17:58:44.425: E/DialogActivity(13152): onDestroy

3.Activity的生命周期变化(不考虑透明影响):
A跳转到B:onPause(A)->onCreate(B)->onStart(B)->onResume(B)->onSaveInstanceState(A)->onStop(A)->onDestroy(A 假如AFinish才有)
B返回到A:onPause(B)->onRestart(A)->onStart(A)->onResume(A)->onStop(B)->onDestroy(B)


```

[详见Demo](https://github.com/AndBird/Demo/tree/master/Activity%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F%E6%80%BB%E7%BB%93)
