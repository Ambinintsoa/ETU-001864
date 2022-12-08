package configuration;

import java.io.PrintWriter;
import java.lang.Process;

public class Configuration {
    static String ip;
    static String adaptor_name = "WI-FI";
    static String subnet_mask = "255.255.255.0";
    static String default_gateway;
    static String dns_1 = "8.8.8.8";
    static String dns_2 = "8.8.4.4";

    public Configuration(String ip1, String subnet_mask1, String default_gateway1) {
        ip = ip1;
        subnet_mask = subnet_mask1;
        default_gateway = default_gateway1;

    }

    public void mainRun() {
        System.out.println("ataony");
        String[] command = { "cmd", };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("netsh int ip set address \"" + adaptor_name + "\" static " + ip + " " + subnet_mask + " "
                    + default_gateway);
            stdin.println("netsh int ip set dns \"" + adaptor_name + "\" static " + dns_1 + " primary");
            stdin.println("netsh interface ip add dns \"" + adaptor_name + "\" " + dns_2 + " INDEX=2");
            stdin.close();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
