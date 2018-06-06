# ListView相关属性

```
1. listview的背景色默认是透明的

2. item选中时的背景设置
android:listSelector 或者listview.setSelector()

3. listview拖动时的背景色
listView.setCacheColorHint(0); 
或者android:cacheColorHint="#000000" 去除listview的拖动背景色

4. listview在拖动的时候背景图片消失变成黑色背景，等到拖动完毕我们自己的背景图片才显示出来
解决：android:scrollingCache="false" 或 android:cacheColorHint="#00000000″

5. listview的上边和下边有黑色的阴影
android:fadingEdge="none"

```
