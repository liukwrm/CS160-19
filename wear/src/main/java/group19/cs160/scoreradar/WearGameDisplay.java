package group19.cs160.scoreradar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class WearGameDisplay extends View {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

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

    public WearGameDisplay(Context context) {
        super(context);
        additionalInit();
        init(null, 0);
    }

    public WearGameDisplay(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        additionalInit();
        init(attrs, 0);
    }

    public WearGameDisplay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        additionalInit();
        init(attrs, 0);
    }

    public void additionalInit() {
        mHomeScore = (TextView) findViewById(R.id.score1);
        mAwayScore = (TextView) findViewById(R.id.score2);
        mTime = (TextView) findViewById(R.id.time);
        mHomeLogo = (ImageView) findViewById(R.id.team1logo);
        mAwayLogo = (ImageView) findViewById(R.id.team2logo);

    }

    public String getIdValue() {
        return id;
    }

    public void setIdValue(String id) {
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
        mTime.setText(this.time);
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
        mAwayScore.setText(this.awayScore);
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
        mHomeScore = (TextView) findViewById(R.id.score1);
        mHomeScore.setText(homeScore);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

    }

    private void invalidateTextPaintAndMeasurements() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce

    }

}