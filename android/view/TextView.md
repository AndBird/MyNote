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

* 设置字体样式
```
Spannable.SPAN_EXCLUSIVE_EXCLUSIVE //前后都不包括
Spannable.SPAN_INCLUSIVE_EXCLUSIVE  //前包括后不包括
Spannable.SPAN_EXCLUSIVE_INCLUSIVE  //前不包括后包括
Spannable.SPAN_INCLUSIVE_INCLUSIVE  //前后都包括



SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
SpannableString spannableString = new SpannableString("测试");
spannableStringBuilder.append(spannableString);
SpannableString spannableString1 = new SpannableString("7");
ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#ff5400"));
spannableString1.setSpan(colorSpan, 0, spannableString1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
spannableString1.setSpan(styleSpan_B, 0, spannableString1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
spannableString1.setSpan(new AbsoluteSizeSpan((int)getResources().getDimension(R.dimen.font_size_6)), 0, spannableString1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
spannableStringBuilder.append(spannableString1);
spannableStringBuilder.append(new SpannableString("end"));
spannableStringBuilder.append("121");
textView2.setText(spannableStringBuilder);
```
