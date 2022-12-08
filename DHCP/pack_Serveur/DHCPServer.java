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

public class DHCPServer {
    private static ServerSocket serveur;
<<<<<<< HEAD
    private static int portListening = 68;
    private static int portsender = 67;
=======
    private static int portListening = 1168;
>>>>>>> parent of a8bf33c (Delete DHCP directory)
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
            System.out.println(pref + "ssss");
            /********************************
             * Adresse de diffusion broadcast
             */
            // adresse réseau - masque inversé ~val & 0xff
            // inverser le masque
            bytes_masque = new byte[] {
                    (byte) (~bytes_masque[0] & 0xff),
                    (byte) (~bytes_masque[1] & 0xff),
                    (byte) (~bytes_masque[2] & 0xff),
                    (byte) (~bytes_masque[3] & 0xff),
            };
            System.out
                    .println("Masque inverse (Wildcard) =\t" + InetAddress.getByAddress(bytes_masque).getHostAddress());
            System.out.println("aaa" + InetAddress.getByAddress(bytes_masque).getHostAddress().split("\\.")[3]);
            limit = Integer.parseInt(InetAddress.getByAddress(bytes_masque).getHostAddress().split("\\.")[3]);
            byte[] bytes_broadcast = new byte[] {
                    (byte) (bytes_reseau[0] | bytes_masque[0]),
                    (byte) (bytes_reseau[1] | bytes_masque[1]),
                    (byte) (bytes_reseau[2] | bytes_masque[2]),
                    (byte) (bytes_reseau[3] | bytes_masque[3]),
            };
            // adresse Broadcast obtenue
            InetAddress adrbroadcast = InetAddress.getByAddress(bytes_broadcast);
            System.out.println("Adresse de diffusion (Broadcast) =\t" + adrbroadcast.getHostAddress());

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
<<<<<<< HEAD
            System.out.println(portListening);
            System.out.println(portsender);
=======
>>>>>>> parent of a8bf33c (Delete DHCP directory)
            DHCPPacket message = null;
            DHCPServer.getConfiguration("192.180.0.0/26");
            serveur1.createIPDispo();
            serveur = new ServerSocket(portListening);
            while (true) {
                Socket client = serveur.accept();
<<<<<<< HEAD
                Socket client1 = new Socket(client.getInetAddress(), portsender);
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(client1.getOutputStream());
=======
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
>>>>>>> parent of a8bf33c (Delete DHCP directory)
                DHCPmsg msg = new DHCPmsg();
                while (true) {

                    if (message != null) {
                        oos.writeObject(serveur1.arrangement(message));
                    } else {
                        oos.writeObject(msg.createPacket(1, InetAddress.getLocalHost(),
                                InetAddress.getLocalHost(), (byte) 0));
                    }
                    message = (DHCPPacket) ois.readObject();
                    System.out.println(message);
                    if (Byte.compare(message.getDHCPMessageType(), (byte) 7) == 0) {
                        dispo = dispo + 1;
                        client.close();
                        message = null;
                        client = null;
<<<<<<< HEAD
                        client1 = null;

=======
>>>>>>> parent of a8bf33c (Delete DHCP directory)
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }

    }
}
