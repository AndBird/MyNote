# Android Studio创建Java运行程序

```Java
1.在android工程中新增Java Library类型的Module A:
File-->New-->New Module-->Java Library-->Next-->Finish，此步骤最重要是选择Java Library，

2.新建TestJavaMain类
public class TestJavaMain {
    public static void main(String[] args){
        System.out.println("Hello Android");
    }
}

3.配置运行

在Edit configurations窗口中新增Application配置，命名为Test,Configuration的Main class中
填写包名.TestJavaMain,然后保存，即可运行。

Configuration中的Use classpath of module选项选择ModuleA



```