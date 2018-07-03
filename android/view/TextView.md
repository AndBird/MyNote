*  TextView设置最长显示内容 </br>
```
android:maxEms设置最长显示内容(显示字符数不定),会换行,用android:layout_width="wrap_content"
android:maxLength设置最长显示内容(显示字符个数定),不会换行,用android:layout_width="wrap_content"
参考链接： https://blog.csdn.net/beiminglei/article/details/9317997
```


* TextView可滚动
```Java
 android:scrollbars="vertical"
 android:paddingRight="3dp"
 android:scrollbarStyle="outsideOverlay" 
 
 
 textView.setMovementMethod(ScrollingMovementMethod.getInstance());
```
* 测量文字宽度
```Java
textView.getPaint().measureText(textView.getText().toString())
```
