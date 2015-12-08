package group19.cs160.scoreradar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Michael on 11/19/2015.
 */
public class Game implements Parcelable {

    String id;
    String home;
    String away;
    String time;
    int homeScore;
    int awayScore;
    String homeId;
    String awayId;
    int homeRebounds;
    int homeSteals;
    int homeBlocks;
    int homeTurnovers;
    int awayRebounds;
    int awaySteals;
    int awayBlocks;
    int awayTurnovers;
    String status;
    String clock;
    int quarter;
    ArrayList<Integer> homeQuaters;
    ArrayList<Integer> awayQuaters;

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

    public Game(String id, String home, String away, String time, int homeScore, int awayScore,
                String homeId, String awayId, String status, String clock, int quarter) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.time = time;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeId = homeId;
        this.awayId = awayId;
        this.status = status;
        this.clock = clock;
        this.quarter = quarter;
    }

    public Game(String id, String home, String away, String time, int homeScore, int awayScore,
                String homeId, String awayId, int homeRebounds, int homeSteals, int homeBlocks,
                int homeTurnovers, int awayRebounds, int awaySteals, int awayBlocks, int awayTurnovers,
                String status, String clock, int quarter, ArrayList<Integer> homeQuaters, ArrayList<Integer> awayQuaters) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.time = time;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeId = homeId;
        this.awayId = awayId;
        this.homeRebounds = homeRebounds;
        this.homeSteals = homeSteals;
        this.homeBlocks = homeBlocks;
        this.homeTurnovers = homeTurnovers;
        this.awayRebounds = awayRebounds;
        this.awaySteals = awaySteals;
        this.awayBlocks = awayBlocks;
        this.awayTurnovers = awayTurnovers;
        this.status = status;
        this.clock = clock;
        this.quarter = quarter;
        this.homeQuaters = homeQuaters;
        this.awayQuaters = awayQuaters;
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object game) {
        if (getClass() != game.getClass()) {
            return false;
        }
        return id.equals(((Game)game).getId());
    }

    protected Game(Parcel in) {
        id = in.readString();
        home = in.readString();
        away = in.readString();
        time = in.readString();
        homeScore = in.readInt();
        awayScore = in.readInt();
        homeId = in.readString();
        awayId = in.readString();
        homeRebounds = in.readInt();
        homeSteals = in.readInt();
        homeBlocks = in.readInt();
        homeTurnovers = in.readInt();
        awayRebounds = in.readInt();
        awaySteals = in.readInt();
        awayBlocks = in.readInt();
        awayTurnovers = in.readInt();
        status = in.readString();
        clock = in.readString();
        quarter = in.readInt();
        homeQuaters = new ArrayList<>();
        homeQuaters.add(in.readInt());
        homeQuaters.add(in.readInt());
        homeQuaters.add(in.readInt());
        homeQuaters.add(in.readInt());
        awayQuaters = new ArrayList<>();
        awayQuaters.add(in.readInt());
        awayQuaters.add(in.readInt());
        awayQuaters.add(in.readInt());
        awayQuaters.add(in.readInt());
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
        dest.writeString(homeId);
        dest.writeString(awayId);
        dest.writeInt(homeRebounds);
        dest.writeInt(homeSteals);
        dest.writeInt(homeBlocks);
        dest.writeInt(homeTurnovers);
        dest.writeInt(awayRebounds);
        dest.writeInt(awaySteals);
        dest.writeInt(awayBlocks);
        dest.writeInt(awayTurnovers);
        dest.writeString(status);
        dest.writeString(clock);
        dest.writeInt(quarter);
        for (Integer i : homeQuaters) {
            dest.writeInt(i);
        }
        for (Integer i : awayQuaters) {
            dest.writeInt(i);
        }
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