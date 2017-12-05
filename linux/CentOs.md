# 目录

* 防火墙

  防火墙状态: service iptable status
  
  临时关闭防火墙: servcie iptables stop 
  
* jdk 安装

  (1)下载<br>
  在官网下载http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
  <br>然后上传到linux中，并解压
  tar -xf jdk-8u151-linux-x64.tar.gz<br>
  (2)配置环境变量<br>
  打开/etc/profile文件
  vim /etc/profile
  
  加入<br>
  JAVA_HOME=/usr/local/kencery/javajdk
             PATH=$JAVA_HOME/bin:$PATH
             CLASSPATH=$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
             export PATH JAVA_HOME CLASSPATH
             
  配置完成之后，最重要的一步就是使文件立即生效<br>
  source /etc/profile
  
  
* tomcat
  
    (1)下载<br>
    在https://tomcat.apache.org/download-90.cgi选择tomcat版本下载地址，
    wget http://mirrors.hust.edu.cn/apache/tomcat/tomcat-8/v8.5.24/bin/apache-tomcat-8.5.24.tar.gz
    <br>下载完后解压
    tar -xf apache-tomcat-8.5.24.tar.gz 
    <br>
    启动tomcat,进入apache-tomcat-8.5.24/bin
    <br>  sh startup.sh
