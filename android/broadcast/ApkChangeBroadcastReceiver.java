package com.example.test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ApkChangeBroadcastReceiver extends BroadcastReceiver{
	private static final String TAG = ApkChangeBroadcastReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent){
		try {
			Log.e(TAG, "收到应用变化广播: action=" + intent.getAction());
			//接收安装广播 android.intent.action.PACKAGE_ADDED
	        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {   
	            String data = intent.getDataString(); 
	            String packageName = data.replaceFirst("package:", "");
	            Log.e(TAG, "安装了:" + packageName + "包名的程序");
	            //Toast.makeText(context, "安装完成", Toast.LENGTH_LONG).show();
	           
	        }   
	        
	        
	        //接收卸载广播  "android.intent.action.PACKAGE_REMOVED"
	        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){   
	            String data = intent.getDataString();   
	            String packageName = data.replaceFirst("package:", "");
	            Log.e(TAG, "卸载了:"  + packageName + "包名的程序");
	           /* if(Tool.apkIsInstalled(context, packageName)){
	            	 //升级覆盖安装
	            	 DebugUtils.printInfo(TAG, "收到应用变化"  + packageName + "已安装，执行的覆盖安装");
	            }else{
	            	 DebugUtils.printInfo(TAG, "收到应用变化"  + packageName + "未安装，执行的卸载");
	            }*/
	        }
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//注册安装卸载广播
	public static void registerApkInstallReceiver(Activity activity, ApkChangeBroadcastReceiver apkChangeBroadcastReceiver){
		try {
			Log.e(TAG, "registerApkInstallReceiver");
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_PACKAGE_ADDED);
			//filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
			//filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
			//filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
			filter.addDataScheme("package");
			activity.registerReceiver(apkChangeBroadcastReceiver, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unRegisterReceiver(Activity activity, ApkChangeBroadcastReceiver apkChangeBroadcastReceiver){
		try {
			Log.e(TAG, "unRegisterReceiver");
			if(apkChangeBroadcastReceiver != null){
				activity.unregisterReceiver(apkChangeBroadcastReceiver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}