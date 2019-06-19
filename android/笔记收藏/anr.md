# Application Not Responding

#### 1.ANR产生的原因
只有当应用程序的UI线程响应超时时才会引起ANR,超时产生原因一般有2种。
* 当前的事件没有机会得到处理，例如UI线程正在响应另外一个事件，当前事件由于某种原因被阻塞了。
* 当前的事件正在处理，但是由于耗时太长没能及时完成。

```
根据ANR产生的原因不同，超时时间也不尽相同，从本质上讲，产生ANR的原因有3种，大致可以对应到Android中四大组件中的三
个(Activity/View、BroadcastReceiver和Service)

KeyDispatchTimeout 最常见的一种类型，原因是View的按键事件或者触摸事件在特定的时间(5秒)内无法得到响应。
BroadcastTimeout 原因是BroadcastReceiver的onReceiver函数运行在主线程中，在特定的时间(10秒)内无法完成处理。
ServiceTimeout比较少出现的一种类型，原因是Service的各个生命周期函数在特定时间(20秒)内无法完成处理。

```

#### 2.ANR的定位和分析
当发生ANR时，开发者可以通过结合Logcat和生成的位于手机内部存储的/data/anr/traces.txt文件进行分析和定位。

#### 3.ANR的避免和检测
* 1.StrictMode
<br>严格模式StrictMode是Android SDK提供的一个用来检测代码中是否存在违规操作的工具类。StrictMode主要检测两大
类问题。
  * 线程策略ThreadPolicy
     *  detectCustomSlowCalls:检测自定义耗时操作
     *  detectDiskReads:检测是否存在磁盘读取操作
     *  detectDisjWrites:检测是否存在磁盘写入操作
     *  detectNetwork:检测是否存在网络操作
  * 虚拟机策略VmPolicy
     * detectActivityLeaks:检测是否存在Activity泄露
     * detectLeakedClosableObjects:检测是否存在未关闭Closable对象泄露
     * detectLeakedSqliteObjects:检测是否存在Sqlite对象泄露
     * setClassInstanceLimit:检测类实例个数是否超过限制
ThreadPolicy可以用来检测可能存在的主线程耗时操作，解决这些检测到的问题能够减少应用发生ANR的概率。需要注意的是，只能在Debug版本中使用它，发布到市场上的版本要关闭掉。StrictMode的使用很简单，我们只需要在应用初始化的地方例如Application或者MainActivity类的onCreate方法中执行如下代码即可。
```
if(BuildConfig.DEBUG){
			//开启线程模式
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
			//开启虚拟机模式
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

			/**
			 * 初始化代码penaltyLog表示在Logcat中打印日志，调用detectAll方法表示启动所有的检测策略，可以根据应用的
			 * 具体需求只开启某些策略
			 *
			 * */

		}
```
* 2.BlockCanary
BlockCanary是一个非侵入式的性能监控函数库，它的用法和LeakCanary类似，只不过后者监控应用的内存泄露，而BlockCanary主要用来监控应用主线程的卡顿。它的基本原理是利用主线程的消息队列处理机制，通过对比消息分发开始和结束的时间点来判断是否超过设定的时间，如果是，则判断为主线程卡顿。
```
在build.gradle中添加在线依赖
dependencies{
	compile ‘com.github.moduth:blockcanary-android:1.2.1’
	
	//仅在debug包启用BlockCanary进行卡顿监控和提示的话，可以这么用
	debugCompile ‘com.github.moduth:blockcanary-android:1.2.1’
	releaseCompile 'com.github.moduth:blockcanary-no-op:1.2.1'
}

然后在Application类中onCreate()进行配置和初始化即可:
//在主进程初始化调用
BlockCanary.install(this,  new AppBlockCanaryContext()).start();

public class AppBlockCanaryContext extends BlockCanaryContext{
/*实现各种上下文，包括应用标识符、用户uid、网络类型、卡慢判断阈值、Log保存位置等，更多详细的信息可以参见官网说明*/
}



```
