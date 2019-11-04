# Android studio

* [android studio更多](https://github.com/AndBird/MyNote/blob/master/工具使用/android_studio/android_studio_content.md)

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

2.R类的路径
/build/generated/not_namespaced_r_class_sources/debug/generateDebugRFile/out/包名/R文件

3.默认签名文件
路径：C:\Users\Administrator\.android\debug.keystore
别名:androiddebugkey
密码:android

4.混淆配置
(1)添加基础混淆配置和其它公共库的混淆配置
(2)如果R文件不需要混淆
# 对于R（资源）类中的静态属性不能被混淆
-keepclassmembers class **.R$* {
 public static <fields>;
}
#或者
#-keep public class **.R$*{
#   *;
#}
(3)对于aar, sdk对外类不混淆
#sdk
#不混淆sdk对外类
-dontwarn a.b.**
-keep class a.b {*;}
-keep interface b.ce$AAListener {*;}
#end sdk
(4)sdk允许混淆Activity
#允许activity混淆(注释下行)
#-keep public class * extends android.app.Activity
#保持BaseActivity混淆
-keep class a.b.BaseActivity { public *;}



5.导出aar
(1)配置混淆文件，但R文件不能混淆
# 对于R（资源）类中的静态属性不能被混淆
-keepclassmembers class **.R$* {
 public static <fields>;
}
#或者
#-keep public class **.R$*{
#   *;
#}

(2)将混淆文件文件打包到aar
consumerProguardFiles 'proguard-rules.pro'

示例：
buildTypes {
    release {
        //是否启用混淆
        minifyEnabled true
        //混淆文件
        proguardFiles 'proguard-rules.pro'
        //混淆文件一起打包
        //consumerProguardFiles 'proguard-rules.pro'
        //多个混淆配置文件：
        //consumerProguardFiles 'proguard-a.pro','proguard-b.pro'或consumerProguardFiles fileTree(dir: projectDir, include: 'proguard*')
    }
}
(3)生成aar
a.release版本
As页面右侧 Gradle  双击assmeableRelease或者右键运行assmeableRelease,运行结束后在项目的build/outputs/aar文件夹,里面有一个xxx-release.aar文件,这就是我们要生成的aar.
b.debug版本
直接build-->make module，在该module的build/output/aar目录中会生成xxx-debug.aar包

6.引用aar
(1)在主工程中应用aar
a.将ab.aar复制到主工程libs中
b.配置build
android {
    ...
    
    
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
}

dependencies {
    ...
    
    
    implementation(name: 'ab', ext: 'aar')
}

(2)在library中引用aar
a.先按(1)在library moduleA中bulid.gradle中配置
b.任何依赖此library moduleA的module，必须在它的build.gradle声明此aar的lib所在的位置(这个位置根据文件路径所确定)
android{
    repositories {
          ...
          
          /library moduleA引用aar
          flatDir {
            dirs 'libs', '../moduleA/libs'
          }
    }
}

或者在project的build.gradle文件中配置，如下
allprojects {
    repositories {
        ...
        
        //library moduleA引用aar
        flatDir {
            dirs 'libs', '../moduleA/libs'
        }
    }
}


7.过滤so
在引用AAR的时候排除armeabi-v7a目录下的.so文件

android {
    ....
    defaultConfig {
        ....
        ndk {
            abiFilters "armeabi"
        }
    }
}

```

