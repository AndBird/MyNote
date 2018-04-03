
# Instrumentation
* 使用权限 </br>
 ```Java
  1. Instrumentation 需要在线程中运行才有效
  2. 不需要root也可以生效，但是对于非系统应用只在本进程中才生效(特别注意)
  3. 需要声明权限
    <!-- Instrumentation 跨进程注入事件，在线程中才有效 -->
    <uses-permission android:name="android.permission.INJECT_EVENTS" /> 
 ```
 
 * 使用实例
 ```Java
    Instrumentation inst=new Instrumentation();  
 
    1.点击触摸事件
    MotionEvent downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0);
    instrumentation.sendPointerSync(downEvent);
    downEvent.recycle();
    MotionEvent moveEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, x, y, 0);
    instrumentation.sendPointerSync(moveEvent);
    moveEvent.recycle();
    MotionEvent upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0);
    instrumentation.sendPointerSync(upEvent);
    upEvent.recycle();
    
    2.输入字符串
    注意:需要先点击输入框，内容才能输入进去
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
    inst.sendStringSync("aaccdd");
    
    3.输入按键事件		
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
 ```

# input
```Java
使用input分发事件，有root权限时可以跨进程分发事件；无root权限时，能在本进程中分发事件
Runtime.getRuntime().exec("input tap 200 200");
Runtime.getRuntime().exec("input text sssa");
```
