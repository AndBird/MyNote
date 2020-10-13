# 常用Adb命令

**目录**

[TOC]

## 1.输入法相关  

	adb shell imei list -s   查看已激活的输入法
	adb shell imei set   设置输入法

## 2.日志相关  
    
     //获取对应包名应用(过滤字符串等)的日志，没有引号会报错
     adb shell "logcat | grep com.包名"                         
     //把信息存放到D盘目录下的1.txt文件，方便后续查看
     adb logcat > D:/1.txt
     //要显示包含FilterStr的字符串
     adb logcat | find "FilterStr"   
     //清除log缓存：
     adb logcat -c
     //查看bug报告：
	 adb bugreport > bugreport.txt
	 
## 3.设备操作
```
//列出设备列表
adb devices
adb -s deveiceName shell ....
//连接调试设备(部分可以不输入端口)
adb connect 192.168.0.101:5555 
adb disconnect
//重启到recovery模式
adb reboot recovery
//重启到fastboot模式
adb reboot bootloader


```
	 
## 4.应用管理
    
 ```
 adb install (-r) apkfile  (如果加-r参数会覆盖原来安装的软件并保留数据)
 adb uninstall (-k) com.xx.xx   (-k 卸载app但保留数据和缓存文件)
 //列出手机上所有已安装的应用包名
 adb shell pm list packages 
 //输出应用的apk文件路径和包名
 adb shell pm list packages -f
 //获取对应包名应用的所有信息
 adb shell dumpsys package com.examle.xx
 //获取当前应用
 adb shell "dumpsys activity | grep mFocusedActivity"
 adb shell dumpsys activity | findstr "mFocusedActivity"
 //列出所有应用的信息
 adb shell dumpsys 列出所有
 //获取指定包名应用的路径
 pm path com.xx.xx 
 //冻结应用
 adb shell pm disable/enable <包名>
 //清除应用数据和缓存
 adb shell pm clear <packagename>
//强制停止应用
adb shell am force-stop <packagename>
//安装apk
pm install -r /system/app/test.apk 
//卸载应用
pm uninstall 应用包名
//查看正在运行的Service(包名是必须的、不需要写完整的包名、支持模糊匹配)
adb shell dumpsys activity services 包名
//查看正在运行的Activity(包名是必须的、不需要写完整的包名、支持模糊匹配)
adb shell dumpsys activity 包名
 ```
	 
## 5.进程管理和内存
```    
//查看设备cpu和内存占用情况：
adb shell top
//查看占用内存前6的app：
adb shell top -m 6
//刷新一次内存信息，然后返回：
adb shell top -n 1
//内存
adb shell dumpsys meminfo

//查看设备的CPU占有率
adb shell top -n 1 -m 10 -d
adb shell dumpsys cpuinfo


//杀死一个进程：
adb shell kill [pid]
adb shell am force-stop [包名] //后面跟的是包名
//查看进程列表：
adb shell ps
//查看指定进程状态：
adb shell ps -x [PID]
//查看后台services信息：
adb shell service list
//查看当前内存占用：
adb shell cat /proc/meminfo
//查看手机剩余内存和总内存的大小
adb chat proc/meminfo 
//cpu 信息
adb shell cat /proc/cpuinfo
//端口（shell下）
netstat
//显示已连接的端口
netstat -ntup 
//显示正监听的端口
netstat -ntupl 
//查看进程使用情况
adb shell dumpsys usage stats

```

## 6.文件操作
 ```   
//文件操作
adb pull/push
adb push src_apk /sdcard/
//复制文件
cp sdcard/src_apk_name system/app
//重命名文件：
adb shell rename path/oldfilename path/newfilename
//删除system/avi.apk：
adb shell rm /system/avi.apk
//删除文件夹及其下面所有文件：
adb shell rm -r <folder>
//移动文件：
adb shell mv path/file newpath/file
//设置文件权限：
adb shell chmod 777 /system/fonts/DroidSansFallback.ttf
//让apk变成可以安装的
chmod 644 /system/app/test.apk
//新建文件夹：
adb shell mkdir path/foldelname
//查看文件内容：
adb shell cat <file>
//查看部分文件(head后面设置行数)
busybox head -10 <file>
busybox tail -10 <file>
//重新挂载并给予可写权限
mount -o remount rw system
//文件md5
md5 /system/app/test.apk 
md5sum filename
//文件夹下所有文件的md5
md5 /system/app/* 
```
	
