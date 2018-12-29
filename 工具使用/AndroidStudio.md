# Android studio
#  1.gradle 下载的jar或者aar的位置
```Java
可能路径1：
在C:\Users\你的用户名\.gradle\caches\modules-2\files-2.1\jar的包名目录下
或者C:\Users\Administrator\.gradle\caches\transforms-1\files-1.1
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

# 3.android studio svn忽略文件
```Java
as svn 忽略文件需要在工程与svn关联前处理，忽略文件添加方式:
法1：
File->Setting->Version Control->Ignored Files 添加忽略文件
法2：工程内的文件中加入忽略配置
在.idea->workspace.xml中，<component>内新加忽略配置，<ignored/>标签

<component name="ChangeListManager"> 
    <list default="true" id="98591f63-0b13-45b3-9e45-edfa0345c7da" name="Default" comment=""> 
...
...
 </list>
 
<ignored path=".idea/" />
<ignored path=".gradle/" />
<ignored path="build/" />
<ignored mask="*.iml" />
<ignored path="local.properties" />
<ignored path="modulename/build/" />
....
....
</component>

```
# 4.分支管理
```Java
1.android studio 直接从svn check工程后，点击yes创建工程，然后选择导入工程的路径，后面选择
unmarkall导入工程，否则工程目录结构变化。或者点击no不创建工程，然后通过open导入工程。所有
的module都会变独立的模块，改变工程结构。
2.主干合并到分支如果选择最新更新，那么分支中的已提交过的记录会被主干覆盖，未提交的还存在。


```

# 使用技巧
```Java
1.lint功能
Android Studio 中，Android Lint 已经被集成，只需要点击菜单 —— Analyze —— Inspect Code 即可运行 Android Lint
2.删除无用的资源
在Anaylze中选择Run Inspection by Name...，在点击之后弹出的窗口输入unused resources后，回车查找无用的资源


```

