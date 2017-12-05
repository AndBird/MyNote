* chgrp

作用： 该命令用于改变文件所属用户组

```Java
chgrp [-R] dirname/filename  
-R ：进行递归的持续对所有文件和子目录更改  
# 例如：  
chgrp users -R ./dir # 递归地把dir目录下中的所有文件和子目录下所有文件的用户组修改为users  
```
