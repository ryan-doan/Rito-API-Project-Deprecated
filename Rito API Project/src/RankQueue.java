/*
 * RankQueue
 *
 * Same as Summoner but for different queues, TFT not yet included
 */

public class RankQueue {
    String leagueId;
    String queueType;
    String tier;
    int rank;
    int leaguePoints;
    int wins;
    int losses;
    boolean veteran;
    boolean freshBlood;
    boolean hotStreak;

    public RankQueue (String leagueId, String queueType, String tier, int rank, int leaguePoints,
                      int wins, int losses, boolean veteran, boolean freshBlood, boolean hotStreak) {
        this.leagueId = leagueId;
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
        this.veteran = veteran;
        this.freshBlood = freshBlood;
        this.hotStreak = hotStreak;
    }
}
