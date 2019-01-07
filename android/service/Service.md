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
[Service示例](https://github.com/AndBird/Demo/blob/master/%E5%AE%89%E5%8D%93Service.zip)

