# SDK升级适配

## 升级6.0(sdk 23)
* 找不到 org.apache.http.legacy
```
法1：在libs中导入org.apache.http.legacy.jar (..sdk/platforms\android-23\optional/目录下)
法2：
android{
      .....
      useLibrary 'org.apache.http.legacy'

}

```

* 动态权限申请
```
使用PermissionsDispatcher插件

https://github.com/permissions-dispatcher/PermissionsDispatcher
```



## 升级7.0(sdk 24)
* 应用间共享文件(如安装应用)  
Android7.0引入私有目录被限制访问和StrictMode API 。私有目录被限制访问是指在Android7.0中为了提高应用的安全性，在7.0上应用私有
目录将被限制访问，StrictMode API是指禁止向你的应用外公开 file:// URI。 如果一项包含文件 file:// URI类型 的 Intent 离开你的
应用，则会报出异常，android.os.FileUriExposedException file exposed beyond app through Intent.getData()。

  * 安装应用
安装应用(应用间共享文件)需要FileProvider  
```
//第一步:在AndroidManifest.xml中:
<!--文件共享-->
<provider
  android:name="android.support.v4.content.FileProvider"
  android:authorities="${applicationId}.fileProvider"
  android:grantUriPermissions="true"
  android:exported="false">
<meta-data
	android:name="android.support.FILE_PROVIDER_PATHS"
	android:resource="@xml/file_paths"/>
</provider>

//第二步:在res/xml中定义file_paths
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <!--下载目录-->
   <!-- <external-path path="Android/data/应用包名/download/" name="download_save"/>-->
    <!--更新目录-->
   <!-- <external-path path="子目录/" name="update"/>-->
    <!--整个外部存储-->
    <external-path path="." name="external"/>


    <!--原理介绍:
    在paths节点内部支持以下几个子节点，如下：-->

        <!-- <cache-path/>代表context.getCacheDir()-->
        <!--<cache-path name="test" path="test"/>-->

        <!-- <files-path>代表代表context.getFilesDir()-->
        <!--<files-path name="test" path="test"/>-->

        <!-- <external-files-path>代表context.getExternalFilesDirs()-->
        <!--<external-files-path name="test" path="test"/>-->

        <!-- <external-cache-path>代表getExternalCacheDirs()-->
        <!--<external-cache-path name="test" path="test"/>-->

        <!-- <root-path/>代表设备的根目录new File("/")-->
        <!--<root-path name="test" path="test"/>-->

        <!--  <external-path/> 代表Environment.getExternalStorageDirectory()-->
        <!--<external-path name="test" path="test"/>-->


   <!--
   每个节点都支持两个属性：
    name
    path
    path即为代表目录下的子目录，比如：

    <external-path
        name="external"
        path="pics" />

    代表的目录即为：Environment.getExternalStorageDirectory()/pics，其他同理。
    当这么声明以后，代码可以使用你所声明的当前文件夹以及其子文件夹。
    -->
</paths>




//第三步:调起安装界面
private boolean install(final String filepath){
	Intent intent = new Intent(Intent.ACTION_VIEW);
	File file = new File(filepath);
	//Log.e(TAG, "安装文件：" + filepath);
	if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			//*.fileProvider是在AndroidManifest中的FileProvider的android:authorities值
			Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
			//添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			//Log.e(TAG, "contentUri=" + contentUri.toString());
			intent.setDataAndType(contentUri,"application/vnd.android.package-archive");
		}else{
			intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
			//intent.setDataAndType(Uri.parse("file://" + filepath), "application/vnd.android.package-archive");
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		return true;
	}else{
		//Toast.makeText(context, "安装文件不存在", Toast.LENGTH_SHORT).show();
		return false;
	}
}


FileProvider.getUriForFile(this, "com.zhy.android7.fileprovider", file)得到的是Uri的格式:
content://authorities/定义的name属性/file文件的相对路径，即name隐藏了可存储的文件夹路径

```

