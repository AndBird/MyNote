# 目录

* 防火墙

  防火墙状态: service iptable status
  
  临时关闭防火墙: servcie iptables stop 
  
* jdk 安装
```Java
  1. 下载jdk
    在官网下载http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    然后上传到linux中，并解压
    tar -xf jdk-8u151-linux-x64.tar.gz<br>
  2. 配置环境变量
    打开/etc/profile文件
    vim /etc/profile
  
    加入下面内容
    JAVA_HOME=/usr/local/kencery/javajdk
    PATH=$JAVA_HOME/bin:$PATH
    CLASSPATH=$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
    export PATH JAVA_HOME CLASSPATH
             
    配置完成之后，最重要的一步就是使文件立即生效<br>
    source /etc/profile
```  
  
* tomcat安装
```Java
    1. 下载tomcat
      在https://tomcat.apache.org/download-90.cgi选择tomcat版本下载地址，
      wget http://mirrors.hust.edu.cn/apache/tomcat/tomcat-8/v8.5.24/bin/apache-tomcat-8.5.24.tar.gz
      下载完后解压
      tar -xf apache-tomcat-8.5.24.tar.gz 
    2. 启动tomcat
      启动tomcat,进入apache-tomcat-8.5.24/bin
      sh startup.sh
```
    
* 配置tomcat开机自启动
```Java
网上有很多人自己写了脚本用于tomcat的自启动。实际上，完全没有必要，因为tomcat也自带了脚本，
只需稍作修改即可用于注册服务。我们只需要将%TOMCAT_HOME%/bin/catalina.sh文件拷贝
到/etc/init.d/文件夹下，然后稍作编辑，最后注册成系统服务，是否设置自启动均可

 1. 复制catalina.sh 到/etc/init.d目录下，命名为tomcat
 2. vim tomcat,并新增
    JAVA_HOME=/disk/java/jdk/jdk1.8.0_151
    CATALINA_HOME=/disk/apache-tomcat-8.5.24
    CLASSPATH=.:${JAVA_HOME}/lib:$CATALINA_HOME/lib
 3. 授权可执行文件
    chmod a+x tomcat  
 4. 注册系统服务
    chkconfig –-add tomcat
    如果报错，提示service tomcat does not support chkconfig，那么需要在tomcat文件头中新增
    # chkconfig: 2345 10 90 
    # description: tomcat autorun
    
    注：
   其中2345是默认启动级别，级别有0-6共7个级别。

　　等级0表示：表示关机 　　

　　等级1表示：单用户模式 　　

　　等级2表示：无网络连接的多用户命令行模式 　　

　　等级3表示：有网络连接的多用户命令行模式 　　

　　等级4表示：不可用 　　

　　等级5表示：带图形界面的多用户模式 　　

　　等级6表示：重新启动

   10是启动优先级，90是停止优先级，优先级范围是0－100，数字越大，优先级越低

5. 查看服务配置情况
   chkconfig --list 将得到如下信息：
   network        	0:off	1:off	2:on	3:on	4:on	5:on	6:off
   tomcat         	0:off	1:off	2:on	3:on	4:on	5:on	6:off
6. 测试
   service tomcat start 启动或者通过重启系统
   service tomcat stop  停止
   service tomcat version
   
7. 查看tomcat是否运行
   ps -ef |grep tomcat
   如果返回类似以下信息说明tomcat没有启动
   502 19258  8770   0  7:14下午 ttys000    0:00.01 grep tomcat

  如果返回类似以下信息出现，说明tomcat是启动了，第一个是启动该进程的用户，第二个是该进程的id，第三个 是占用CPU的百分比，
  第四个是占用内存的百分比
  # ps -ef|grep tomcat
  root       881     1  2 21:25 ?        00:00:02 /disk/java/jdk/jdk1.8.0_151/bin/java -   
  Djava.util.logging.config.file=/disk/apache-tomcat-8.5.24/conf/logging.properties -     
  Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djdk.tls.ephemeralDHKeySize=2048 -
  Djava.protocol.handler.pkgs=org.apache.catalina.webresources -Dignore.endorsed.dirs= -classpath /disk/apache-tomcat-
  8.5.24/bin/bootstrap.jar:/disk/apache-tomcat-8.5.24/bin/tomcat-juli.jar -Dcatalina.base=/disk/apache-tomcat-8.5.24 -
  Dcatalina.home=/disk/apache-tomcat-8.5.24 -Djava.io.tmpdir=/disk/apache-tomcat-8.5.24/temp 
  org.apache.catalina.startup.Bootstrap start
  root      1162  1125  0 21:27 pts/0    00:00:00 grep --color=auto tomcat



```

