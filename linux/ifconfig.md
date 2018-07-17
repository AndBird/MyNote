* ifconfig
```Java
说明: ifconfig命令被用于配置和显示Linux内核中网络接口的网络参数。用ifconfig命令配置的网卡信息，
在网卡重启后机器重启后，配置就不存在。要想将上述的配置信息永远的存的电脑里，那就要修改网卡的配置文件了。

(1)查看ip
ifconfig
(2)设置ip
ifconfig eth0 192.168.xxx.xxx
(3)
//启动网卡eth0
ifconfig eth0 up
//关闭网卡eth0
ifconfig eth0 down
```
