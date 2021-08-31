/*
 * Stores data from a match
 */

public class Match {
    //TODO since we can't access match history service right now, some variable types are speculative and might change
    // later on

    //  Constants
    final boolean redTeam = true;
    final boolean blueTeam = false;

    //  Match stats

    boolean winner;  //  True for red, false for blue. For sake of clarity, use defined constants above
    String gameID;
    String queueType;
    Player[] players;
    int matchTime;
    int matchDate;

    //  Team stats
    String[] redBans = new String[5];
    String[] blueBans = new String[5];

    int redGold;
    int blueGold;

    int redKills;
    int blueKills;

    int redDeaths;
    int blueDeaths;

    int redAssists;
    int blueAssists;

    int redTowers;
    int blueTowers;

    int redDragons;
    int blueDragons;

    int redBarons;
    int blueBarons;

    int redHeralds;
    int blueHeralds;

    int redInhibs;
    int blueInhibs;
}
