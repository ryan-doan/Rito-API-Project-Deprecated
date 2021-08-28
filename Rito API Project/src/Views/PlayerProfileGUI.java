package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerProfileGUI {
    JFrame frame;
    JPanel panel, infoPanel;
    JLabel profilePic;
    JTextArea nameAndLevel;
    Font font;
    Color lightRed, lighterRed;

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
        lightRed = new Color(252, 171, 193);
        lighterRed = new Color(252, 211, 221);

        frame = new JFrame("Profile");
        panel = new JPanel(null);
        panel.setBackground(lightRed);

        infoPanel = new JPanel(null);
        infoPanel.setBounds(20, 20, 245, 120);
        infoPanel.setBackground(lighterRed);
        frame.add(infoPanel);

        profilePic = new JLabel();
        profilePic.setBounds(20, 20, 75, 75);
        infoPanel.add(profilePic);

        nameAndLevel = new JTextArea();
        nameAndLevel.setBounds(105, 20, 200, 100);
        nameAndLevel.setFont(font.deriveFont(25f));
        nameAndLevel.setBackground(lighterRed);
        nameAndLevel.setEditable(false);
        infoPanel.add(nameAndLevel);

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
}
