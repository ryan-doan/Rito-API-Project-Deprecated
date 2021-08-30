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

    String API_key = "?api_key=RGAPI-223634df-6dfb-4fcd-95df-c3d24f575056";
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


                    ImageIcon imageIcon = new ImageIcon(new URL("http://ddragon.leagueoflegends.com/cdn/" +
                                "11.16.1/img/profileicon/" + player.getProfileIconId() + ".png"));
                    Image image = imageIcon.getImage().getScaledInstance(75, 75,
                                java.awt.Image.SCALE_SMOOTH);

                    imageIcon = new ImageIcon(image);

                    playerProfileGUI.getProfilePic().setIcon(imageIcon);


                    //set player variable's rank data. See rankData method for more information

                    rankedData(player);

                    System.out.println(player.getFlex().toString());
                    System.out.println(player.getSoloQ().toString());

                    //display player's ranked data

                    Image soloQ = new ImageIcon(String.format("Emblem_%s.png",player.getSoloQ().getTier())).getImage()
                            .getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                    Image flexQ = new ImageIcon(String.format("Emblem_%s.png",player.getFlex().getTier())).getImage()
                            .getScaledInstance(75, 75, Image.SCALE_SMOOTH);

                    playerProfileGUI.getSoloQPic().setIcon(new ImageIcon(soloQ));
                    playerProfileGUI.getSoloQText().setText(String.format("%s %s\n%d LP", player.getSoloQ().getTier(),
                            player.getSoloQ().getRank(), player.getSoloQ().getLeaguePoints()));

                    playerProfileGUI.getFlexQPic().setIcon(new ImageIcon(flexQ));
                    playerProfileGUI.getFlexQText().setText(String.format("%s %s\n%d LP", player.getFlex().getTier(),
                            player.getFlex().getRank(), player.getFlex().getLeaguePoints()));

                    playerProfileGUI.getFrame().setVisible(true);

                } else {
                    startingGUI.getErrorLabel().setText("An Error Occured.");
                    startingGUI.getErrorLabel().setFont(font.deriveFont(18f));
                }

            } catch (FileNotFoundException exception) {
                startingGUI.getErrorLabel().setText("Username doesn't exists");
                startingGUI.getErrorLabel().setFont(font.deriveFont(18f));
            } catch (Exception exception) {
                exception.printStackTrace();
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

            player.setSoloQ(new RankQueue("RANKED_SOLO_5x5"));
            player.setFlex(new RankQueue("RANKED_FLEX_SR"));

        } else {

            rawData = rawData.substring(1, rawData.length() - 1).replaceAll("[\"{}]",
                    "").replaceAll("[a-zA-Z]*:", "");

            String[] rankedData = rawData.split(",");

            if (rankedData.length < 14) {  //only 1 rank queue

                if (rankedData[1].equals("RANKED_SOLO_5x5")) {
                    //  see rankQueue method in this class

                    player.setSoloQ(rankQueue(rankedData, 1));
                    player.setFlex(new RankQueue("RANKED_FLEX_SR"));
                } else {
                    player.setSoloQ(new RankQueue("RANKED_SOLO_5x5"));
                    player.setFlex(rankQueue(rankedData, 1));
                }

            } else {  //both rank queue

                if (rankedData[1].equals("RANKED_SOLO_5x5")) {
                    player.setSoloQ(rankQueue(rankedData, 1));
                    player.setFlex(rankQueue(rankedData, 14));
                } else {
                    player.setFlex(rankQueue(rankedData, 1));
                    player.setSoloQ(rankQueue(rankedData, 14));
                }  //  Sometimes Riot's API just decide to swap Flex and SoloQ around, requiring this check to make
                   //  sure we don't swap the queues with each other.

            }

        }

    }

    public RankQueue rankQueue(String[] rankedData, int index) {

        return new RankQueue(rankedData[index], rankedData[index + 1], rankedData[index + 2],
                Integer.parseInt(rankedData[index + 5]), Integer.parseInt(rankedData[index + 6]),
                Integer.parseInt(rankedData[index + 7]), Boolean.parseBoolean(rankedData[index + 8]),
                Boolean.parseBoolean(rankedData[index + 9]), Boolean.parseBoolean(rankedData[index + 10]),
                Boolean.parseBoolean(rankedData[index + 11]));

    }  //create a rankQueue object from the data in the String array

    public static void skipLine(BufferedReader reader, int lines) throws Exception {

        for (int i = 0; i < lines; i++) {
            reader.readLine();
        }

    }  //this is to skip multiple lines on a txt file
}
