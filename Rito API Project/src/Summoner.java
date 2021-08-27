/*
 * Summoner
 *
 * For storing data, replacing txt files in the future
 */

public class Summoner {
    String id;
    String accountId;
    String puuid;
    String name;
    int profileIconId;
    int summonerLevel;
    RankQueue soloQ;
    RankQueue flex;

    public Summoner(String id, String accountId, String puuid, String name, int profileIconId,
                    int summonerLevel, RankQueue soloQ, RankQueue flex) {
        this.id = id;
        this.accountId = accountId;
        this.puuid = puuid;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.soloQ = soloQ;
        this.flex = flex;
    }
}
