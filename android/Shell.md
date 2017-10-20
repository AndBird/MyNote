```
private void execShellCmd(String[] cmd){  
		    try {  
		    	 Log.e(TAG,"执行模拟");
		        //Process process = Runtime.getRuntime().exec("su");  //获取root权限
		    	//Process process = Runtime.getRuntime().exec("sh"); 
		    	//Process process = Runtime.getRuntime().exec("adb shell ime"); //cannot bind 'tcp:5038' 
		        //Process process = Runtime.getRuntime().exec("adb shell");  //cannot bind 'tcp:5038'
		    	 
		    	 Process process = Runtime.getRuntime().exec(cmd);
//		         // 获取输出流  
//		         OutputStream outputStream = process.getOutputStream();  
//		         DataOutputStream dataOutputStream = new DataOutputStream(outputStream); 
//		         dataOutputStream.writeBytes("ime set com.yulong.android.coolpadime/.CoolpadIME\n");
//		         //dataOutputStream.writeBytes(cmd + "\n");  
//		         dataOutputStream.writeBytes("exit\n");  
//		         dataOutputStream.flush();  
//		         dataOutputStream.close();  
//		         outputStream.close();  
		         InputStream in = process.getInputStream();
		         BufferedReader br = new BufferedReader(new InputStreamReader(in));
		         String content = null;
		         while((content = br.readLine()) != null){
		        	 Log.e("result:", content);
		         }
		         br.close();
		         in.close();
		         
		         in = process.getErrorStream();
		         br = new BufferedReader(new InputStreamReader(in));
		         content = null;
		         while((content = br.readLine()) != null){
		        	 Log.e("error:", content);
		         }
		         br.close();
		         in.close();
		         
		         process.waitFor();
		         Log.e(TAG, "完成");
		    } catch (Throwable t) {  
		        t.printStackTrace();  
		    }  
	}
	 
	 //测试，指令需要带上指令文件的路径
	 execShellCmd(new String[]{"/system/bin/ime", "list", "-s"});
	 execShellCmd(new String[]{"/system/bin/ls", "-l"}); 
	 
	  //将命令执行结果保存
	  private static void execShellCmd(){  
	    try {  
	    	 Process process = Runtime.getRuntime().exec("su");
	    	 //Process process = Runtime.getRuntime().exec(cmd);
	    	 
	    	DataOutputStream output = new DataOutputStream(process.getOutputStream());
	        DataInputStream input = new DataInputStream(process.getInputStream());	    	
	    	output.writeBytes("/system/bin/ls  > /sdcard/test");//将ls命令执行的结果保存到/sdcard/test 文件中
    	    output.flush();
	    } catch (Throwable t) {  
	        t.printStackTrace();  
	    }  
 	}
```
