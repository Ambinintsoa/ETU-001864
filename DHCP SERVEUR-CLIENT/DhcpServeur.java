import org.onosproject.net.*;
import org.onosproject.net.provider.*;
import org.onlab.packet.*;
import org.onosproject.net.packet.*;
import org.onosproject.net.host.*;
import org.onlab.packet.Ip4Address;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import org.dhcp4java.*;

import static org.dhcp4java.DHCPConstants.BOOTP_REPLY_PORT;
import static org.dhcp4java.DHCPConstants.BOOTP_REQUEST_PORT;
import static org.dhcp4java.DHCPConstants.BOOTREQUEST;
import static org.dhcp4java.DHCPConstants.DHCPDISCOVER;
import static org.dhcp4java.DHCPConstants.DHCPINFORM;
import static org.dhcp4java.DHCPConstants.DHCPREQUEST;
import static org.dhcp4java.DHCPConstants.DHO_DHCP_REQUESTED_ADDRESS;
import static org.dhcp4java.DHCPConstants.HTYPE_ETHER;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.dhcp4java.DHCPOption;
import org.dhcp4java.DHCPPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DhcpServeur {
    // private DHCP dhcpXManager;
    // protected PacketProcessor packetProcessor;
    // protected HostProviderService hostProviderService;
    // private static final HostId CLIENT1_HOST =
    // HostId.hostId(MacAddress.valueOf("1a:1a:1a:1a:1a:1a"));
    // private static final String EXPECTED_IP = "10.2.0.2";
    // private static final Ip4Address BROADCAST =
    // Ip4Address.valueOf("255.255.255.255");
    // private static final int TRANSACTION_ID = 1000;
    // private static final ProviderId PID = new ProviderId("of", "foo");
    // private static int PLAGE_IP = 0;

    // public String getIP() {
    // String ip = "100.100.010" + PLAGE_IP;
    // PLAGE_IP = PLAGE_IP + 1;
    // return ip;
    // }

    // public int correspondantPacket(DHCP paquet) {
    // if (paquet.getPacketType().getValue() ==
    // DHCPPacketType.getType(1).getValue()) {
    // return 2;
    // } else if (paquet.getPacketType().getValue() ==
    // DHCPPacketType.getType(3).getValue()) {
    // return 5;
    // }
    // }

    // public DHCPPacket createpaquet(int paquetValue) {
    // // DHCP dhcpReply = new DHCP();
    // // dhcpReply.setOpCode(DHCP.OPCODE_REQUEST);
    // // dhcpReply.setYourIPAddress(0);
    // // dhcpReply.setServerIPAddress(0);
    // // dhcpReply.setTransactionId(TRANSACTION_ID);
    // // dhcpReply.setClientHardwareAddress(CLIENT1_HOST.mac().toBytes());
    // // dhcpReply.setHardwareType(DHCP.HWTYPE_ETHERNET);
    // // dhcpReply.setHardwareAddressLength((byte) 6);
    // // final DHCPPacket discover = new DHCPPacket ();
    // // discover.setOp (BOOTREQUEST);
    // // discover.setHtype (HTYPE_ETHER);
    // // discover.setHlen ((byte) 6);
    // // discover.setHops ((byte) 0);
    // // discover.setXid ((new Random ()).nextInt ());
    // // discover.setSecs ((short) 0);
    // // discover.setFlags ((short) 0);
    // // discover.setChaddr (macAddress);

    // }

    public DHCPPacket createPacket(int xid, InetAddress mIP, InetAddress mac, byte messageType) throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();

        dhcpPacket.setOp((byte) 0);
        dhcpPacket.setDHCPMessageType(messageType);

        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        // dhcpPacket.setChaddr(mac.getAddress());
        NetworkInterface net = NetworkInterface.getByInetAddress(mac);
        dhcpPacket.setChaddr(net.getHardwareAddress());
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setGiaddr(mIP);
            dhcpPacket.setSiaddr(mIP);
            dhcpPacket.setYiaddr(mIP);
            dhcpPacket.setHops((byte) 1);
            dhcpPacket.setSname("DHCP3.0");
            dhcpPacket.setFile("conf.txt");

        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }

        // switch (dhcpPacket.getDHCPMessageType()) {
        // case DHCPREQUEST: {
        // dhcpPacket.setOption(DHCPOption.newOptionAsInetAddress(DHO_DHCP_REQUESTED_ADDRESS,
        // transaction.getRequestIpAddress()));
        // dhcpPacket.setCiaddr(transaction.getRequestIpAddress());
        // break;
        // }
        // case DHCPINFORM: {
        // dhcpPacket.setOption(
        // DHCPOption.newOptionAsInetAddress(DHO_DHCP_REQUESTED_ADDRESS,
        // transaction.getMyIpAddress()));
        // dhcpPacket.setCiaddr(transaction.getMyIpAddress());
        // break;
        // }
        // }

        return dhcpPacket;
    }

    public static void main(String[] args) {
        try {
            DhcpServeur s = new DhcpServeur();
            InetAddress a = InetAddress.getByName("127.0.0.1");
            InetAddress b = InetAddress.getLocalHost();
            System.out.println(b);

            DHCPPacket c = s.createPacket(0, a, b, (byte) 2);
            System.out.println(c.getFileRaw());

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
