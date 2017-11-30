* 禁止GridView滚动
```Java
 //自定义GridView,并重写下面的方法
 public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;  //禁止GridView滑动
        }
        return super.dispatchTouchEvent(ev);
    }
```
