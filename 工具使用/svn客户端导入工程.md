* svn客户端导入工程

 1. 使用svn管理项目时，被管理项目通过svn客户端导入<br>
   选中要导入文件 -> 右键 -> 导入<br>
 2. 把导入的工程 checkout下来， 将要忽略的文件忽略掉<br>
    以安卓工程为例：<br>
    (1)bin文件 -> 右键 -> Unversion and add to ignore list  <br>
    (2)最后将工程commit，这样bin就被忽略掉了（显示被忽略图标）<br>
    <tr>其中.settings、bin、gen、proguard.classpath 几个文件容易冲突，故新建工程时忽略掉，然后大家可以更新使用了<br>
