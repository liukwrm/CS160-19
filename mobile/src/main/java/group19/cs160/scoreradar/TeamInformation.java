package group19.cs160.scoreradar;

/**
 * Created by liukwarm on 12/4/15.
 */
public class TeamInformation {

    String name, record;
    boolean subscribed;
    int logo;

    public TeamInformation(String name, String record, boolean subscribed) {
        this.name = name;
        this.record = record;
        this.subscribed = subscribed;

        logo = GameInformation.getLogo(name);
    }
}
