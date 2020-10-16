* Xshell

```Java
1. 上传和下载文件
  rz: 上传  sz:下载
  
  （1）安装
  yum provides */rz
  yum install -y lrzsz
  
 scp:
 从本地复制到远程指令格式：
scp local_file remote_username@remote_ip:remote_folder 
或者 
scp local_file remote_username@remote_ip:remote_file 
或者 
scp local_file remote_ip:remote_folder 
或者 
scp local_file remote_ip:remote_file 

示例：
scp /home/space/music/1.mp3 root@www.runoob.com:/home/root/others/music 
scp /home/space/music/1.mp3 root@www.runoob.com:/home/root/others/music/001.mp3 
scp /home/space/music/1.mp3 www.runoob.com:/home/root/others/music 
scp /home/space/music/1.mp3 www.runoob.com:/home/root/others/music/001.mp3 

从远程复制到本地:
从远程复制到本地，只要将从本地复制到远程的命令的后2个参数调换顺序即可
示例:
scp root@www.runoob.com:/home/root/others/music /home/space/music/1.mp3 
scp -r www.runoob.com:/home/root/others/ /home/space/music/

-r 文件夹

  

2.查看ip
ifconfig

3.xshell连接虚拟机中的ubuntu
(1)桥接模式联网
(2)ifconfig查看ubuntu的ip
(3)使用上面查询到的ip作为主机，端口22，协议ssh,连接到ubuntu

4.xshell连接不上虚拟机中的ubuntu(桥接模式联网)
解决办法:
apt-get install openssh-server （ 我是在root模式下，不是root用户的话要加sudo）
检查ssh服务是否启动（如果没有启动，那么/etc/init.d/ssh start）
ps -ef |grep ssh


```

