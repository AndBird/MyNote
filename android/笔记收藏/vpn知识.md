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
        String s = "start mtpd,%s,pptp,%s,1723,name,%s,password,%s,linkname,vpn,refuse-eap,nodefaultroute,usepeerdns,idle,1800,mtu,1400,mru,1400,nomppe\n";
        return String.format(s, iface, server, username, password);
    }

    /**
     *
     * l2tp参数
     * */
    private static String l2tpArags(String iface, String server, String username, String password){
       String s = "start racoon,%s,%s,udppsk,,vpn,1701#mtpd,%s,l2tp,%s,1701,,name,%s,password,%s,linkname,vpn,refuse-eap,nodefaultroute,usepeerdns,idle,1800,mtu,1400,mru,1400\n";
       return String.format(s, iface, server, iface, server, username, password);
    }

```