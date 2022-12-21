package graphisme;

import java.net.NetworkInterface;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import listener.*;
import java.awt.*;

public class ClientInteraction extends JFrame {

    public ClientInteraction() throws Exception {
        try {
            this.setSize(500, 200);
            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] macProv = network.getHardwareAddress();
            String[] sb = new String[macProv.length];
            for (int i = 0; i < macProv.length; i++) {
                sb[i] = String.format("%02X", macProv[i]);
            }

            JLabel macText = new JLabel("ORDINATEUR:" + String.join("", sb));
            macText.setBounds(200, 10, 300, 15);
            JLabel textIntro = new JLabel("Utiliser notre logiciel pour attribuer un INTERNET PROCOCOL(IP) ");
            textIntro.setBounds(40, 50, 1000, 15);
            JButton validation = new JButton("CONFIGURATION ");
            validation.setBounds(160, 100, 150, 15);
            validation.addMouseListener(new Listener());
            JPanel panelGraph = new JPanel();
            this.setBounds(500, 300, 500, 200);
            panelGraph.setLayout(null);
            panelGraph.add(textIntro);
            panelGraph.add(macText);
            panelGraph.add(validation);
            this.setJMenuBar(null);
            this.add(panelGraph);
            this.setResizable(false);
            this.setVisible(true);
            this.setDefaultCloseOperation(3);
        } catch (Exception e) {
            // TODO: handle exception
            try {
                JOptionPane option = new JOptionPane();
                option.showMessageDialog(this, "CAN'T ACCESS TO YOUR INFORMATION");
                throw e;
            } catch (Exception eh) {
                // TODO: handle exception
                throw eh;
            }

        }

    }
}
