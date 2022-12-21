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

public class DHCPServer extends Thread {
    private static int portListening = 68;
    private static int portsender = 67;
    private static String gIPAdress = "192.185.10.30";
    private Vector<String> IPDispo;
    private int xid;
    private String serveurIP;
    private static int dispo;
    private static String pref;
    private static int limit;

    public void setIPDispo(Vector<String> iPDispo) {
        IPDispo = iPDispo;
    }

    public static int getPortsender() {
        return portsender;
    }

    public static int getPortListening() {
        return portListening;
    }

    public static void setDispo(int dispo) {
        DHCPServer.dispo = dispo;
    }

    public Vector<String> getIPDispo() {
        return IPDispo;
    }

    public int getDispo() {
        return dispo;
    }

    public String getServeurIP() {
        return serveurIP;
    }

    // etablissement du packet a envoye par rapport au packet recu
    public DHCPPacket arrangement(DHCPPacket packet) throws Exception {
        DHCPPacket sentPacket = null;
        DHCPmsg msg = new DHCPmsg();
        System.out.println(packet.getDHCPMessageType());
        if (Byte.compare(packet.getDHCPMessageType(), (byte) 1) == 0) {
            sentPacket = msg.offer(xid, InetAddress.getByName(IPDispo.get(dispo)), InetAddress.getByName(serveurIP),
                    packet.getChaddr(), InetAddress.getByName(gIPAdress), (byte) 2);
        } else if (Byte.compare(packet.getDHCPMessageType(), (byte) 3) == 0) {
            sentPacket = msg.pack(xid, InetAddress.getByName(IPDispo.get(dispo)), InetAddress.getByName(serveurIP),
                    packet.getChaddr(), InetAddress.getByName(gIPAdress), (byte) 5);
        }
        return sentPacket;
    }

    // creation de la plage d'ip
    public void createIPDispo() {
        Vector<String> ip = new Vector<>();

        for (int i = 1; i < limit; i++) {
            ip.add(pref + i);
        }
        this.setIPDispo(ip);
    }

    // having all ip dispo
    public static void getConfiguration(String ipnetmask) {
        String adresse = ipnetmask;
        String[] parties = adresse.split("/");
        String ip = parties[0];

        int prefixe;
        if (parties.length < 2) {
            prefixe = 0;
        } else {
            prefixe = Integer.parseInt(parties[1]);
        }
        System.out.println("Addresse =\t" + ip + "\nPrefixe =\t" + prefixe);

        // convertir le masque entier en un tableau de 32bits
        int masque = 0xffffffff << (32 - prefixe);
        int valeur = masque;
        byte[] bytes_masque = new byte[] {
                (byte) (valeur >>> 24), (byte) (valeur >> 16 & 0xff), (byte) (valeur >> 8 & 0xff),
                (byte) (valeur & 0xff) };

        try {
            // masque
            InetAddress netAddr = InetAddress.getByAddress(bytes_masque);
            System.out.println("Masque =\t" + netAddr.getHostAddress());

            /*************************
             * Adresse réseau
             */
            // Convertir l'adresse IP en long
            long ipl = ipToLong(ip);

            // Convertir l'IP en un tableau de 32bits
            byte[] bytes_ip = new byte[] {
                    (byte) ((ipl >> 24) & 0xFF),
                    (byte) ((ipl >> 16) & 0xFF),
                    (byte) ((ipl >> 8) & 0xFF),
                    (byte) (ipl & 0xFF) };

            // Le ET logique entre l'adresse IP et le masque
            byte[] bytes_reseau = new byte[] {
                    (byte) (bytes_ip[0] & bytes_masque[0]),
                    (byte) (bytes_ip[1] & bytes_masque[1]),
                    (byte) (bytes_ip[2] & bytes_masque[2]),
                    (byte) (bytes_ip[3] & bytes_masque[3]),
            };
            // adresse réseau obtenue
            InetAddress adr_reseau = InetAddress.getByAddress(bytes_reseau);
            System.out.println("Adresse réseau =\t" + adr_reseau.getHostAddress());
            pref = adr_reseau.getHostAddress().split("\\.")[0] + "." + adr_reseau.getHostAddress().split("\\.")[1]
                    + "."
                    + adr_reseau.getHostAddress().split("\\.")[2] + ".";
            // inverser le masque
            bytes_masque = new byte[] {
                    (byte) (~bytes_masque[0] & 0xff),
                    (byte) (~bytes_masque[1] & 0xff),
                    (byte) (~bytes_masque[2] & 0xff),
                    (byte) (~bytes_masque[3] & 0xff),
            };
            limit = Integer.parseInt(InetAddress.getByAddress(bytes_masque).getHostAddress().split("\\.")[3]);// ip
                                                                                                              // limit

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static long ipToLong(String ipAddress) {
        long result = 0;
        String[] ipAddressInArray = ipAddress.split("\\.");

        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            result |= ip << (i * 8);
        }
        return result;
    }

    public static void main(String[] args) {

        DHCPServer serveur1 = new DHCPServer();

        try {
            DHCPServer.getConfiguration("192.180.0.0/26");
            serveur1.createIPDispo();
            ServerSocket serveur = new ServerSocket(portListening);
            while (true) {
                Socket client = serveur.accept();
                ThreadServeur server = new ThreadServeur(client, serveur1);
                server.start();
            }

        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
    }

}
