# Flutter开发环境搭建

教程
https://flutter-io.cn/docs/get-started/install/windows

* 系统配置要求(教程中的步骤，待考究=>无需安装，如果命令行git下载flutter sdk或者下载app的依赖库则需要)
要想安装和运行 Flutter，你的开发环境至少应该满足如下的需求：
操作系统：Windows 7 SP1 或更高的版本（64 位操作系统）。
磁盘空间：除安装 IDE 和一些工具之外还应有至少 400 MB 的空间。
工具：要让 Flutter 在你的开发环境中正常使用，依赖于以下的工具：
Windows PowerShell 5.0 或者更高的版本（Windows 10 中已经预装了）
(地址https://docs.microsoft.com/zh-cn/powershell/scripting/install/installing-windows-powershell?view=powershell-6)
Git for Windows 2.x，并且勾选从 Windows 命令提示符使用 Git 选项。
如果 Windows 版的 Git 已经安装过了，那么请确保能从命令提示符或者 PowerShell 中直接执行 git 命令。


* 下载Flutter SDK

1.下载最新版本 stable 的 Flutter SDK
https://flutter.dev/docs/development/tools/sdk/releases

2.将压缩包解压，然后把其中的 flutter 目录整个放在你预想的 Flutter SDK 安装目录中（比如 C:\src\flutter；请勿将该目录放在一些需要额外操作权限的目录，比如 C:\Program Files\）。

3.找到 flutter 目录中的 flutter_console.bat 文件，双击执行该批处理脚本

4.更新 path 环境变量
如果你想要在普通的 Windows 控制台中使用 Flutter 命令，那就需要按照下面的步骤来将 Flutter 的二进制文件路径加入到 PATH 环境变量。
(1)在开始菜单的搜索功能键入“env”，然后选择 编辑当前用户的环境变量
(2)加入到path中(:\src\flutter\bin)
在 用户变量 一栏中，检查是否有 Path 这个条目：
如果存在，直接把 flutter\bin 目录的完整路径以 ; 作为分隔加到已有的值后面。
如果不存在的话，在用户环境变量中创建一个新的 Path 变量，然后将 flutter\bin 所在的完整路径作为新变量的值。
注意，你需要关闭和重新启动已经打开的各个控制台窗口，这样下次启动控制台时才能访问到刚才修改的变量。

5，运行 flutter doctor
在命令行运行flutter doctor。上述命令会检查你的现有环境，然后把检测结果以报告形式呈现出来。仔细阅读它显示的内容，检查是否有尚未安装的软件或是有其他的步骤需要完成（通常会以粗体呈现）。上述命令会检查你的现有环境，然后把检测结果以报告形式呈现出来。仔细阅读它显示的内容，检查是否有尚未安装的软件或是有其他的步骤需要完成（通常会以粗体呈现）。
(1)问题：License for package Android SDK Platform 27 not accepted.
解决：flutter doctor --android-licenses

* IDE
1.安卓Android Studio 3.0 或之后的版本
2. 安装Flutter 和 Dart 插件
打开 Android Studio->File>Settings>Preferences->Plugins
，选择 Browse repositories，然后选择 Flutter 插件并点击 安装。
当弹出安装 Dart 插件提示时，点击 Yes。当弹出重新启动提示时，点击 Restart。

* 创建app
1.打开 IDE，选择新 Flutter 项目 (Start a new Flutter project).
2.选择 Flutter 应用程序 作为项目类型，然后点 下一步, 确认 Flutter SDK 路径 区域所示路径是正确的 SDK 路径。
3.输入项目名称(比如 ‘myapp’), 然后点击下一步。点击 完成。
