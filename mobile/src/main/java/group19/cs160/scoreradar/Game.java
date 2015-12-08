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
    int homeAssists;
    int awayRebounds;
    int awaySteals;
    int awayBlocks;
    int awayTurnovers;
    int awayAssists;
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

    public Game(String id, String home, String away, String time, String homeId, String awayId) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.time = time;
        this.homeId = homeId;
        this.awayId = awayId;
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
                int homeTurnovers, int homeAssists, int awayRebounds, int awaySteals, int awayBlocks,
                int awayTurnovers, int awayAssists, String status, String clock, int quarter,
                ArrayList<Integer> homeQuaters, ArrayList<Integer> awayQuaters) {
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
        this.homeAssists = homeAssists;
        this.awayRebounds = awayRebounds;
        this.awaySteals = awaySteals;
        this.awayBlocks = awayBlocks;
        this.awayTurnovers = awayTurnovers;
        this.awayAssists = awayAssists;
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

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getAwayId() {
        return awayId;
    }

    public void setAwayId(String awayId) {
        this.awayId = awayId;
    }

    public int getHomeRebounds() {
        return homeRebounds;
    }

    public void setHomeRebounds(int homeRebounds) {
        this.homeRebounds = homeRebounds;
    }

    public int getHomeSteals() {
        return homeSteals;
    }

    public void setHomeSteals(int homeSteals) {
        this.homeSteals = homeSteals;
    }

    public int getHomeBlocks() {
        return homeBlocks;
    }

    public void setHomeBlocks(int homeBlocks) {
        this.homeBlocks = homeBlocks;
    }

    public int getHomeTurnovers() {
        return homeTurnovers;
    }

    public void setHomeTurnovers(int homeTurnovers) {
        this.homeTurnovers = homeTurnovers;
    }

    public int getHomeAssists() {
        return homeAssists;
    }

    public void setHomeAssists(int homeAssists) {
        this.homeAssists = homeAssists;
    }

    public int getAwayRebounds() {
        return awayRebounds;
    }

    public void setAwayRebounds(int awayRebounds) {
        this.awayRebounds = awayRebounds;
    }

    public int getAwaySteals() {
        return awaySteals;
    }

    public void setAwaySteals(int awaySteals) {
        this.awaySteals = awaySteals;
    }

    public int getAwayBlocks() {
        return awayBlocks;
    }

    public void setAwayBlocks(int awayBlocks) {
        this.awayBlocks = awayBlocks;
    }

    public int getAwayTurnovers() {
        return awayTurnovers;
    }

    public void setAwayTurnovers(int awayTurnovers) {
        this.awayTurnovers = awayTurnovers;
    }

    public int getAwayAssists() {
        return awayAssists;
    }

    public void setAwayAssists(int awayAssists) {
        this.awayAssists = awayAssists;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public ArrayList<Integer> getHomeQuaters() {
        return homeQuaters;
    }

    public void setHomeQuaters(ArrayList<Integer> homeQuaters) {
        this.homeQuaters = homeQuaters;
    }

    public ArrayList<Integer> getAwayQuaters() {
        return awayQuaters;
    }

    public void setAwayQuaters(ArrayList<Integer> awayQuaters) {
        this.awayQuaters = awayQuaters;
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
        return id.equals(((Game) game).getId());
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
        homeAssists = in.readInt();
        awayRebounds = in.readInt();
        awaySteals = in.readInt();
        awayBlocks = in.readInt();
        awayTurnovers = in.readInt();
        awayAssists = in.readInt();
        status = in.readString();
        clock = in.readString();
        quarter = in.readInt();
        if (in.readByte() == 0x01) {
            homeQuaters = new ArrayList<Integer>();
            in.readList(homeQuaters, Integer.class.getClassLoader());
        } else {
            homeQuaters = null;
        }
        if (in.readByte() == 0x01) {
            awayQuaters = new ArrayList<Integer>();
            in.readList(awayQuaters, Integer.class.getClassLoader());
        } else {
            awayQuaters = null;
        }
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
        dest.writeInt(homeAssists);
        dest.writeInt(awayRebounds);
        dest.writeInt(awaySteals);
        dest.writeInt(awayBlocks);
        dest.writeInt(awayTurnovers);
        dest.writeInt(awayAssists);
        dest.writeString(status);
        dest.writeString(clock);
        dest.writeInt(quarter);
        if (homeQuaters == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(homeQuaters);
        }
        if (awayQuaters == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(awayQuaters);
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