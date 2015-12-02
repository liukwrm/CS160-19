package group19.cs160.scoreradar.Pojo;

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

    public Game(String id, String home, String away, String time) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.time = time;
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

    protected Game(Parcel in) {
        id = in.readString();
        home = in.readString();
        away = in.readString();
        time = in.readString();
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