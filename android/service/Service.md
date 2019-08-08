# Service

[Service示例](https://github.com/AndBird/Demo/blob/master/%E5%AE%89%E5%8D%93Service.zip)

* 判断服务是否在运行
```Java
//className,服务类名
public static boolean isServiceRunning(Context context, String className){
		try {
			ActivityManager myManager=((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE));
			ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
			for(int i = 0 ; i<runningService.size();i++){
				if(runningService.get(i).service.getClassName().toString().equals(className)){
					Log.e(TAG, className + "服务已经在运行");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
```

* bindService
```java
public class LocalService extends Service{
	private final static String TAG = LocalService.class.getSimpleName();

	private MsgBinder msgBinder;
	
	private String msg = "当前通讯次数:";
	private int count = 0;
	

	// 继承Stub，也就是实现了ICat接口，并实现了IBinder接口
	public class MsgBinder extends Binder{
		public String getMessage(){
			// TODO Auto-generated method stub
			count++;
			String string = msg + count;
			Log.e(TAG, "local service msg=" + string);
			return string;
		}
		
		public LocalService getLocalService(){
			return LocalService.this;
		}
		
	}	
	
	@Override
	public void onCreate(){
		super.onCreate();
		Log.e(TAG, "onCreate");
		msgBinder = new MsgBinder();
	}
	
	@Override
	public IBinder onBind(Intent arg0){
		Log.e(TAG, "onBind");
		/* 返回msgBinder对象
		 * 在绑定本地Service的情况下，该msgBinder对象会直接
		 * 传给客户端的ServiceConnection对象
		 * 的onServiceConnected方法的第二个参数；
		 * 在绑定远程Service的情况下，只将msgBinder对象的代理
		 * 传给客户端的ServiceConnection对象
		 * 的onServiceConnected方法的第二个参数；
		 */
		return msgBinder; //
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy(){
		Log.e(TAG, "onDestroy");
	}
}

bindService:onCreate->onBind
unbindService:onUnbind->onDestory

```

* Android 5.0以上的隐式启动问题
```
原因：
 Android 5.0之后google出于安全的角度禁止了隐式声明Intent来启动Service。如果使用隐式启动Service，会出没有指明Intent的错误。

主要原因我们可以从源码中找到，这里看看Android 4.4的ContextImpl源码中的validateServiceIntent(Intent service),可知如果启动service的intent的component和package都为空并且版本大于KITKAT的时候只是报出一个警报,告诉开发者隐式声明intent去启动Service是不安全的. 

而在android5.0之后呢？我们这里看的是android6.0的源码,从源码可以看出如果启动service的intent的component和package都为空并且版本大于LOLLIPOP(5.0)的时候,直接抛出异常，该异常与之前隐式启动所报的异常时一致的。

解决方式:
1.设置Action和packageName
final Intent serviceIntent=new Intent(); serviceIntent.setAction("隐式action");
//设置应用的包名
serviceIntent.setPackage(getPackageName());
startService(serviceIntent);

2.将隐式启动转换为显示启动
public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
    // Retrieve all services that can match the given intent
     PackageManager pm = context.getPackageManager();
     List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
     // Make sure only one match was found
     if (resolveInfo == null || resolveInfo.size() != 1) {
         return null;
     }
     // Get component info and create ComponentName
     ResolveInfo serviceInfo = resolveInfo.get(0);
     String packageName = serviceInfo.serviceInfo.packageName;
     String className = serviceInfo.serviceInfo.name;
     ComponentName component = new ComponentName(packageName, className);
     // Create a new intent. Use the old one for extras and such reuse
     Intent explicitIntent = new Intent(implicitIntent);
     // Set the component to be explicit
     explicitIntent.setComponent(component);
     return explicitIntent;
    }
    

//调用如下
Intent mIntent=new Intent();
mIntent.setAction("隐式action");
final Intent serviceIntent=new Intent(getExplicitIntent(this,mIntent));
startService(serviceIntent);


```






