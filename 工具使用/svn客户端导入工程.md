# svn客户端导入工程

#### TortoiseSVN导入工程
 1. 使用svn管理项目时，被管理项目通过svn客户端导入<br>
   选中要导入文件 -> 右键 -> 导入<br>
 2. 把导入的工程 checkout下来， 将要忽略的文件忽略掉<br>
    以安卓工程为例：<br>
    (1)bin文件 -> 右键 -> Unversion and add to ignore list  <br>
    (2)最后将工程commit，这样bin就被忽略掉了（显示被忽略图标）<br>
    <tr>其中.settings、bin、gen、proguard、classpath 几个文件容易冲突，故新建工程时忽略掉，然后大家可以更新使用了<br>
 
 #### Android Studio 与TortoiseSVN
 
 * 安装配置svn
 
1.安装  
&emsp;&emsp;需注意，TortoiseSvn安装时需安装 command line功能。  
2. 配置  
(1)打开Android Studio–>File–>Setting–>Version Control–>Subversion  
(2)配置svn路径：在上图中的Use Command Line Client中添加svn.exe的路径， svn默认路径C:\Program Files\TortoiseSVN\bin\svn.exe。
若是不存在svn.exe，则安装TotoseSVN未安装command line功能，需重新安装  
