#

* 创建Java工程


* 将class文件转换成dex、运行
```Java
1、dx工具：
将.class转为dex的工具dx所在目录

a.老版本的sdk存放在SDK\platform-tools下
b.新的sdk存放在SDK\build-toolsx\xx.xx(版本号)下

注意：dx并没有配置到环境变量中,需要到对应目录下输入命令行.

2.javac或者使用IDE运行一下,HelloWorld.java将生成.class文件

3.cmd进入dx工具目录:

先将class资源或者jar复制到dx工具目录

dx --dex --output=C:\Users\Administrator\Desktop\test.dex .\com\text\HelloWorld.class

指令说明:dx --dex --output=(输出路径) (.class文件路径)

注意：在输入.class的路径时,直接从桌面上拖到cmd上输入了绝对路径,dx的.class文件不支持绝对路径,只能放到dx目录下,使用相对路径.


注意:如果需要打包多个class文件到dex中，可以先将多个class转换成test.jar,然后将test.jar转换成dex
dx --dex --output=C:\Users\Administrator\Desktop\test.dex .\test.jar


4.将test.dex推到sd卡中
adb push C:\Users\Administrator\Desktop\test.dex /sdcard

5.运行

(1)若push 到/data/local/tmp中，运行指令：
#cd /data/local/tmp
#app_process -Djava.class.path=test.dex  /data/local/tmp com.text.HelloWorld

或者
#app_process -Djava.class.path=/data/local/tmp/test.dex  /data/local/tmp com.text.HelloWorld

(2)若push到sd卡中，运行指令
#app_process -Djava.class.path=/sdcard/test.dex  /sdcard com.text.HelloWorld

```