## 升级8.0(sdk 26)
* 安装应用的权限(targetsdkversion大于25必须声明REQUEST_INSTALL_PACKAGES权限)  
官方解释：https://developer.android.com/reference/android/Manifest.permission.html#REQUEST_INSTALL_PACKAGES  
Android8.0的变化是，未知应用安装权限的开关被除掉，取而代之的是未知来源应用的管理列表，需要在里面打开每个应用的未知来源的安装权限。Google这么做是为了防止一开始正经的应用后来开始通过升级来做一些不合法的事情，侵犯用户权益。  
当你的应用直接适配到Android8之后，内部启动应用安装是会被阻塞的，如果不处理好这个未知来源的权限，会导致应用根本无法更新，只能去应用市场重新下载。
```
//第一步:在AndroidManifest.xml中:
  <!--安装应用的权限-->
  <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
  
//第二步:
/**
 * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
 */
private void checkIsAndroidO() {
	if (Build.VERSION.SDK_INT >= 26) {
		boolean b = getPackageManager().canRequestPackageInstalls();
		if (b) {
			//去安装
			install();
		} else {
			//请求安装未知应用来源的权限
			final AlertDialog dialog = new AlertDialog(mContext).builder()
					.setContent("安装应用需要打开未知来源权限，请去设置中开启权限？");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.setOnConfirmClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 10010);
				}
			});

			dialog.show();
		}
	} else {
		//去安装
	}

}

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	switch (requestCode) {
		case 10010:
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				//去安装
			} else {
				Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
				startActivityForResult(intent, 10012);
			}
			break;
	}
}
  
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
		case 10012:
			checkIsAndroidO();
			break;

		default:
			break;
	}
}  

```

** 遇到“Only fullscreen opaque activities can request orientation”报错处理
```
原因:是我们给Activity同时设置了 android:screenOrientation="" 和 <item name="android:windowIsTranslucent">true</item>
方法1：删除AndroidManifest中相应Activity的 android:screenOrientation=""属性；或者删除相应Activity的theme中<item name="android:windowIsTranslucent">true</item>属性
方法2：
(1) 默认style中定义CustomAppBaseTheme，其它Theme继承CustomAppBaseTheme，可以根据需求设置透明
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="CustomAppBaseTheme" parent="android:Theme.Light">
        <!--
            Theme customizations available in newer API levels can go in
            res/values-vXX/base_styles_custom.xml, while customizations related to
            backward-compatibility can go here.
        -->
    </style>
	
	<!--  非全屏无标题  -->
    <style name="BaseNoTitleTheme" parent="CustomAppBaseTheme">
        <item name="android:windowNoTitle">true</item>
    </style>

     <!--  全屏 -->
    <style name="BaseFullScreenTheme" parent="CustomAppBaseTheme">
        <item name="android:windowFullscreen">true</item>
    </style>

    <!-- 透明 -->
    <style name="BaseTranslucentTheme" parent="CustomAppBaseTheme">
        <item name="android:windowIsTranslucent">true</item>
    </style>
</resources>
(2)定义values-v26目录下，新建styles.xml移除透明属性做一个适配
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--安卓8.0固定activity方向时需禁止透明否则异常-->
    <style name="CustomAppBaseTheme" parent="android:Theme.Light">
        <item name="android:windowIsTranslucent">false</item>
    </style>
</resources>
(3)义values-v27目录下，新建styles.xml恢复透明属性做一个适配
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <!--
       Base application theme, dependent on API level. This theme is replaced
       by AppBaseTheme from res/values-vXX/base_styles_custom.xml on newer devices.
   -->
    <!--安卓8.0以上允许透明(安卓8.1修复了8.0透明方向的问题)-->
    <style name="CustomAppBaseTheme" parent="android:Theme.Light">
        <item name="android:windowIsTranslucent">true</item>
    </style>
</resources>

```

* java.lang.SecurityException: Failed to find provider ** for user 0; expected to find a valid ContentProvider for this authority
```
原因:target到android8.0之后对ContentResolver.notifyChange() 以及 registerContentObserver(Uri, boolean, ContentObserver)做了限制，官方解释在这里
https://developer.android.com/about/versions/oreo/android-8.0-changes#ccn
方法:简单来说解决的办法就是创建一个contentprovider，并在AndroidManifest里面注册的provider的authority声明为registerContentObserver中uri的authority就可以了。
<provider
        android:name=".download.DownloadUriProvider"
        android:authorities="${applicationId}"
        android:enabled="true"
        android:exported="false"/>

public class DownloadUriProvider extends ContentProvider {
    public DownloadUriProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}


```


*  notification没有显示
```
原因:如果targetsdkversion设定为26或以上，开始要求notification必须知道channel，具体查阅这里。
方法：
在notify之前先创建notificationChannel
private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CharSequence name = "下载提醒";
        String description = "显示下载过程及进度";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(DOWNLOAD_CHANNEL_ID, name, importance);
        channel.setDescription(description);
        mNotificationManager.createNotificationChannel(channel);
    }
}


```

* 在AndroidManifest中注册的receiver不能收到广播
```
原因:针对targetsdkversion为26的应用，加强对匿名receiver的控制，以至于在manifest中注册的隐式receiver都失效。具体见官方原文
方法:将广播从在AndroidManifest中注册移到在Activity中使用registerReceiver注册

```

