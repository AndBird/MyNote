# pptp��l2tp

* �ж�vpn�Ƿ�����
```Java
    /**
     *
     * ͨ��mtpd ���̺�������(˫У��)
	 
	 
     * */
    private static boolean isVpnRunning1() {
        try {
			//ִ��shell
            Process p = RunCommand.run("ps | grep mtpd");
            p.waitFor();
            String result = RunCommand.readInput(p);

			//vpn����ʱ������mtpd���̣��������ӡ�����̣��������mtpd���̾�˵��vpn�Ѿ�����
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
     * ͨ��������
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
                                if (!TextUtils.isEmpty(ipaddress) && !ipaddress.contains("::") && !"0.0.0.0".equals(ipaddress)) {//ipV6�ĵ�ַ
                                    //ip��ַ��Ϊ�գ��Ҳ���ipv6���Ҳ�Ϊ0���������ӳɹ�
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


* l2tp��pptp����ָ��
```Java

    /**
     * ��ȡ��������
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
     * ��ȡĬ������
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
     * pptp ����
	 * ifaceͨ��getConnectIface()��ȡ
     * */
    private static String pptpArags(String iface, String server, String username, String password){
        String s = "start mtpd %s pptp %s 1723 name %s password %s linkname vpn refuse-eap nodefaultroute usepeerdns idle 1800 mtu 1400 mru 1400 nomppe &";
        String cmd = String.format(s, iface, server, username, password);
		
    }

    /**
     *
     * l2tp����
     * */
    private static String[] l2tpArags(String iface, String server, String username, String password){
	   //vpn��Ĭ��IPSecԤ������Կ
       String s = "start racoon %s %s udppsk \"\" vpn 1701 &"; 
	   String cmd1 = String.format(s, iface, server);
	   String s2 = "mtpd %s l2tp %s 1701 \"\" name %s password %s linkname vpn refuse-eap nodefaultroute usepeerdns idle 1800,mtu 1400 mru  1400 &";
       String cmd2 = String.format(iface, server, username, password);
    }

```

* 3.mtpd����
```Java
android����mtpd�����������vpn
��pc��ִ��adb shell�������̨
ִ�� mtpd 
���
mtpd interface 12tp <server> <port> <secret> pppd-arguments
mtpd interface pptp <server> <port> pppd-arguments

ʾ������:

mtpd wlan0  pptp a.ueuz.com 1723 name d11234 password 1234 linkname vpn refuse-eap  nodefaultroute idle 1800 mtu 1400 mru 1400 nomppe unit 100\
mtpd�����в����������������:https://ppp.samba.org/pppd.html
��������:
wlan0 ʹ�õĽӿ�
pptp  Э��
a.ueuz.com  ��������ַ
1723 �˿ں�
name d11234  �˺���d11234
password 1234 ������1234
linkname vpn  ���ӵ�����
refuse-eap  ��ʹ��eap
nodefaultroute ��ʹ��Ĭ��·��
idle 1800 �������
mtu 1400   һ���������1400�ֽ�
mru 1400   һ����������1400�ֽ�
nomppe  ��ʹ��΢���Ե����
unit 100  �ӿ����ƣ��������� ������ӳɹ� ͨ�� ip ro������Կ�������0.0.0.0/1 dev ppp100  scope link���ַ�����ppp�ĺ�׺100�����������õ�


l2tp:�ɲο�l2tp����.pdf
# racoon eth0 192.168.0.1 udppsk myhomelan \
 d41d8cd98f00b204e980 1701 &
# mtpd eth0 l2tp 192.168.0.1 1701 "" linkname vpn name joe \
 password test1234 refuse-eap nodefaultroute \
 usepeerdns idle 1800 mtu 1400 mru 1400 &
 

```
