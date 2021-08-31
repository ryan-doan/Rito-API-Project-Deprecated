/*
 *  Store a player's stats from a match. Not to be confused with summoner, which stores basic account info
 */

public class Player {
    //TODO since we can't access match history service right now, some variable types are speculative and might change
    // later on

    //  Basic stats

    String champion;
    String ban;
    int team; //  red or blue
    int kills;
    int deaths;
    int assists;
    int gold;
    int cs;

    //  Summoner spell

    String spell1;
    String spell2;

    //  Runes

    public class Runes {
        String primary;
        String keystone;
        String[] runes;
        String secondary;

        public Runes(String primary, String secondary, String keystone, String[] runes) {
            this.primary = primary;
            this.secondary = secondary;
            this.keystone = keystone;
            this.runes = runes;
        }
    }

    Runes runes;

    //  Damage dealt

    //  Damage taken

    //  Items

    int slot1;
    int slot2;
    int slot3;
    int slot4;
    int slot5;
    int slot6;
    int trinkets;

    //  Ability order

    char[] abilityOrder = new char[18];

    public Player(String champion, String ban, int team, int kills, int deaths, int assists, int gold, int cs,
                  Runes runes) {
        this.champion = champion;
        this.ban = ban;
        this.team = team;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.gold = gold;
        this.cs = cs;
        this.runes = runes;
    }
}