## 7.事件发送
```    
//发送返回事件(keyCode同KeyEvent)
adb shell input keyevent 4
//发送返回事件(down state)
sendevent /dev/input/event0 1 158 1
sendevent /dev/input/event0 0 0 0 
//发送返回事件(up state)
sendevent /dev/input/event0 1 158 0
sendevent /dev/input/event0 0 0 0 

//input 按键值
final int key_back_value = 158;//0x9e
final int key_up_value = 103;  //0x67
final int key_left_value = 105; //0x69
final int key_right_value = 106; //0x6a
final int key_down_value = 108;//0x6c
final int key_ok_value = 232;//0xe8
final int key_volumedown_value = 114; //0x72
final int key_volumeup_value = 115;//0x73
final int key_home_value = 102; // 0x66  
final int key_menu_value = 139; //0x8b
final int key_power_value = 116;//0x74
//(0x6b 107 key_END)   (0xd3 211 key_HP)   (0xd4 212 KEY_CAMERA)  (0xe7 231 key_send(电话本)) (0xe8 232 key_center)

```
	
## 8.截屏相关
```	
//截屏
adb shell screencap -p /mnt/sdcard/save.png
//录制屏幕
adb shell screenrecord /mnt/sdcard/1.mp4
//设置录制的视频分辨率
adb shell screenrecord --size 848*480 /sdcard/1.mp4
//默认比特率是4M/s，为了分享方便，我们可以调低比特率为2M
adb shell screenrecord --bit-rate 2000000 /sdcard/1.mp4
```

## 9. 发送广播，启动Activity和Service
```	
//发送广播
adb shell -am broadcast -a android.intent.action.BOOT_COMPLETED
adb shell -am broadcast -a android.intent.action.BOOT_COMPLETED -n apk包名/广播接收包名.接收类
//启动activity
adb shell am start --user 0 -n apk包名/Activity包名.Activity类名
//启动service
adb shell am startservice -n apk包名/Service包名.Service类名
```

## 10.设备相关
```
//获取MAC地址
adb shell cat /sys/class/net/wlan0/address
//获取手机IMEI
adb shell dumpsys iphonesubinfo 
//获取设备相关属性
adb shell getprop 属性名
//设备型号
adb shell getprop ro.product.model
//查看安卓系统版本
adb shell getprop ro.build.version.release
//android id
adb shell settings get secure android_id
//屏幕密度（wm命令,模拟器测试通过,可修改分辨率和密度进行适配）
adb shell wm density
//屏幕分辨率(wm命令),可用来模拟不同比例的手机
adb shell wm size 360x780
//恢复分辨率
adb shell wm size reset
//显示屏参数
adb shell dumpsys window displays
//电池相关
adb shell dumpsys battery
//电池相关指令(root可用)
adb shell dumpsys batterystats
//设备停止充电的命令
adb shell dumpsys battery unplug 
//重复该命令，遍历Doze各种状态
adb shell dumpsys deviceidle step
//查看分区逻辑大小
adb shell df
//查看分区物理大小
adb shell cat sys/block/mmcblk0/mmcblk0p*/size
//GPU渲染
adb shell dumpsys gfxinfo <packagename> 
adb shell dumpsys gfxinfo <packagename> framestats


```

## 11.数据库
```
//进入sqlite
adb shell
sqlite3 /data/data/package(应用包名)/databases/dbname
//查看数据库版本
PRAGMA user_version;

```

## 12.设置系统环境变量
```
//设置环境变量test1
setprop test1 value1
//获取环境变量test1
getprop test1
```

## 网络
```
//查看网络状态
adb shell netstat 
//ping 4次后停止
adb shell ping -c 4 www.baidu.com
//查看网络连接
adb shell netcfg
//查看wifi的ip
adb shell ip -f inet addr show wlan0
//查看wifi密码：
adb shell cat /data/misc/wifi/*.conf
//
adb shell ip

```
	
	
## .其他操作
```



//获取设备名称：
adb shell cat /system/build.prop
//跑monkey：
adb shell monkey -v -p your.package.name 500
//查看adb版本
adb version
busybox wget 下载


```
        

	 






