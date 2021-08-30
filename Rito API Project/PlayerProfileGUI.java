package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerProfileGUI {
    Font font;
    Color black, grey;
    JFrame frame;
    JPanel panel;

    //  Basic player info panel

    JPanel infoPanel;
    JLabel profilePic;
    JTextArea nameAndLevel;

    //  Ranked panel

    JPanel rankedPanel = new JPanel(null);
    JLabel soloQPic = new JLabel();
    JTextArea soloQText = new JTextArea();
    JLabel flexQPic = new JLabel();
    JTextArea flexQText = new JTextArea();

    public static void main(String[] args) {
        new PlayerProfileGUI();
    }

    public PlayerProfileGUI() {
        try {
            font = Font.createFont
                    (Font.TRUETYPE_FONT, new File("Christmas.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        black = new Color(59, 11, 59);
        grey = new Color(86, 24, 86);

        frame = new JFrame("Profile");
        panel = new JPanel(null);
        panel.setBackground(black);

        infoPanel = new JPanel(null);
        infoPanel.setBounds(20, 20, 245, 120);
        infoPanel.setBackground(grey);
        frame.add(infoPanel);

        profilePic = new JLabel();
        profilePic.setBounds(20, 20, 75, 75);
        infoPanel.add(profilePic);

        nameAndLevel = new JTextArea();
        nameAndLevel.setBounds(105, 20, 200, 100);
        nameAndLevel.setFont(font.deriveFont(25f));
        nameAndLevel.setForeground(Color.white);
        nameAndLevel.setBackground(grey);
        nameAndLevel.setEditable(false);
        infoPanel.add(nameAndLevel);

        rankedPanel.setBounds(20, 150, 245,150);
        rankedPanel.setBackground(grey);
        frame.add(rankedPanel);

        soloQPic.setBounds(20, 30, 75, 75);
        rankedPanel.add(soloQPic);

        JTextArea soloQHeader = new JTextArea();
        soloQHeader.setBounds(20, 10, 100, 20);
        soloQHeader.setFont(font.deriveFont(11f));
        soloQHeader.setForeground(Color.white);
        soloQHeader.setBackground(grey);
        soloQHeader.setText("Solo/Duo");
        soloQHeader.setEditable(false);
        rankedPanel.add(soloQHeader);

        soloQText.setBounds(20, 110, 100, 50);
        soloQText.setFont(font.deriveFont(11f));
        soloQText.setForeground(Color.white);
        soloQText.setBackground(grey);
        soloQText.setEditable(false);
        rankedPanel.add(soloQText);

        flexQPic.setBounds(150, 30, 75, 75);
        rankedPanel.add(flexQPic);

        flexQText.setBounds(150, 110, 100, 50);
        flexQText.setFont(font.deriveFont(11f));
        flexQText.setForeground(Color.white);
        flexQText.setBackground(grey);
        flexQText.setEditable(false);
        rankedPanel.add(flexQText);

        JTextArea flexQHeader = new JTextArea();
        flexQHeader.setBounds(150, 10, 100, 20);
        flexQHeader.setFont(font.deriveFont(11f));
        flexQHeader.setForeground(Color.white);
        flexQHeader.setBackground(grey);
        flexQHeader.setText("Flex");
        flexQHeader.setEditable(false);
        rankedPanel.add(flexQHeader);

        frame.setSize(300, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(false);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getNameAndLevel() {
        return nameAndLevel;
    }

    public JLabel getProfilePic() {
        return profilePic;
    }

    public JLabel getSoloQPic() {
        return soloQPic;
    }

    public JTextArea getSoloQText() {
        return soloQText;
    }

    public JLabel getFlexQPic() {
        return flexQPic;
    }

    public JTextArea getFlexQText() {
        return flexQText;
    }
}
