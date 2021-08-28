import Views.PlayerProfileGUI;
import Views.StartingGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class Launcher implements ActionListener {
    // Initialize GUIs
    StartingGUI startingGUI;
    PlayerProfileGUI playerProfileGUI;

    String API_key = "?api_key=RGAPI-61e24667-0dba-41a6-a866-532ad8226a7f";
    boolean validName = false;
    Font font;

    String summonerName;
    String summonerData;
    String summonerID;
    String accountID;
    String PUUID;
    int profileIconID;
    int summonerLevel;

    public Launcher() {
        init();
    }

    public static void main(String[] args) {
        new Launcher();
    }

    public void init() {
        try {
            font = Font.createFont
                    (Font.TRUETYPE_FONT, new File("Christmas.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        startingGUI = new StartingGUI();
        playerProfileGUI = new PlayerProfileGUI();

        // StartingGUI
        startingGUI.getSearchButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // StartingPage
        if (e.getSource() == startingGUI.getSearchButton()) {
            System.out.println("StartingGUI/Search summoners");

            summonerName = startingGUI.getUsername().getText();

            // Connects to Rito's API
            try {
                URL url = new URL("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +
                        summonerName.replaceAll(" ", "") + API_key);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //Get response code
                int responseCode = connection.getResponseCode();

                if (responseCode != 200) {
                    System.out.println("You typed " + summonerName);
                    startingGUI.getErrorLabel().setText("Incorrect username.. Try again!");
                    startingGUI.getErrorLabel().setFont(font.deriveFont(18f));
                }

                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    validName = true;

                    //Get player data
                    summonerData = reader.readLine();

                    // Formats data and put it into a txt file so its easier to read
                    // TODO delete txt files at the end to save space, might have to find a better way to store data on
                    // RAM but still makes it easier to read out from

                    summonerData = summonerData.substring(1, summonerData.length() - 1).replaceAll("\"",
                            "").replaceAll(",", "\n");

                    String nameFilename = summonerName + ".txt";

                    PrintWriter writer = new PrintWriter(new FileWriter(nameFilename));

                    writer.println(summonerData);
                    writer.flush();
                    writer.close();

                    FileReader fileReader = new FileReader(nameFilename);
                    try (BufferedReader br = new BufferedReader(fileReader)) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] summonerAccountDetails = line.split(":");

                            switch (summonerAccountDetails[0]) {
                                case "profileIconId" -> profileIconID = Integer.parseInt(summonerAccountDetails[1]);
                                case "summonerLevel" -> summonerLevel = Integer.parseInt(summonerAccountDetails[1]);
                            }
                        }

                        fileReader.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    System.out.println("Player's profileIconID is " + profileIconID);
                    System.out.println("Player's level is " + summonerLevel);

                    startingGUI.getFrame().setVisible(false);
                    playerProfileGUI.getFrame().setVisible(true);
                    playerProfileGUI.getNameAndLevel().setText(summonerName + "\n");
                    playerProfileGUI.getNameAndLevel().append("<Level " + summonerLevel + ">");

                    try {
                        ImageIcon imageIcon = new ImageIcon(new URL("http://ddragon.leagueoflegends.com/cdn/" +
                                "11.16.1/img/profileicon/" + profileIconID + ".png"));
                        Image image = imageIcon.getImage().getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);

                        imageIcon = new ImageIcon(image);

                        playerProfileGUI.getProfilePic().setIcon(imageIcon);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }

            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Error");
            }
        }
    }
}
