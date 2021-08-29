/*
 * RankQueue
 *
 * Same as Summoner but for different queues, TFT not yet included
 */

public class RankQueue {
    String queueType;
    String tier;
    String rank;
    int leaguePoints;
    int wins;
    int losses;
    boolean veteran;
    boolean inactive;
    boolean freshBlood;
    boolean hotStreak;

    public RankQueue (String queueType, String tier, String rank, int leaguePoints,
                      int wins, int losses, boolean veteran, boolean inactive, boolean freshBlood, boolean hotStreak) {
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
        this.veteran = veteran;
        this.inactive = inactive;
        this.freshBlood = freshBlood;
        this.hotStreak = hotStreak;
    }

    public RankQueue(String queueType) {
        this.queueType = queueType;
        this.tier = "Unranked";
        this.rank = "";
        this.leaguePoints = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public String getTier() {
        return tier;
    }

    public String getRank() {
        return rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public String toString() {
        return String.format("%s %s %s", queueType, tier, rank);
    }
}
