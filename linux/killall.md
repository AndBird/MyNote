* killall

作用： 该命令用于向一个命令启动的进程发送一个信号

语法： killall [-iIe] [command name] 

```Java
-i ：交互式的意思，若需要删除时，会询问用户  
-e ：表示后面接的command name要一致，但command name不能超过15个字符  
-I ：命令名称忽略大小写  

# 例如：  
killall -SIGHUP syslogd # 重新启动syslogd  
```
