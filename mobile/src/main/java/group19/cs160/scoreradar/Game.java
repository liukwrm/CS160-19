package group19.cs160.scoreradar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michael on 11/19/2015.
 */
public class Game implements Parcelable {

    private String id;
    private String home;
    private String away;
    private String time;
    private int homeScore;
    private int awayScore;
    private String status;

    public Game() {
    }

    public Game(String id, String home, String away, String time) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.time = time;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public Game(String id, String home, String away, String time, int homeScore, int awayScore, String status) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.time = time;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    protected Game(Parcel in) {
        id = in.readString();
        home = in.readString();
        away = in.readString();
        time = in.readString();
        homeScore = in.readInt();
        awayScore = in.readInt();
        status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(home);
        dest.writeString(away);
        dest.writeString(time);
        dest.writeInt(homeScore);
        dest.writeInt(awayScore);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}