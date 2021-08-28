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

    String API_key = "?api_key=RGAPI-817c033f-2570-4f79-860c-439ddbf218ac";
    Font font;

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

            // Connects to Rito's API
            try {
                Summoner player = search(startingGUI.getUsername().getText());
                //Refer to the search method

                if (player != null) {

                    System.out.println("Player's profileIconID is " + player.getProfileIconId());
                    System.out.println("Player's level is " + player.getSummonerLevel());

                    startingGUI.getFrame().setVisible(false);
                    playerProfileGUI.getFrame().setVisible(true);
                    playerProfileGUI.getNameAndLevel().setText(player.getName() + "\n");
                    playerProfileGUI.getNameAndLevel().append("<Level " + player.getSummonerLevel() + ">");

                    try {
                        ImageIcon imageIcon = new ImageIcon(new URL("http://ddragon.leagueoflegends.com/cdn/" +
                                "11.16.1/img/profileicon/" + player.getProfileIconId() + ".png"));
                        Image image = imageIcon.getImage().getScaledInstance(75, 75,
                                java.awt.Image.SCALE_SMOOTH);

                        imageIcon = new ImageIcon(image);

                        playerProfileGUI.getProfilePic().setIcon(imageIcon);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                } else {
                    System.out.println("You typed " + player.getName());
                    startingGUI.getErrorLabel().setText("Incorrect username.. Try again!");
                    startingGUI.getErrorLabel().setFont(font.deriveFont(18f));
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("Error");
            }
        }
    }

    public Summoner search(String summonerName) throws Exception {
        //Connects to Rito's API

        URL url = new URL("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +
                summonerName.replaceAll(" ", "") + API_key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        if (connection.getResponseCode() == 200) {  //Making sure the player exists before we do anything else
            //Get player data

            String rawData = reader.readLine();
            reader.close();

            // Formats data

            rawData = rawData.substring(1, rawData.length() - 1).replaceAll("\"",
                    "");

            String[] summonerData = rawData.split(",");

            return newSummoner(summonerData);  //Refer to newSummoner method
        }

        return null;
    }

    public Summoner newSummoner(String[] summonerData) {
        //formats data from the string[] and use it to create a new player object

        Summoner player = new Summoner(summonerData[0].replaceAll("id:", ""),
                summonerData[1].replaceAll("adccountId", ""),
                summonerData[2].replaceAll("puuid:", ""),
                summonerData[3].replaceAll("name:", ""),
                Integer.parseInt(summonerData[4].replaceAll("profileIconId:", "")),
                summonerData[5].replaceAll("revisionDate:", ""),
                Integer.parseInt(summonerData[6].replaceAll("summonerLevel:", "")));

        return player;
    }

    public static void skipLine(BufferedReader reader, int lines) throws Exception {
        for (int i = 0; i < lines; i++) {
            reader.readLine();
        }
    }  //this is to skip multiple lines on a txt file
}
