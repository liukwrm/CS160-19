package group19.cs160.scoreradar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liukwarm on 12/4/15.
 */
public class TeamInformation implements Parcelable {

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

    protected TeamInformation(Parcel in) {
        name = in.readString();
        wins = in.readInt();
        losses = in.readInt();
        id = in.readString();
        subscribed = in.readByte() != 0x00;
        logo = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeString(id);
        dest.writeByte((byte) (subscribed ? 0x01 : 0x00));
        dest.writeInt(logo);
    }

    @SuppressWarnings("unused")
    public static final Creator<TeamInformation> CREATOR = new Creator<TeamInformation>() {
        @Override
        public TeamInformation createFromParcel(Parcel in) {
            return new TeamInformation(in);
        }

        @Override
        public TeamInformation[] newArray(int size) {
            return new TeamInformation[size];
        }
    };
}