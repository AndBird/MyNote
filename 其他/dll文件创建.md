生成dll文件
法一:
1. 从"文件"菜单中，选择"新建"，然后选择"项目…"，在"项目类型"窗格中，选择"Visual C++"下的"Win32控制台程序" => "下一步"的选项卡中选中"DLL"和"空项目"。
2. 将编译好的程序的头文件(.h)和源文件(.c或.cpp)文件拷贝到工程中
3. 在stdafx.h或新建.h文件中加入
extern "C" int __declspec(dllexport)add(int x, int y);  //add为被调用程序入口
4. 编译生成，在debug文件夹下会生成.dll文件

法二:在vs2010
1. 选中已经编译好的项目，右键 =>属性 => 配置属性 => 常规 => 配置类型 => 动态链接库 ， 在stdafx.h头文件中配置(见法一3)
2. 编译生成dll文件

法三：用.def文件
用.def文件创建动态连接库DllDemo.dll
1. 删除DllDemo工程中的DllDemo.h文件。
2. 在DllDemo.cpp文件头，删除 #include DllDemo.h语句。
3. 向该工程中加入一个文本文件，命名为DllDemo.def并写入如下语句：
LIBRARY “MyDll”             //MyDll文件名
EXPORTS Max@1        //Max是函数名


使用dll文件：
将编译生成的.dll文件拷贝到C工程(需调用工程)下的debug文件下；
包含#include <Windows.h>
```C++
int _tmain(int argc, _TCHAR* argv[])
{ 
	HINSTANCE hDll; //DLL句柄 
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

或者
int _tmain(int argc, _TCHAR* argv[])
{  	// 加载DLL
	//HINSTANCE hInstance = LoadLibrary(L"makeserverdll.dll");

	typedef void (*StartTransferServer)();
	StartTransferServer startTransferServer = (StartTransferServer)GetProcAddress(hInstance, "startTransferServer");
	startTransferServer();
}
```


