# String
* 特殊符号
```xml
 html空格: &nbsp
 xml空格:  \u0020  (在字符串尾部按空格键产生的space将无效，需特殊字符)
 html字符串: <string name="html_text_prefix"><Data><![CDATA[%1$s]]></Data></string>
            <string name="html_text"><Data><![CDATA[<font color="#fda300"><b>%1$s</b></font>html测试]]></Data></string> 
 xml换行:  \n
 xml下划线: <string name="test"><u>下划线</u></string>
 xml双引号: <string name="test">创建\"快捷\"方式</string>
 xml单引号: <string name="test">创建\'快捷\'方式</string>
           <string name="test">创建快捷方式&apos;</string>
 xml省略号: <string name="test">创建快捷方式\u2026</string>
           <string name="test">创建快捷方式...</string>
 占位符:    <string name="test">整数型:%1$d，浮点型：%2$.2f，字符串:%3$s</string>
          占位符说明: %后面是占位符的位置，从1开始（比如这里用到了三个占位符，从1开始往后排）
                    $ 后面是填充数据的类型
                      %d：表示整数型；
                      %f ：表示浮点型，其中f前面的.2表示小数的位数
                      %s：表示字符串

&=>&amp;
<=>&lt;
>=>&gt;
<string name="test">创建快捷方式&amp;&lt;&gt;</string>的显示结果是:创建快捷方式&<>
```

* 转义字符
```Java
转义字符特点:
a. 转义序列各字符间不能有空格； 
b. 转义序列必须以"；"结束； 
c. 单独的&不被认为是转义开始； 
d. 区分大小写。
```
