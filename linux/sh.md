* sh

作用： sh命令是shell命令语言解释器，执行命令从标准输入读取或从一个文件中读取。通过用户输入命令，和内核进行沟通
语法： sh [options] [file]

```Java
-c string：命令从-c后的字符串读取。 
-i：实现脚本交互。 
-n：进行shell脚本的语法检查。
-x：实现shell脚本逐条语句的跟踪。

例如：
sh startup.sh
```
