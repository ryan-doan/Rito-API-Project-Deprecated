/*
 * Summoner
 *
 * For storing data while app is open, replacing txt files in the future
 */

public class Summoner {
    String id;
    String accountId;
    String puuid;
    String name;
    String revisionDate;
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

    public Summoner(String id, String accountId, String puuid, String name, int profileIconId, String revisionDate,
                    int summonerLevel) {
        this.id = id;
        this.accountId = accountId;
        this.puuid = puuid;
        this.name = name;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.summonerLevel = summonerLevel;
    }

    public String getName() {
        return name;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public String getId() {
        return id;
    }

    public void setSoloQ(RankQueue soloQ) {
        this.soloQ = soloQ;
    }

    public void setFlex(RankQueue flex) {
        this.flex = flex;
    }

    public RankQueue getSoloQ() {
        return this.soloQ;
    }

    public RankQueue getFlex() {
        return this.flex;
    }
}
