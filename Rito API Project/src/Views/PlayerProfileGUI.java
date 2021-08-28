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
    Color black, grey;

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
