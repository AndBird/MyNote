# clone������flutter���������޸�

```Java
˵��:clone�Ĺ�����ͨ��git�ύ�ģ�clone�����󣬵��뵽android studio�󣬹��̽ṹ�쳣(android module�޷�ʶ��java�������ʾ,�����ڱ�д����)�����潫�����޸�(���蹤��Ŀ¼��myflutter)

1.���빤�̣�ͨ��File->open����Open an existing Android Studio project���룬Ȼ��ͨ��Tools->Flutter->Flutter Pub Get�����Ҽ�pubspec.yaml->Flutter->Flutter Pub Get�޸���libĿ¼�µ�.dart�ļ��������⡣

���̵����moduleֻʣ��һ����module android������Project Structure���޷�ʶ��java��Ҳ����build.gradle�ļ�Ҳ��������Ӱ�칤���������С�

2.�޸�language:��File->Project Structure->Project Settings->Project->Project language level,ѡ��language�汾13������(̫�ͣ�java���йؼ��ֱ���),��һ��Ҳ���Ժ���Ū

3.����android module:
(1)��File->Project Structure->Project Settings->Modules,������ƣ�����module nameΪ"������_android",��myflutter_android,ѡ��Module file location Ϊ����Ŀ¼�µ�androidĿ¼��
(2)�л���myflutter_android->Dependencies,����<No SDK>��<Module source>,����SDK�汾��ɾ��������Dart SDK, Dart Packages,Flutter Plugins(��Ҫ���Problems���޸�����)��
(3)���(2)�󣬵��+->Library->New Library->Java(������������ֱ�ӻس�������޷�ѡ��)->Ȼ��ѡ���ļ�flutter.jar(ע�⣺flutter sdkĿ¼/bin\cache\artifacts\engine\android-arm\flutter.jar),Ȼ������Library name(��flutter for Android)->���ȷ��->Added Selected��Ȼ��һ·���档
(4)չ��android module,�Ҽ��ļ���java->Mark Directory as->Sources Root

����ǰ��4������Ŀ�е�android���ֻ�����������ʾ�ˡ�




������
��File->Project Structure->Project Settings->Modules->myflutter_android,�����myflutter_android���Ҽ�Add Android,��������Ŀ¼
...\myflutter\android\app\src\main\AndroidManifest.xml
...\myflutter\android\app\src\main\res
...\myflutter\android\app\src\main\assets
...\myflutter\android\app\src\main\libs

���������ᵼ��File�˵������Project Structure����ʾ��


```