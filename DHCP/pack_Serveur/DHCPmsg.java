package communication;

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

//byte[] mac pour serveur car prends l'adresse du packet
//InetAdress pour le client car c'est son adresse
public class DHCPmsg {
    public DHCPPacket createPacket(int xid, InetAddress mIP, InetAddress mac, byte messageType) throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();

        dhcpPacket.setOp((byte) 0);
        dhcpPacket.setDHCPMessageType(messageType);

        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        NetworkInterface net = NetworkInterface.getByInetAddress(mac);
        dhcpPacket.setChaddr(net.getHardwareAddress());
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setGiaddr(InetAddress.getLoopbackAddress());
            dhcpPacket.setSiaddr(mIP);
            dhcpPacket.setYiaddr(mIP);
            dhcpPacket.setHops((byte) 1);

        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }

        return dhcpPacket;
    }

    public DHCPPacket discover(int xid, InetAddress mac, byte messageType) throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();

        dhcpPacket.setOp((byte) 1);
        dhcpPacket.setDHCPMessageType(messageType);
        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        NetworkInterface net = NetworkInterface.getByInetAddress(mac);
        dhcpPacket.setChaddr(net.getHardwareAddress());
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);

        } else {
            dhcpPacket.setHops((byte) 0);

        }

        return dhcpPacket;
    }

    public DHCPPacket offer(int xid, InetAddress iP, InetAddress mIP, byte[] mac, InetAddress gateway,
            byte messageType)
            throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();

        dhcpPacket.setOp((byte) 2);
        dhcpPacket.setDHCPMessageType(messageType);
        dhcpPacket.setGiaddr(gateway);
        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        dhcpPacket.setChaddr(mac);
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setSiaddr(mIP);
            dhcpPacket.setYiaddr(iP);// ilay omena
            dhcpPacket.setHops((byte) 1);

        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }

        return dhcpPacket;
    }

    public DHCPPacket request(int xid, InetAddress mac, byte messageType) throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();
        dhcpPacket.setOp((byte) 1);
        dhcpPacket.setDHCPMessageType(messageType);
        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        NetworkInterface net = NetworkInterface.getByInetAddress(mac);
        dhcpPacket.setChaddr(net.getHardwareAddress());
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setHops((byte) 1);

        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }

        return dhcpPacket;
    }

    public DHCPPacket pack(int xid, InetAddress iP, InetAddress mIP, byte[] mac, InetAddress gateway,
            byte messageType)
            throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();

        dhcpPacket.setOp((byte) 2);
        dhcpPacket.setDHCPMessageType(messageType);
        dhcpPacket.setGiaddr(gateway);
        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        dhcpPacket.setChaddr(mac);
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setSiaddr(mIP);
            dhcpPacket.setYiaddr(iP);
            dhcpPacket.setCiaddr(iP);
            dhcpPacket.setHops((byte) 1);

        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }

        return dhcpPacket;
    }

    public DHCPPacket release(int xid, InetAddress mIP, InetAddress mac, byte messageType)
            throws Exception {
        final DHCPPacket dhcpPacket = new DHCPPacket();
        dhcpPacket.setOp((byte) 1);
        dhcpPacket.setDHCPMessageType(messageType);
        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        NetworkInterface net = NetworkInterface.getByInetAddress(mac);
        dhcpPacket.setChaddr(net.getHardwareAddress());
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setCiaddr(mIP);
            dhcpPacket.setHops((byte) 1);

        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }

        return dhcpPacket;
    }

}
