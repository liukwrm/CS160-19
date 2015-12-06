package group19.cs160.scoreradar;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.ImageView;
import android.widget.TextView;

public class WearGame extends Activity {

    private TextView mTeam1Score;
    private TextView mTeam2Score;
    private TextView mTime;
    private ImageView mTeam1Logo;
    private ImageView mTeam2Logo;
    private int score1;
    private int score2;
    private String team1;
    private String team2;
    private String time;

    public WearGame(int score1, int score2, String team1, String team2, String time) {

        this.score1 = score1;
        this.score2 = score2;
        this.team1 = team1;
        this.team2 = team2;
        this.time = time;

        mTeam1Score = (TextView) findViewById(R.id.score1);
        mTeam2Score = (TextView) findViewById(R.id.score2);
        mTime = (TextView) findViewById(R.id.time);
        mTeam1Logo = (ImageView) findViewById(R.id.team1logo);
        mTeam2Logo = (ImageView) findViewById(R.id.team2logo);


        mTeam1Score.setText(this.score1);
        mTeam2Score.setText(this.score2);
        mTime.setText(this.time);
        // Get Logos


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
}