* 启动服务问题： by java.lang.IllegalStateException Not allowed to start service Intent { cmp=com.x.x.x/.x.x.xService }: app is in background uid UidRecord{}   

```Java
方法1:
第一步：
// 启动服务的地方
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
	context.startForegroundService(intent);
} else {
	context.startService(intent);
}
有个简写：ContextCompat.startForegroundService(context, intent)

第二步：
// service 类内部  oncreate
@Override
public void onCreate() {
    super.onCreate();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		//这个id不要和应用内的其他同志id一样，不行就写 int.maxValue()     
       startForeground(1,new Notification()); 
	   //context.startForeground(SERVICE_ID, builder.getNotification());
    }
}

//需要channel
NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
if (manager == null) {
	return;
}
NotificationChannel channel = new NotificationChannel("111", getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
manager.createNotificationChannel(channel);

Notification notification = new NotificationCompat.Builder(this, "111")
		.setAutoCancel(true)
		.setCategory(Notification.CATEGORY_SERVICE)
		//设置可清除false
		.setOngoing(true)
		.setPriority(NotificationManager.IMPORTANCE_LOW)
		.build();


startForeground(1001, notification);

方法2：推荐使用JobScheduler
```


## 升级9.0(sdk 28)
* java.lang.NoClassDefFoundError: Failed resolution of: Lorg/apache/http/ProtocolVersion; Caused by: java.lang.ClassNotFoundException: 
Didn't find class "org.apache.http.ProtocolVersion"
```
//Android P Developer Preview的bug
在AndroidManifest.xml文件中<Application>标签里面加入:
<uses-library android:name="org.apache.http.legacy" android:required="false"/>
```
* Http请求:java.io.IOException: Cleartext HTTP traffic to dict.youdao.com not permitted
```
原因:从Android 6.0开始引入了对Https的推荐支持，与以往不同，Android P的系统上面默认所有Http的请求都被阻止
了,<application android:usesCleartextTraffic=["true" | "false"]>,原本这个属性的默认值从true改变为false。

为保证用户数据和设备的安全，Google针对下一代 Android 系统(Android P) 的应用程序，将要求默认使用加密连接，这意
味着 Android P 将禁止 App 使用所有未加密的连接，因此运行 Android P 系统的安卓设备无论是接收或者发送流量，未来
都不能明码传输，需要使用下一代(Transport Layer Security)传输层安全协议，而 Android Nougat 和 Oreo 则不受影响

方法:解决的办法简单来说可以通过在AnroidManifest.xml中的application显示设置
<application android:usesCleartextTraffic="true">
更为根本的解决办法是修改应用程序中Http的请求为Https，当然这也需要服务端的支持。


```

* WebView多进程：Caused by: java.lang.RuntimeException: Using WebView from more than one process at once with the same data directory is not supported. https://crbug.com/558377
```
这行代码翻译过来的意思就是：不支持同时使用多个进程中具有相同数据目录的WebView.
Android P 以及之后版本不支持同时从多个进程使用具有相同数据目录的WebView.
针对这个问题，谷歌也给出了解决方案，代码很简单：在初始化的时候，需要为其它进程webView设置目录.

//Android P 以及之后版本不支持同时从多个进程使用具有相同数据目录的WebView
//为其它进程webView设置目录

@RequiresApi(api = 28)
public void webviewSetPath(Context context) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
		String processName = getProcessName(context);
		if (!getPackageName().equals(processName)) {//判断不等于默认进程名称
			WebView.setDataDirectorySuffix(processName);
		}
	}
}

public String getProcessName(Context context) {
	if (context == null) return null;
	ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
		if (processInfo.pid == android.os.Process.myPid()) {
			return processInfo.processName;
		}
	}
	return null;
}

注意：webviewSetPath()的调用一定是在进程初始化的时候调用，比如Application中进行调用，并且这行代码需要在其他的SDK等等初始化之前就要调用，否则会报其他的错误。

```

## 升级10.0(sdk 29  Q)
* 分区存储
```
当满足以下每个条件时，将开启兼容模式，即不开启Q设备中的存储权限改动：
应用targetSDK<=P。
应用安装在从 Android P 升级到 Android Q 的设备上。
但是当应用重新安装(更新)时，不会重新开启兼容模式，存储权限改动将生效.

最简单粗暴的方法就是在AndroidManifest.xml中添加 android:requestLegacyExternalStorage="true"来请求使用旧的存储模式

 <application
	android:name="com.lgh5.web.MyApplication"
	.......
	android:requestLegacyExternalStorage="true">

```


