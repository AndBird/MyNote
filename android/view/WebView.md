# WebView

### 1.WebSettings
```Java
setting.setJavaScriptEnabled(true);
setting.setBuiltInZoomControls(false);//支持缩放
setting.setUseWideViewPort(true); //将图片调整到适合webview的大小 
setting.setJavaScriptCanOpenWindowsAutomatically(true);//支持js打开新的窗口
//setting.setLoadsImagesAutomatically(true);//支持自动加载图片
//setting.setPluginState(WebSettings.PluginState.ON);//支持插件
setting.setDomStorageEnabled(true);
//setting.setDatabaseEnabled(true);
setting.setSupportMultipleWindows(true);//支持多窗口

//if(Build.VERSION.SDK_INT >= 19) {//加快HTML网页加载完成速度 
//   webView.getSettings().setLoadsImagesAutomatically(true);
//} else {
// 	webView.getSettings().setLoadsImagesAutomatically(false);
//}
```

### 2.WebView调起QQ（企业QQ）临时聊天界面
```Java
1.通过浏览器调用
String url="mqqwpa://im/chat?chat_type=wpa&uin=xxxxxxx";  
startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));  

参见:https://blog.csdn.net/growing_tree/article/details/48157873
   

2.webView调用
WebSettings settings=mWebView.getSettings();  
settings.setJavaScriptEnabled(true);  
mWebView.setWebViewClient(new WebViewClient(){  
    //重写该方法就是为了在使用WEBVIEW请求网页时，先拦截请求的数据，可以在请求网络数据前做一些处理  
    @Override  
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {  
        //这里处理为了就是让一般的http和https协议开头的走正常的页面，而其他的URL则会开启一个Acitity然后去调用原生APP应用  
        try {
             if (url.startsWith("http")||url.startsWith("https")){  
                 return super.shouldInterceptRequest(view,url);  
             }else{  
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
                 startActivity(intent);  
                 return super.shouldInterceptRequest(view,url);  
             }  
        } catch (Exception e) {
          //不捕获异常，部分手机直接闪退
          e.printStackTrace();
				}
        return super.shouldInterceptRequest(view,url);
    }  
});  

参见: https://blog.csdn.net/u013064109/article/details/52496502
```

### 3.WebView加载本地html
```
//加载 assets中的html
webView.loadUrl("file:///android_asset/index.html");
//加载 sdcard中的html
webView.loadUrl("file://" + Environment.getExternalStorageDirectory() + File.separator + "index.html");
//加载String html
String web = "<!DOCTYPE html>\n" +
	"<html>\n" +
	"<meta charset=\"utf-8\"></meta>\n" +
	"\n" +
	"<form name=\"testForm1\" action=\"http://www.baidu.com\">\n" +
	"    <input type=\"submit\" value=\"go\">\n" +
	"</form>\n" +
	"</body>\n" +
	"</html>";
webView.loadData(web, "text/html", "utf-8");
```

### 4.WebView Java和JavaScript交互详解
&emsp;&emsp;Hybrid(混合开发)方式的app集合了Native App和Web App的优点，既保证了用户体验，又使得App在一定程度上具备动态更新的能力，同时有利于实现跨平台开发，减少人力成本。Hybrid实现的关键点在于如何打通Java和Javascript之间的通信，主要包括2点:Java如何调用Javascript和Javascript如何调用Java。  
&emsp;&emsp;在Android开发中我们是使用WebView组件来加载HTML5页面的，WebView默认提供了让Java和Html5页面中JavaScript脚本交互的能力。
* 1.Java调用script  
使用webView.loadUrl("javascript:toast()"),其中toast(),方法是HTML5页面中的JavaScript函数。
```Java
1.需要设置支持JavaScript
WebSettings webSettings = webView.getSettings();
// 设置与Js交互的权限
webSettings.setJavaScriptEnabled(true);
// 设置允许JS弹窗
webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


/*webview只是载体，内容的渲染需要使用webviewChromClient类去实现
, 通过设置WebChromeClient对象处理JavaScript的对话框,
设置响应js 的Alert()函数*/
webView.setWebChromeClient(new WebChromeClient() {
	@Override
	public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
		AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
		b.setTitle("Alert");
		b.setMessage(message);
		b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				result.confirm();
			}
		});
		b.setCancelable(false);
		b.create().show();
		return true;
	}

});

2.调用(webView得先调用html,且JS代码调用一定要在 onPageFinished（） 回调之后调用才有效)
webView.loadUrl("javascript:toast()");


3.html
<!DOCTYPE html>
<html>
<meta charset="utf-8"></meta>
<body>
<form name="testForm1" action="http://www.baidu.com">
    <input type="submit" value="百度">
</form>


<script language="javascript" type="text/javascript">
	//alert("head");
	//alert("第2个个");

    <!--提供给Android的java代码调用-->
    function toast(){
	    alert("test javascript toast");
    }
	
    function toast(x){
	alert(x);
    }
</script>

</body>
</html>

```

