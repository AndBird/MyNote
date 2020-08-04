# clone下来的flutter工程问题修复

```Java
说明:clone的工程是通过git提交的，clone下来后，导入到android studio后，工程结构异常(android module无法识别，java类红线显示,不利于编写代码)，下面将进行修复(假设工程目录问myflutter)

1.导入工程：通过File->open或者Open an existing Android Studio project导入，然后通过Tools->Flutter->Flutter Pub Get或者右键pubspec.yaml->Flutter->Flutter Pub Get修复下lib目录下的.dart文件报错问题。

工程导入后，module只剩下一个，module android部分在Project Structure中无法识别，java类也报错，build.gradle文件也报错，但不影响工程正常运行。

2.修改language:打开File->Project Structure->Project Settings->Project->Project language level,选择language版本13及其他(太低，java类中关键字报错),这一步也可以后面弄

3.配置android module:
(1)打开File->Project Structure->Project Settings->Modules,点击复制，输入module name为"工程名_android",如myflutter_android,选择Module file location 为工程目录下的android目录。
(2)切换到myflutter_android->Dependencies,保留<No SDK>和<Module source>,配置SDK版本，删除其它如Dart SDK, Dart Packages,Flutter Plugins(需要点击Problems栏修复才有)。
(3)完成(2)后，点击+->Library->New Library->Java(弹窗下拉窗，直接回车，鼠标无法选中)->然后选择文件flutter.jar(注意：flutter sdk目录/bin\cache\artifacts\engine\android-arm\flutter.jar),然后输入Library name(如flutter for Android)->点击确定->Added Selected，然后一路保存。
(4)展开android module,右键文件夹java->Mark Directory as->Sources Root

经过前面4步，项目中的android部分基本能正常显示了。




其它：
打开File->Project Structure->Project Settings->Modules->myflutter_android,如果在myflutter_android下右键Add Android,配置如下目录
...\myflutter\android\app\src\main\AndroidManifest.xml
...\myflutter\android\app\src\main\res
...\myflutter\android\app\src\main\assets
...\myflutter\android\app\src\main\libs

但是这样会导致File菜单下面的Project Structure不显示。


```