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
