
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.dhcp4java.DHCPOption;
import org.dhcp4java.DHCPPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DhcpConfiguration {
    public DHCPPacket createPacket(int xid, InetAddress mIP, InetAddress mac, byte messageType) {
        final DHCPPacket dhcpPacket = new DHCPPacket();

        dhcpPacket.setOp((byte) 0);
        dhcpPacket.setDHCPMessageType(messageType);

        dhcpPacket.setHtype(HTYPE_ETHER);
        dhcpPacket.setHlen((byte) 6);
        dhcpPacket.setChaddr(mac.getAddress());
        dhcpPacket.setXid(xid);
        dhcpPacket.setSecs((short) 0);

        if (true) {
            dhcpPacket.setGiaddr(mIP);
            dhcpPacket.setHops((byte) 1);
        } else {
            dhcpPacket.setHops((byte) 0);
            dhcpPacket.setFlags((short) 0x8000);
        }
        return dhcpPacket;
    }
}
