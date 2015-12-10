package group19.cs160.scoreradar;

        import android.app.PendingIntent;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.app.Activity;
        import android.support.v4.app.NotificationCompat;
        import android.support.v4.app.NotificationManagerCompat;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.wearable.view.GridViewPager;
        import android.util.Log;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.lang.reflect.Type;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Locale;

import group19.cs160.scoreradar.Game;
import group19.cs160.scoreradar.GameAdapter;
import group19.cs160.scoreradar.GameFragment;
import group19.cs160.scoreradar.MainActivity;
import group19.cs160.scoreradar.R;
import pl.tajchert.buswear.EventBus;

public class NotificationActivity extends Activity implements GameFragment.OnFragmentInteractionListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    public static ArrayList<Game> listOfGames = new ArrayList<>();
    private GridViewPager mGamesView;
    private GameAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);

        EventBus.getDefault().register(this);

        // Handles notification transition
        Intent intent = getIntent();
        if (!intent.getParcelableArrayListExtra("gamesList").equals(null)) {
            ArrayList<Game> list = intent.getParcelableArrayListExtra("gamesList");
            Log.e("in wear/Main/Oncreate", "list: " + list.toString());
            if (!list.isEmpty()) {
                listOfGames = list;
                mGamesView = (GridViewPager) findViewById(R.id.pager);
                GameAdapter adapter = new GameAdapter(this, getFragmentManager(), listOfGames);
                mGamesView.setAdapter(adapter);
            }
        }


        // pull current games, get list. Iterate through and add each game into mGamesLayout

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void populateLinks(LinearLayout ll) {

        if (listOfGames.size() > 0) {
            LinearLayout llAlso = new LinearLayout(this);
            llAlso.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSample = new TextView(this);


            llAlso.addView(txtSample);
            txtSample.measure(0, 0);

            int widthSoFar = txtSample.getMeasuredWidth();
        }
    }

    public void onEventMainThread(String temp) {
        if (temp.startsWith("WearActivity")) {
            Gson gson = new Gson();
            Type listOfTemp = new TypeToken<ArrayList<Game>>() {
            }.getType();
            ArrayList<Game> tempGames = gson.fromJson(temp.substring(12), listOfTemp);
            Log.v("temp json", tempGames.get(0).toString());
            if (!tempGames.equals(listOfGames)) {
                System.out.println("In listOfGames = tempList");

                listOfGames = tempGames;
                mGamesView = (GridViewPager) findViewById(R.id.pager);
                GameAdapter adapter = new GameAdapter(this, getFragmentManager(), tempGames);
                mGamesView.setAdapter(adapter);
            }
        }
        if (temp.startsWith("WearUpdate")) {
            Log.d("in listener-onEvent", "Here in event for notification");
            temp = temp.substring(10);
            Gson gson = new Gson();
            Type listOfTemp = new TypeToken<ArrayList<Game>>() {
            }.getType();
            ArrayList<Game> gameList = gson.fromJson(temp, listOfTemp);
            Game first = gameList.get(0);


            int notificationId = 001;
            // Build intent for notification content
            Intent viewIntent = new Intent(this, MainActivity.class);
            viewIntent.putParcelableArrayListExtra("gamesList", gameList);
            PendingIntent viewPendingIntent =
                    PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.watch_basketball_bg_small))
                            .setContentTitle("New Score Update From ScoreRadar")
                            .setContentText(String.format(first.getHome() + ": " + first.getHomeScore() + "  " + first.getAway() + ": " + first.getAwayScore()))
                            .setContentIntent(viewPendingIntent)
                            .addAction(R.mipmap.ic_launcher,
                                    "Opening ScoreRadar", viewPendingIntent);
            //.extend(new NotificationCompat.WearableExtender().setDisplayIntent(viewPendingIntent));

            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);

            // Build the notification and issues it with notification manager.
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }

}