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
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

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
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.WearGameDisplay, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.WearGameDisplay_exampleString);
        mExampleColor = a.getColor(
                R.styleable.WearGameDisplay_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.WearGameDisplay_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.WearGameDisplay_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.WearGameDisplay_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();
    }

    private void invalidateTextPaintAndMeasurements() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce

    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
