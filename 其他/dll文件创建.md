����dll�ļ�
��һ:
1. ��"�ļ�"�˵��У�ѡ��"�½�"��Ȼ��ѡ��"��Ŀ��"����"��Ŀ����"�����У�ѡ��"Visual C++"�µ�"Win32����̨����" => "��һ��"��ѡ���ѡ��"DLL"��"����Ŀ"��
2. ������õĳ����ͷ�ļ�(.h)��Դ�ļ�(.c��.cpp)�ļ�������������
3. ��stdafx.h���½�.h�ļ��м���
extern "C" int __declspec(dllexport)add(int x, int y);  //addΪ�����ó������
4. �������ɣ���debug�ļ����»�����.dll�ļ�

����:��vs2010
1. ѡ���Ѿ�����õ���Ŀ���Ҽ� =>���� => �������� => ���� => �������� => ��̬���ӿ� �� ��stdafx.hͷ�ļ�������(����һ3)
2. ��������dll�ļ�

��������.def�ļ�
��.def�ļ�������̬���ӿ�DllDemo.dll
1. ɾ��DllDemo�����е�DllDemo.h�ļ���
2. ��DllDemo.cpp�ļ�ͷ��ɾ�� #include DllDemo.h��䡣
3. ��ù����м���һ���ı��ļ�������ΪDllDemo.def��д��������䣺
LIBRARY ��MyDll��             //MyDll�ļ���
EXPORTS Max@1        //Max�Ǻ�����


ʹ��dll�ļ���
���������ɵ�.dll�ļ�������C����(����ù���)�µ�debug�ļ��£�
����#include <Windows.h>

int _tmain(int argc, _TCHAR* argv[])
{ 
	HINSTANCE hDll; //DLL��?������2 
	typedef int(*PSUM)(int a,int b);  
    PSUM pSum;  
  
    
  
	  hDll = LoadLibrary(L"testdll.dll");  
	  if (hDll != NULL)
	  {
		  pSum = (PSUM)GetProcAddress(hDll,"sum"); 
		  int result;
		  result = pSum(2, 3);
		  printf("%d",result);
	 }
	 FreeLibrary(hDll);
}

����
int _tmain(int argc, _TCHAR* argv[])
{  // �ӡ�?��?DLL
	//HINSTANCE hInstance = LoadLibrary(L"makeserverdll.dll");
		HINSTANCE hInstance = LoadLibrary(L"CenterServer.dll");
	// ��?�����?��?ʾo?ѧ?����|��2��?Ϣ?���Ĩ�?����?����?��
	typedef void (*StartTransferServer)();
	StartTransferServer startTransferServer = (StartTransferServer)GetProcAddress(hInstance, "startTransferServer");
	startTransferServer();
}

