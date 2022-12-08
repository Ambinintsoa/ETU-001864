package communication;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.io.*;
import org.dhcp4java.DHCPPacket;
import configuration.*;

public class DHCPClient {
    private String mac;
    private InetAddress ip;
    private int xid;
    private static int portListening = 1168;
    private static int portSender = 67;
    private static InetAddress host;

    // etablissement du packet a envoye par rapport au packet recu
    public DHCPPacket arrangement(DHCPPacket packet) throws Exception {
        DHCPPacket sentPacket = null;
        DHCPmsg msg = new DHCPmsg();
        System.out.println(packet.getDHCPMessageType());
        if (packet == null) {
            sentPacket = msg.discover(1, InetAddress.getLocalHost(), (byte) 1);
            return sentPacket;
        }
        if (Byte.compare(packet.getDHCPMessageType(), (byte) 2) == 0) {
            ip = packet.getYiaddr();
            sentPacket = msg.request(xid, InetAddress.getLocalHost(), (byte) 3);
        }
        if (Byte.compare(packet.getDHCPMessageType(), (byte) 5) == 0) {
            sentPacket = msg.release(xid, ip, InetAddress.getLocalHost(), (byte) 7);
        }
        return sentPacket;
    }

    public static void main(String[] args) throws Exception {

        try {

            DHCPClient client1 = new DHCPClient();

            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            byte[] macProv = network.getHardwareAddress();
            String[] sb = new String[macProv.length];
            for (int i = 0; i < macProv.length; i++) {
                sb[i] = String.format("%02X", macProv[i]);
            }
            // having the mac Adress
            client1.mac = String.join("", sb);
            host = InetAddress.getByName("192.168.88.179");
            DHCPPacket message = null;
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;
            boolean firstConversation = true;
            Socket client = new Socket(host.getHostName(), portListening);
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            while (true) {
                System.out.println(client.getInputStream());
                message = (DHCPPacket) ois.readObject();
                // see the packet
                System.out.println(message);
                String[] sbMessage = new String[macProv.length];
                for (int i = 0; i < macProv.length; i++) {
                    sbMessage[i] = String.format("%02X", message.getChaddr()[i]);
                }
                // compare if the message is for this client
                if (String.join("", sbMessage).compareToIgnoreCase(client1.mac) == 0
                        || message.getDHCPMessageType() == 0) {

                    if (firstConversation == true) {
                        firstConversation = false;
                        DHCPmsg msg = new DHCPmsg();
                        oos.writeObject(msg.discover(1, InetAddress.getLocalHost(), (byte) 1));
                    } else {
                        oos.writeObject(client1.arrangement(message));
                    }
                    if (Byte.compare(message.getDHCPMessageType(), (byte) 5) == 0) {

                        Configuration main = new Configuration(message.getCiaddr().getHostAddress(), "255.255.255.0",
                                message.getGiaddr().getHostAddress());
                        // exec le programme interne
                        main.mainRun();
                        oos.writeObject(client1.arrangement(message));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("serveur is close" + e);
            // TODO: handle exception
        }

    }
}
