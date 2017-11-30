
* adb安装系统应用(要求能收到开机广播)
```Java
1. 有root权限

adb push 安装应用(system/app或者system/priv-app)：
法一：(对于多设备连接时用-s devicename选择设备)
当adb启动失败时，用adb kill-server 和adb start-server启动
（1） 先修改system/app的权限
adb (-s devicename) shell
$ su
# mount -o remount rw system
# exit
$ exit
（2）adb (-s devicename) push src_apk system/app
注：设备重启， stop -> start(su下)

法二：（mount修改读写权限失败时）
（1） 先修改system/app的权限
adb (-s devicename) shell
$ su
#mount -o remount rw system
# chmod 777 system（也许用此行就行）
# cd system
# chmod 777 app
# exit
$ exit
（2）adb (-s devicename) push src_apk system/app
注：设备重启， stop -> start(su下)

参考:http://wenku.baidu.com/link?url=zmkw3JStt-LXgK_1kLA3s6jhYdjbbpeEaL2gbQz2jEw_ewhmedsXbA7O_s5N3wjgs7z03tuqjhiLozNp4etDfzv8uwN1RAZ_CUPoCI7MIxu

用以上方法adb push apk会"应用 -> 全部" 中看到被安装的应用，能够接受系统广播并启动(首次启动)

//发送开机广播
adb shell -am broadcast -a android.intent.action.BOOT_COMPLETED
adb shell -am broadcast -a android.intent.action.BOOT_COMPLETED -n apk包名/广播接收包名.接收类



==========================================================================
错误安装方法(拷文件可以)
adb push src_apk /sdcard/

adb shell
$ su
# mount -o remount rw system
# cp sdcard/src_apk_name system/app

此种方式的apk无法接受到系统广播，因而无法自启动
```
