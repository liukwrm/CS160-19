package group19.cs160.scoreradar;

/**
 * Created by liukwarm on 12/4/15.
 */
public class TeamInformation {

    String name;
    int wins;
    int losses;
    String id;
    boolean subscribed;
    int logo;

    public TeamInformation(String name, int wins, int losses, String id, boolean subscribed) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.id = id;
        this.subscribed = subscribed;

        //logo = GameInformation.getLogo(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
