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
