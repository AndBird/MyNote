* du
```Java
作用: 查看文件大小

语法: du -h filename

文件夹大小
max-depth参数表示指定深入目录的层数,很重要，不指定的话，会显示所有层次目录
du -h --max-depth=1 /usr
//查看当前文件夹大小
du -sh
//查看文件夹内的文件的大小
du -hl --max-depth=1

```
* df
```Java
查看当前文件系统各分区的大小
df -h
```
