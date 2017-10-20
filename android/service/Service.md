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