* 2.JavaScript调用Java  
WebView调用Java方法，只需要3个步骤:
	* 调用与WebView关联的WebSettings实例的setJavaScriptEnabled方法来开启JavaScript的调用功能
	* 调用WebView的addJavascriptInterface方法将应用中的Java对象暴露给JavaScript
	* 在JavaScript脚本中调用步骤二暴露出来的Java对象方法

```Java

1.需要设置支持JavaScript
WebSettings webSettings = webView.getSettings();
// 设置与Js交互的权限
webSettings.setJavaScriptEnabled(true);

2.设置java回调
webView.addJavascriptInterface(new JavaObject(getApplicationContext()), "javaObject");

public static class JavaObject{
	private final String TAG = JavaObject.class.getSimpleName();

	private Context context;

	public JavaObject(Context context){
		this.context = context;
	}

	@JavascriptInterface
	public void printLog(String message){
		Log.e(TAG, message);
	}

	@JavascriptInterface
	public void showToast(String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}

3.html
<!DOCTYPE html>
<html>
<meta charset="utf-8"></meta>

<body>

<input type="button" style="height:32px;" id="loginsubmit" value="登录" onclick=" javaObject.showToast('javascript callback');"/>

</body>
</html>

```
&emsp;&emsp;在Android4.2之前的系统中，会引起臭名昭著的WebView远程代码执行漏洞，从Android4.2开始，Google修复了这个漏洞，唯一需要修改的是对暴露给JavaScript调用的方法增加@JavascriptInterface注解。  
&emsp;&emsp;那么Android4.2之前的系统版本中该如何规避这个安全隐患呢？答案是不要再使用addJavascriptInterface这种方式，转而寻找其他的途径。我们知道，JavaScript有三种常用的消息提示框架，分别是:弹出警告框alert、弹出确认框confirm和弹出输入框prompt.对应到Android的WebChromeClient类，分别是以下三个方法.
```Java
   @Override
	public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
		return false;
	}

	@Override
	public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
		return super.onJsConfirm(view, url, message, result);
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
		return super.onJsPrompt(view, url, message, defaultValue, result);
	}
```
可以看到这三个方法参数唯一的区别是返回给Javascript的结果类型不一样，前两者是JsResult类型，这个类中带有一个boolean的结果值;而onJsPrompt是JsPromptResult类型，它是JsResult的子类，带有一个字符串类型的结果值,部分代码如下：
```Java
public class JsResult{
	private boolean mResult;
	
	@SystemApi
	public final boolean getResult(){
		return mResult;
	}
	
}

public class JsPromptResult extends JsResult{
	private String mStringResult;
	
	@SystemApi
	public String getStringResult(){
		return mStringResult;
	}
}


```
**很显然，String类型的结果值可以带上更多的信息，因此我们选择onJsPrompt方法作为解决方案的突破口，通过这个方法，我们能够实现在JavaScript中将字符串信息(对应onJsPrompt入参中的message传递给Java)，而Java执行完成后也能够把返回结果的字符串形式(对应onJsPrompt的返回值mStringResult)传递给JavaScript。基本思路如下：**   
	
   * 由于我们是通过字符串形式在JavaScript和Java之间进行通信的，因此需要基于这个字符串定义好通信协议，可以是JSON格式，这个字符串中可能会包含调用的类型type、方法名method、方法参数args等。  
   * 在JavaScript中封装一个方法，它通过最终调用prompt方法实现将上面的文本协议信息传递给Java层WebChromeClient类的onJsPrompt方法，在这个方法中对协议信息进行解析，可以得到类型、方法名、参数等信息，通过Java的反射机制可以实现调用到对应的Java方法。  
   * 步骤二中的Java方法执行完毕后，同理，需要定义好返回值的协议格式，并通过JsPromptResult返回给JavaScript。  
&emsp;&emsp;当然，具体实现起来还是有许多其它工作需要做的，想要应用到线上项目中，更少不了各种测试。好消息是，国内开发者pedant基于上述方案已经实现了一个健壮可靠的开源函数库safe-java-js-webview-bridge(https://github.com/pedant)， 我们可以直接拿来用。

