package communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;
import org.dhcp4java.DHCPPacket;

public class ThreadServeur extends Thread {
    Socket client;
    DHCPServer serveur;

    public void setClient(Socket client) {
        this.client = client;
    }

    public void setServeur(DHCPServer serveur) {
        this.serveur = serveur;
    }

    public ThreadServeur(Socket client, DHCPServer serveur) {
        setClient(client);
        setServeur(serveur);
    }

    public void run() {
        try {

            DHCPPacket message = null;
            Socket client1 = null;
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;
            DHCPmsg msg = new DHCPmsg();
            while (true) {
                client1 = new Socket(client.getInetAddress(), DHCPServer.getPortsender());
                ois = new ObjectInputStream(client.getInputStream());
                oos = new ObjectOutputStream(client1.getOutputStream());

                while (true) {

                    if (message != null) {
                        oos.writeObject(serveur.arrangement(message));
                    } else {
                        oos.writeObject(msg.createPacket(1, InetAddress.getLocalHost(),
                                InetAddress.getLocalHost(), (byte) 0));
                    }
                    message = (DHCPPacket) ois.readObject();
                    System.out.println(message);
                    if (Byte.compare(message.getDHCPMessageType(), (byte) 7) == 0) {
                        DHCPServer.setDispo(serveur.getDispo() + 1);
                        client.close();
                        System.out.println(client.isClosed());
                        message = null;
                        client = null;
                        client1 = null;
                        ois = null;
                        oos = null;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
