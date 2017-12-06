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
网上有很多人自己写了脚本用于tomcat的自启动。实际上，完全没有必要，因为tomcat也自带了脚本，只需稍作修改即可用于注册服务。我们只需要将%TOMCAT_HOME%/bin/catalina.sh文件拷贝到/etc/init.d/文件夹下，然后稍作编辑，最后注册成系统服务，是否设置自启动均可

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
在http://subversion.apache.org/上选择版本下载地址
wget http://mirrors.hust.edu.cn/apache/subversion/subversion-1.9.7.tar.gz

tar -xf subversion-1.9.7.tar.gz


在线安装参考阿里云地址

启动svn
svnserve -d -r /库地址
查看启动端口
netstat -antp |grep svn

客户端访问
svn://ip:端口或者svn://ip


```
