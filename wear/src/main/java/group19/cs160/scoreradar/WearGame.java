package group19.cs160.scoreradar;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.ImageView;
import android.widget.TextView;

public class WearGame extends Activity {

    private TextView mHomeScore;
    private TextView mAwayScore;
    private TextView mTime;
    private ImageView mHomeLogo;
    private ImageView mAwayLogo;
    private int homeScore;
    private int awayScore;
    private String Home;
    private String Away;
    private String time;
    private String id;
    private String status;

    public WearGame() { }

    public WearGame(String id, String Home, String Away, String time) {

        this.id = id;
        this.Home = Home;
        this.Away = Away;
        this.time = time;

    }

    public WearGame(String id, String Home, String Away, String time, int homeScore, int awayScore, String status) {

        this.id = id;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.Home = Home;
        this.Away = Away;
        this.time = time;
        this.status = status;

        mHomeScore = (TextView) findViewById(R.id.score1);
        mAwayScore = (TextView) findViewById(R.id.score2);
        mTime = (TextView) findViewById(R.id.time);
        mHomeLogo = (ImageView) findViewById(R.id.team1logo);
        mAwayLogo = (ImageView) findViewById(R.id.team2logo);


        mHomeScore.setText(this.homeScore);
        mAwayScore.setText(this.awayScore);
        mTime.setText(this.time);
        // Get Logos


    }

    protected void goToMobileGame() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_game);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

            }
        });


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHome() {
        return Home;
    }

    public void setHome(String home) {
        this.Home = home;
    }

    public String getAway() {
        return Away;
    }

    public void setAway(String away) {
        this.Away = away;
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
}
