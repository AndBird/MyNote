# Android studio
#  1.gradle 下载的jar或者aar的位置
```Java
可能路径1：
在C:\Users\你的用户名\.gradle\caches\modules-2\files-2.1\jar的包名目录下
可能路径2：
在android studio sdk目录\extras\android\m2repository\jar的包名目录下
可能路径3(已解压)：
android studio工程目录\app\build\intermediates\exploded-aar\jar的包名目录下

```

# 2.xx.aar包导入到eclipse中
```Java
步骤1：将xx.aar后缀改成xx.zip,然后将其解压到xx文件夹
步骤2：将eclipse工程中的.classpath、.project、project.properties拷贝至xx文件夹中,并将.project中的工程名修改成xx
步骤3：将xx工程导入到eclipse中，修改classes.jar为xx.jar,并放置到libs文件夹中,并将xx工程设置为Is Library,并编译通过
步骤4：将xx library作为其它工程的lib工程使用，将 proguard.txt 文件的内容拷贝到你的项目的 proguard-project.txt(混淆配置) 文件中

注:步骤2可改为,
创建 project.properties 文件，加入内容如下：
target=android-22
android.library=true
target 的值随 AAR 里 Android Manifest.xml 文件的 targetSdkVersion 属性值而定
删除 aapt 和 jni 文件夹以及 R.txt 和 proguard.txt 文件

```
