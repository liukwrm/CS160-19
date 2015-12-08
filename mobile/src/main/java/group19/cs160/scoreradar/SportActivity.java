package group19.cs160.scoreradar;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import pl.tajchert.buswear.EventBus;

public class SportActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient googleClient;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();
    ArrayList<Game> listOfGames = new ArrayList<Game>();

    ArrayList<String> followingGames;
    ArrayList<String> followingTeams;
//    boolean getTeamsListDone = false;
//    boolean getGamesListDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Build a new GoogleApiClient for the Wearable API
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        Intent intent = getIntent();
        listOfTeams = intent.getParcelableArrayListExtra("teams");
        listOfGames = intent.getParcelableArrayListExtra("games");
        followingGames = intent.getStringArrayListExtra("myGames");
        followingTeams = intent.getStringArrayListExtra("myTeams");

        //Sort based on alphabetical order

        TeamInformation[] tempTeams = listOfTeams.toArray(new TeamInformation[listOfTeams.size()]);
//        Game[] tempGames = listOfGames.toArray(new Game[listOfGames.size()]);
        Arrays.sort(tempTeams, new TeamComparator());
        listOfTeams = new ArrayList<>(Arrays.asList(tempTeams));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        sendData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleClient.connect();
    }

    // Send a message when the data layer connection is successful.
    @Override
    public void onConnected(Bundle connectionHint) {
        String message = "Hello wearable\n Via the data layer";

        //Requires a new thread to avoid blocking the UI
        final PutDataMapRequest putRequest = PutDataMapRequest.create("/send_games");
        final DataMap map = putRequest.getDataMap();


        // new SendToDataLayerThread("/message_path", message).start();

//        PendingIntent resultPendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                new Intent(this, SportActivity.class),
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//        Notification notification = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.cast_ic_notification_on)
//                .setContentTitle("New Earthquake")
//                .setContentIntent(resultPendingIntent)
//                .setContentText(String.format("NEW SPORT INFO"))
//                .build();
//    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
//    int notificationId = 1;
//    notificationManager.notify(notificationId, notification);
    }

    // Disconnect from the data layer when the Activity stops
    @Override
    protected void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }

    // Placeholders for required connection callbacks
    @Override
    public void onConnectionSuspended(int cause) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }


    public class TeamComparator implements Comparator<TeamInformation> {
        public int compare(TeamInformation ti1, TeamInformation ti2) {
            if (ti1.getName().compareTo(ti2.getName()) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void sendData() {
        Gson gson = new Gson();
        Type listOfTemp = new TypeToken<ArrayList<Game>>(){}.getType();
        String json = gson.toJson(listOfGames, listOfTemp);
        EventBus.getDefault().post("WearActivity" + json, this);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("games", listOfGames);
        bundle.putStringArrayList("myGames", followingGames);
        GameTab game = new GameTab();
        game.setArguments(bundle);
        adapter.addFragment(game, "game");

        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList("teams", listOfTeams);
        bundle2.putStringArrayList("myTeams", followingTeams);
        TeamTab team = new TeamTab();
        team.setArguments(bundle2);
        adapter.addFragment(team, "team");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendToDataLayerThread(String p, String msg) {
            path = p;
            Gson gson = new Gson();
            String json = gson.toJson(listOfGames);
            Log.v("in sendToData", json);
            message = json;
            Type listOfTemp = new TypeToken<ArrayList<Game>>(){}.getType();
            String json2 = gson.toJson(listOfGames, listOfTemp);
            ArrayList<Game> temp = gson.fromJson(json2, listOfTemp);
            Log.v("temp json", temp.get(0).toString());
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                }
                else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send Message");
                }
            }
        }


    }
}