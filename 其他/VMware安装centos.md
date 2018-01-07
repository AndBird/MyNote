# VM安装Centos

```java
1. centos不能上网(桥接)
vim /etc/sysconfig/network-scripts/ifcfg-eth0

将ONBOOT=no改为ONBOOT=yes

然后service network restart ，可能提示使用systemctl，重启网络后就行了

2. xshell连接vm上的centos
在centos上用ifconfig获取到ip，用ip连接
```
