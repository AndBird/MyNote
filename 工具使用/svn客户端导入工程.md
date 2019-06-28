# svn客户端导入工程

### TortoiseSVN导入工程
 1. 使用svn管理项目时，被管理项目通过svn客户端导入<br>
   选中要导入文件 -> 右键 -> 导入<br>
 2. 把导入的工程 checkout下来， 将要忽略的文件忽略掉<br>
    以安卓工程为例：<br>
    (1)bin文件 -> 右键 -> Unversion and add to ignore list  <br>
    (2)最后将工程commit，这样bin就被忽略掉了（显示被忽略图标）<br>
    <tr>其中.settings、bin、gen、proguard、classpath 几个文件容易冲突，故新建工程时忽略掉，然后大家可以更新使用了<br>
 
### Android Studio 与TortoiseSVN
 
#### 1.安装配置svn
 
1.安装  
&emsp;&emsp;需注意，TortoiseSvn安装时需安装 command line功能。  
2. 配置  
(1)打开Android Studio–>File–>Setting–>Version Control–>Subversion  
(2)配置svn路径：在上图中的Use Command Line Client中添加svn.exe的路径， svn默认路径C:\Program Files\TortoiseSVN\bin\svn.exe。
若是不存在svn.exe，则安装TotoseSVN未安装command line功能，需重新安装  


 #### 2.Android Studio svn忽略文件
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
  #### 3.分支管理
```Java
1.android studio 直接从svn check工程后，点击yes创建工程，然后选择导入工程的路径，后面选择
unmarkall导入工程，否则工程目录结构变化。或者点击no不创建工程，然后通过open导入工程。所有
的module都会变独立的模块，改变工程结构。
2.主干合并到分支如果选择最新更新，那么分支中的已提交过的记录会被主干覆盖，未提交的还存在。


```
