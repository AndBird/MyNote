#

* ����Java����


* ��class�ļ�ת����dex������
```Java
1��dx���ߣ�
��.classתΪdex�Ĺ���dx����Ŀ¼

a.�ϰ汾��sdk�����SDK\platform-tools��
b.�µ�sdk�����SDK\build-toolsx\xx.xx(�汾��)��

ע�⣺dx��û�����õ�����������,��Ҫ����ӦĿ¼������������.

2.javac����ʹ��IDE����һ��,HelloWorld.java������.class�ļ�

3.cmd����dx����Ŀ¼:

�Ƚ�class��Դ����jar���Ƶ�dx����Ŀ¼

dx --dex --output=C:\Users\Administrator\Desktop\test.dex .\com\text\HelloWorld.class

ָ��˵��:dx --dex --output=(���·��) (.class�ļ�·��)

ע�⣺������.class��·��ʱ,ֱ�Ӵ��������ϵ�cmd�������˾���·��,dx��.class�ļ���֧�־���·��,ֻ�ܷŵ�dxĿ¼��,ʹ�����·��.


ע��:�����Ҫ������class�ļ���dex�У������Ƚ����classת����test.jar,Ȼ��test.jarת����dex
dx --dex --output=C:\Users\Administrator\Desktop\test.dex .\test.jar


4.��test.dex�Ƶ�sd����
adb push C:\Users\Administrator\Desktop\test.dex /sdcard

5.����

(1)��push ��/data/local/tmp�У�����ָ�
#cd /data/local/tmp
#app_process -Djava.class.path=test.dex  /data/local/tmp com.text.HelloWorld

����
#app_process -Djava.class.path=/data/local/tmp/test.dex  /data/local/tmp com.text.HelloWorld

(2)��push��sd���У�����ָ��
#app_process -Djava.class.path=/sdcard/test.dex  /sdcard com.text.HelloWorld

```