* svn的搭建

  [阿里云参考地址](https://help.aliyun.com/document_detail/52864.html)

```Java
1. 本地安装，需要关联许多包
  在http://subversion.apache.org/上选择版本下载地址
  wget http://mirrors.hust.edu.cn/apache/subversion/subversion-1.9.7.tar.gz
  tar -xf subversion-1.9.7.tar.gz

2. yum install subversion 
  注：在线安装和配置svn请参考阿里云(上面的地址)

  查看版本号
  svnserve --version
  启动svn
  svnserve -d -r /库地址
  关闭svn
  killall svnserve
  查看启动端口(默认端口3690)
  netstat -antp |grep svn

  客户端访问
  svn://ip:端口或者svn://ip


```


* svn 开机自启（法1）

```Java

安装好 svn 服务后，默认是没有随系统启动自动启动的， CentOS 7 的 /etc/rc.d/rc.local 是没有执行权限的， 
系统建议创建 systemd service 启动服务,于是查看 systemd 里 svn 的配置文件 /lib/systemd/system/svnserve.service

1. vim /lib/systemd/system/svnserve.service
  [Unit]  
  Description=Subversion protocol daemon  
  After=syslog.target network.target  

  [Service]  
  Type=forking  
  EnvironmentFile=/etc/sysconfig/svnserve  
  ExecStart=/usr/bin/svnserve --daemon --pid-file=/run/svnserve/svnserve.pid $OPTIONS  

  [Install]  
  WantedBy=multi-user.target  

  可以发现svn指向：/etc/sysconfig/svnserve

2. 修改/etc/sysconfig/svnserve文件
  vi /etc/sysconfig/svnserve
  将 OPTIONS="-r /var/svn" 改为 svn版本库存放的目录

  注：/var/svn为svn默认目录(阿里云安装svn，创建版本库时的命令svnadmin create /var/svn/svnrepos，指向的就是/var/svn，
  这样就能保证svn开机自启动)，安装时指定的默认库目录是/var/svn就不需要修改本文件了，如果使用的是自定义库目录，
  需要修改本文件才能开机自启

3. 让服务生效
  systemctl enable svnserve.service

4. 重启系统
  通过netstat -antp |grep svn命令就能知道svn已经自启动
  

```

* svn 开机自启（法2）
```Java
  安装好 svn 服务后，默认是没有随系统启动自动启动的， CentOS 7 的 /etc/rc.d/rc.local 是没有执行权限的
  
  1. 先授权rc.local可执行权限
     cd /etc/rc.d/
     chmod a+x rc.local
     ll 查看文件权限
     
  2. 编辑rc.local文件
    /usr/bin/svnserve -d -r /svn库目录
    
    注：在/etc/rc.d/rc.local 文件中svnserve必须写上完整的路径,如果不知道svnserve安装在哪，可以通过如下命令：
      whereis svnserve 查找svn的安装目录


```

* shadowsocks server
  
  [参考链接](https://github.com/shadowsocksr-backup/shadowsocksr)

```Java
    1. 安装
    yum install python-pip
    pip install shadowsocks
    
    2. 使用
    前端运行
    ssserver -p 443 -k password -m aes-256-cfb
    后台运行
    ssserver -p 443 -k password -m aes-256-cfb --user nobody -d start
    停止运行
    sudo ssserver -d stop
    日志
    less /var/log/shadowsocks.log

```


