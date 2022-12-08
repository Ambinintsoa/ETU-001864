package graphisme;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import listener.*;

public class ClientInteraction extends JFrame {

    public ClientInteraction() {
        this.setSize(200, 250);
        JLabel macText = new JLabel("MAC ADDRESS");
        JLabel text = new JLabel("DHCP SERVEUR");
        JLabel textIntro = new JLabel("Configurer votre adresse IP ");
        JButton validation = new JButton("valider");
        validation.addMouseListener(new Listener());
        JPanel panelGraph = new JPanel();
        panelGraph.add(text);
        panelGraph.add(textIntro);
        panelGraph.add(macText);
        panelGraph.add(validation);
        this.add(panelGraph);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
    }
}
