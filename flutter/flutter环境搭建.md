# Flutter���������

�̳�
https://flutter-io.cn/docs/get-started/install/windows

* ϵͳ����Ҫ��(�̳��еĲ��裬������=>���谲װ�����������git����flutter sdk��������app������������Ҫ)
Ҫ�밲װ������ Flutter����Ŀ�����������Ӧ���������µ�����
����ϵͳ��Windows 7 SP1 ����ߵİ汾��64 λ����ϵͳ����
���̿ռ䣺����װ IDE ��һЩ����֮�⻹Ӧ������ 400 MB �Ŀռ䡣
���ߣ�Ҫ�� Flutter ����Ŀ�������������ʹ�ã����������µĹ��ߣ�
Windows PowerShell 5.0 ���߸��ߵİ汾��Windows 10 ���Ѿ�Ԥװ�ˣ�
(��ַhttps://docs.microsoft.com/zh-cn/powershell/scripting/install/installing-windows-powershell?view=powershell-6)
Git for Windows 2.x�����ҹ�ѡ�� Windows ������ʾ��ʹ�� Git ѡ�
��� Windows ��� Git �Ѿ���װ���ˣ���ô��ȷ���ܴ�������ʾ������ PowerShell ��ֱ��ִ�� git ���


* ����Flutter SDK

1.�������°汾 stable �� Flutter SDK
https://flutter.dev/docs/development/tools/sdk/releases

2.��ѹ������ѹ��Ȼ������е� flutter Ŀ¼����������Ԥ��� Flutter SDK ��װĿ¼�У����� C:\src\flutter�����𽫸�Ŀ¼����һЩ��Ҫ�������Ȩ�޵�Ŀ¼������ C:\Program Files\����

3.�ҵ� flutter Ŀ¼�е� flutter_console.bat �ļ���˫��ִ�и�������ű�

4.���� path ��������
�������Ҫ����ͨ�� Windows ����̨��ʹ�� Flutter ����Ǿ���Ҫ��������Ĳ������� Flutter �Ķ������ļ�·�����뵽 PATH ����������
(1)�ڿ�ʼ�˵����������ܼ��롰env����Ȼ��ѡ�� �༭��ǰ�û��Ļ�������
(2)���뵽path��(:\src\flutter\bin)
�� �û����� һ���У�����Ƿ��� Path �����Ŀ��
������ڣ�ֱ�Ӱ� flutter\bin Ŀ¼������·���� ; ��Ϊ�ָ��ӵ����е�ֵ���档
��������ڵĻ������û����������д���һ���µ� Path ������Ȼ�� flutter\bin ���ڵ�����·����Ϊ�±�����ֵ��
ע�⣬����Ҫ�رպ����������Ѿ��򿪵ĸ�������̨���ڣ������´���������̨ʱ���ܷ��ʵ��ղ��޸ĵı�����

5������ flutter doctor
������������flutter doctor�������������������л�����Ȼ��Ѽ�����Ա�����ʽ���ֳ�������ϸ�Ķ�����ʾ�����ݣ�����Ƿ�����δ��װ����������������Ĳ�����Ҫ��ɣ�ͨ�����Դ�����֣��������������������л�����Ȼ��Ѽ�����Ա�����ʽ���ֳ�������ϸ�Ķ�����ʾ�����ݣ�����Ƿ�����δ��װ����������������Ĳ�����Ҫ��ɣ�ͨ�����Դ�����֣���
(1)���⣺License for package Android SDK Platform 27 not accepted.
�����flutter doctor --android-licenses

* IDE
1.��׿Android Studio 3.0 ��֮��İ汾
2. ��װFlutter �� Dart ���
�� Android Studio->File>Settings>Preferences->Plugins
��ѡ�� Browse repositories��Ȼ��ѡ�� Flutter �������� ��װ��
��������װ Dart �����ʾʱ����� Yes������������������ʾʱ����� Restart��

* ����app
1.�� IDE��ѡ���� Flutter ��Ŀ (Start a new Flutter project).
2.ѡ�� Flutter Ӧ�ó��� ��Ϊ��Ŀ���ͣ�Ȼ��� ��һ��, ȷ�� Flutter SDK ·�� ������ʾ·������ȷ�� SDK ·����
3.������Ŀ����(���� ��myapp��), Ȼ������һ������� ��ɡ�
