
* grep

  作用: 该命令常用于分析一行的信息，若当中有我们所需要的信息，就将该行显示出来，该命令通常与管道命令一起使用，用于对一些命令的输出进行筛选加工等等.
    
  语法: grep [-acinv] [--color=auto] '查找字符串' filename  

  ```Java
  -a ：将binary文件以text文件的方式查找数据  
  -c ：计算找到‘查找字符串’的次数  
  -i ：忽略大小写的区别，即把大小写视为相同  
  -v ：反向选择，即显示出没有‘查找字符串’内容的那一行  

  例如：
  # 取出文件/etc/man.config中包含MANPATH的行，并把找到的关键字加上颜色
  grep --color=auto 'MANPATH' /etc/man.config    
  # 把ls -l的输出中包含字母file（不区分大小写）的内容输出
  ls -l | grep -i file   
  ```
    
