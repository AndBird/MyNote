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
			Log.e(TAG, "�յ�Ӧ�ñ仯�㲥: action=" + intent.getAction());
			//���հ�װ�㲥 android.intent.action.PACKAGE_ADDED
	        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {   
	            String data = intent.getDataString(); 
	            String packageName = data.replaceFirst("package:", "");
	            Log.e(TAG, "��װ��:" + packageName + "�����ĳ���");
	            //Toast.makeText(context, "��װ���", Toast.LENGTH_LONG).show();
	           
	        }   
	        
	        
	        //����ж�ع㲥  "android.intent.action.PACKAGE_REMOVED"
	        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){   
	            String data = intent.getDataString();   
	            String packageName = data.replaceFirst("package:", "");
	            Log.e(TAG, "ж����:"  + packageName + "�����ĳ���");
	           /* if(Tool.apkIsInstalled(context, packageName)){
	            	 //�������ǰ�װ
	            	 DebugUtils.printInfo(TAG, "�յ�Ӧ�ñ仯"  + packageName + "�Ѱ�װ��ִ�еĸ��ǰ�װ");
	            }else{
	            	 DebugUtils.printInfo(TAG, "�յ�Ӧ�ñ仯"  + packageName + "δ��װ��ִ�е�ж��");
	            }*/
	        }
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//ע�ᰲװж�ع㲥
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