# pptp和l2tp

* 判断vpn是否在线
```Java
    /**
     *
     * 通过mtpd 进程和网络检测(双校验)
	 
	 
     * */
    private static boolean isVpnRunning1() {
        try {
			//执行shell
            Process p = RunCommand.run("ps | grep mtpd");
            p.waitFor();
            String result = RunCommand.readInput(p);

			//vpn连接时会启动mtpd进程，用命令打印出进程，如果存在mtpd进程就说明vpn已经连接
            //ShellUtils.CommandResult commandResult = ShellUtils.execCommand("ps | grep mtpd", true);
            //String result = commandResult.successMsg;
            //DebugUtils.printInfo(TAG, "command result=" + result + "," + commandResult.errorMsg);
            DebugUtils.printInfo(TAG, "command result=" + result);
            if (!TextUtils.isEmpty(result.replace("\n", "").trim()) && result.contains("mtpd")) {
                return true && isVpnRunning();
            }
        } catch (Exception e) {
			e.printStackTrace();	
        }
        return false;
    }

    /**
     * 通过网络检测
     * */
    public static boolean isVpnRunning() {
        //ArrayList<String> networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp()) {
                    //Log.e(TAG, "net name=" + networkInterface.getName());

                    if(networkInterface.getName().contains("ppp")) {
                        for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            String ipaddress = inetAddress.getHostAddress().toString();
                            //Log.e(TAG, "ip address=" + ipaddress);
                            if (!inetAddress.isLoopbackAddress()) {
                                if (!TextUtils.isEmpty(ipaddress) && !ipaddress.contains("::") && !"0.0.0.0".equals(ipaddress)) {//ipV6的地址
                                    //ip地址不为空，且不是ipv6，且不为0，才算连接成功
                                    return true;
                                }
                            }
                        }
                        //networkList.add(networkInterface.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

```


* l2tp和pptp启动指令
```Java

    /**
     * 获取连接网卡
     * */
    private static String getConnectIface(){
        // Iface
        String iface = getDefaultIface();
        //String iface = "eth0";
        DebugUtils.printInfo(TAG, "iface=" + iface);
        if(TextUtils.isEmpty(iface)) {
            iface = "eth0";
        }
        //iface = "wlan0";
        DebugUtils.printInfo(TAG, "final iface=" + iface);
		return iface;
    }
	
	   /**
     * 获取默认网卡
     * */
    private static String getDefaultIface() {
        String routes;
        try {
            Process p = RunCommand.run("ip ro");
            p.waitFor();
            routes = RunCommand.readInput(p);
        } catch (Exception e) {
            e.printStackTrace();
            routes = null;
        }

        if (routes != null) {
            for (String route : routes.split("\n")) {
                //if (route.startsWith("default")) {
                    String iface = null;
                    boolean last = false;
                    for (String ele : route.split(" ")) {
                        if (last) {
                            iface = ele;
                            break;
                        } else if (ele.equals("dev")) {
                            last = true;
                        }
                    }

                    if (iface != null) {
                        return iface;
                    } else {
                        break;
                    }
                //}
            }
        }

        // Can't load default interface? That's not possible.
        return "eth0";
    }
	

    /**
     * pptp 参数
	 * iface通过getConnectIface()获取
     * */
    private static String pptpArags(String iface, String server, String username, String password){
        String s = "start mtpd %s pptp %s 1723 name %s password %s linkname vpn refuse-eap nodefaultroute usepeerdns idle 1800 mtu 1400 mru 1400 nomppe &";
        String cmd = String.format(s, iface, server, username, password);
		
    }

    /**
     *
     * l2tp参数
     * */
    private static String[] l2tpArags(String iface, String server, String username, String password){
	   //vpn是默认IPSec预共享秘钥
       String s = "start racoon %s %s udppsk \"\" vpn 1701 &"; 
	   String cmd1 = String.format(s, iface, server);
	   String s2 = "mtpd %s l2tp %s 1701 \"\" name %s password %s linkname vpn refuse-eap nodefaultroute usepeerdns idle 1800,mtu 1400 mru  1400 &";
       String cmd2 = String.format(iface, server, username, password);
    }

```

* 3.mtpd参数
```Java
android中有mtpd命令可以连接vpn
在pc上执行adb shell进入控制台
执行 mtpd 
输出
mtpd interface 12tp <server> <port> <secret> pppd-arguments
mtpd interface pptp <server> <port> pppd-arguments

示例如下:

mtpd wlan0  pptp a.ueuz.com 1723 name d11234 password 1234 linkname vpn refuse-eap  nodefaultroute idle 1800 mtu 1400 mru 1400 nomppe unit 100\
mtpd的所有参数解释在这个链接:https://ppp.samba.org/pppd.html
参数解释:
wlan0 使用的接口
pptp  协议
a.ueuz.com  服务器地址
1723 端口号
name d11234  账号是d11234
password 1234 密码是1234
linkname vpn  连接的名称
refuse-eap  不使用eap
nodefaultroute 不使用默认路由
idle 1800 心跳间隔
mtu 1400   一个包最大发送1400字节
mru 1400   一个包最大接收1400字节
nomppe  不使用微软点对点加密
unit 100  接口名称，连接名称 如果连接成功 通过 ip ro命令可以看到类似0.0.0.0/1 dev ppp100  scope link的字符串，ppp的后缀100就是这里设置的


l2tp:可参考l2tp参数.pdf
# racoon eth0 192.168.0.1 udppsk myhomelan \
 d41d8cd98f00b204e980 1701 &
# mtpd eth0 l2tp 192.168.0.1 1701 "" linkname vpn name joe \
 password test1234 refuse-eap nodefaultroute \
 usepeerdns idle 1800 mtu 1400 mru 1400 &
 

```
