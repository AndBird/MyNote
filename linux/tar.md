* tar

作用： 该命令用于对文件进行打包，默认情况并不会压缩，如果指定了相应的参数，它还会调用相应的压缩程序（如gzip和bzip等）进行压缩和解压。
语法： tar [-cxtzjvfpPN] 文件与目录 ....

```Java
-c ：新建打包文件  
-t ：查看打包文件的内容含有哪些文件名  
-x ：解打包或解压缩的功能，可以搭配-C（大写）指定解压的目录，注意-c,-t,-x不能同时出现在同一条命令中  

-j ：通过bzip2的支持进行压缩/解压缩  
-z ：通过gzip的支持进行压缩/解压缩  
-v ：在压缩/解压缩过程中，将正在处理的文件名显示出来  
-f filename ：filename为要处理的文件 
     （在 f 之后要立即接档名喔！不要再加参数！例如使用『 tar -zcvfP tfile sfile』就是错误的写法，要写成
　　　『 tar -zcvPf tfile sfile』才对喔）


-C dir ：指定压缩/解压缩的目录dir 

-p ：使用原文件的原来属性（属性不会依据使用者而变）
-P ：可以使用绝对路径来压缩！
-N ：比后面接的日期(yyyy/mm/dd)还要新的才会被打包进新建的文件中！
--exclude FILE：在压缩的过程中，不要将 FILE 打包！
```
通常我们只需要记住下面三条命令即可：<br>
压缩：tar -jcv filename.tar.bz2 要被处理的文件可目录名称 <br> 
查询：tar -jtv -f filename.tar.bz2  <br>
解压：tar -jxv -f filename.tar.bz2 -C 欲解压缩的目录 <br>
注：文件名并不定要以后缀tar.bz2结尾，这里主要是为了说明使用的压缩程序为bzip2
