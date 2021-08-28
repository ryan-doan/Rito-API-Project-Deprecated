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
                    
                    //set player variable's rank data. See rankData method for more information
                    
                    rankedData(player);
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
                    "").replaceAll("[a-zA-Z]*:", "");

            System.out.println(rawData);

            String[] summonerData = rawData.split(",");

            return newSummoner(summonerData);  //Refer to newSummoner method
        }

        return null;
    }

    public Summoner newSummoner(String[] summonerData) {
        //formats data from the string[] and use it to create a new player object

        Summoner player = new Summoner(summonerData[0], summonerData[1], summonerData[2], summonerData[3],
                Integer.parseInt(summonerData[4]), summonerData[5], Integer.parseInt(summonerData[6]));

        return player;
    }

    public void rankedData(Summoner player) throws Exception {
        //Connects to Rito's API

        URL url = new URL("https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/"
                + player.getId() + API_key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String rawData = reader.readLine();

        if (rawData.length() < 5) {  //player doesn't play ranked
            
            player.setSoloQ(null);
            player.setFlex(null);
            
        } else {
            
            rawData = rawData.substring(1, rawData.length() - 1).replaceAll("[\"{}]",
                    "").replaceAll("[a-zA-Z]*:", "");

            String[] rankedData = rawData.split(",");

            if (rankedData.length < 14) {  //only 1 rank queue
                
                if (rankedData[1].equals("RANKED_SOLO_5x5")) {
                    //  see rankQueue method in this class

                    player.setSoloQ(rankQueue(rankedData));
                } else {
                    player.setFlex(rankQueue(rankedData));
                }
                
            } else {  //both rank queue
                
                player.setSoloQ(rankQueue(rankedData));
                player.setFlex(new RankQueue(rankedData[14], rankedData[15], Integer.parseInt(rankedData[16]),
                        Integer.parseInt(rankedData[19]), Integer.parseInt(rankedData[20]),
                        Integer.parseInt(rankedData[21]), Boolean.parseBoolean(rankedData[22]),
                        Boolean.parseBoolean(rankedData[23]), Boolean.parseBoolean(rankedData[24]),
                        Boolean.parseBoolean(rankedData[25])));
                
            }
            
        }

    }

    public RankQueue rankQueue(String[] rankedData) {
        
        return new RankQueue(rankedData[1], rankedData[2], Integer.parseInt(rankedData[3]),
                Integer.parseInt(rankedData[6]), Integer.parseInt(rankedData[7]),
                Integer.parseInt(rankedData[8]), Boolean.parseBoolean(rankedData[9]),
                Boolean.parseBoolean(rankedData[10]), Boolean.parseBoolean(rankedData[11]),
                Boolean.parseBoolean(rankedData[12]));
        
    }  //create a rankQueue object from the data in the String array

    public static void skipLine(BufferedReader reader, int lines) throws Exception {
        
        for (int i = 0; i < lines; i++) {
            reader.readLine();
        }
        
    }  //this is to skip multiple lines on a txt file
}
