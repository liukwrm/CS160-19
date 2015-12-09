package group19.cs160.scoreradar;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import pl.tajchert.buswear.EventBus;

/*
    1. Services need to be defined in the manifest.
     <service
            android:name=".TimeWatcherService"
            android:label="@string/app_name"/>
*/

public class ListenerService extends Service {

    private static final int INTERVAL = 300000;
    // 300000
    private static final int SECOND = 1000;
    private int prevYear = 0;
    private int prevMonth = 0;
    private int prevDay = 0;
    private HashMap<String, Boolean> games;

    private static final String KEY = "zpejx57rrt5xe5n97j5umymz";
    private static final String testKey1 = "bwewwvxt38nk63z7dmztjcfq";
    private static final String testKey2 = "wuvhhw5at85jukehsv94vyuv";
    private static final String testKey3 = "d7vhqx9y75hfz3u5dtqsa3wm";
    private static final String testKey4 = "ejhpzjnxy5j283bz488g4xtp";
    private LinkedList<String> keys = new LinkedList<String>();
    private String curKey;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Kick off new work to do
        createAndStartTimer();
        return START_STICKY;
    }


    private void createAndStartTimer() {
        /* Ask the Time API "What time is it?" at the end of INTERVAL.
         * ----------------------
         * Time API : GET http://www.timeapi.org/pdt/now
         * Returns text e.g. 2015-10-08T19:33:33-07:00
         *
         */
        CountDownTimer timer = new CountDownTimer(INTERVAL, SECOND) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);

                keys.add(KEY);
                keys.add(testKey1);
                keys.add(testKey2);
                keys.add(testKey3);
                keys.add(testKey4);
                curKey = keys.getFirst();

                if (day != prevDay) {
                    prevYear = year;
                    prevMonth = month;
                    prevDay = day;
                    String url = "http://api.sportradar.us/nba-t3/games/" + String.valueOf(year) + "/" +
                            String.valueOf(month) + "/" + String.valueOf(day) + "/schedule.json?api_key=" + curKey;
                    keys.addLast(keys.removeFirst());
                    curKey = keys.getFirst();

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            // called before request is started
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            games = new HashMap<String, Boolean>();
                            try {
                                JSONArray jsonArray = new JSONObject(new String(response)).getJSONArray("games");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    games.put(id, true);
                                }
                            } catch (JSONException e){
                            }
                            checkScores();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            // called when request is retried
                        }
                    });
                }
                checkScores();

                // Start the timer again
                createAndStartTimer();
            }
        };

        timer.start();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void checkScores() {
        EventBus.getDefault().postLocal(Collections.frequency(new ArrayList<Boolean>(games.values()), true));
        for (final String id : games.keySet()) {
            if (games.get(id)) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String url = "http://api.sportradar.us/nba-t3/games/" + id + "/summary.json?api_key=" + curKey;
                keys.addLast(keys.removeFirst());
                curKey = keys.getFirst();

                Log.d("main", url);

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Game game = new Game();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(responseBody));
                            String status = jsonObject.getString("status");
                            String time = jsonObject.getString("scheduled");
                            String clock = jsonObject.getString("clock");
                            int quarter = jsonObject.getInt("quarter");
                            String id = jsonObject.getString("id");
                            JSONObject jsonHome = jsonObject.getJSONObject("home");
                            JSONObject jsonAway = jsonObject.getJSONObject("away");
                            String home = jsonHome.getString("market") + " " + jsonHome.getString("name");
                            String away = jsonAway.getString("market") + " " + jsonAway.getString("name");
                            int homeScore = jsonHome.getInt("points");
                            int awayScore = jsonAway.getInt("points");
                            JSONArray homeScoring = jsonHome.getJSONArray("scoring");
                            JSONArray awayScoring = jsonAway.getJSONArray("scoring");
                            JSONObject homeStats = jsonHome.getJSONObject("statistics");
                            JSONObject awayStats = jsonAway.getJSONObject("statistics");
                            ArrayList<Integer> homeQuarters = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0));
                            ArrayList<Integer> awayQuarters = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0));
                            for (int i = 0; i < homeScoring.length(); i++) {
                                homeQuarters.set(i, homeScoring.getJSONObject(i).getInt("points"));
                                awayQuarters.set(i, awayScoring.getJSONObject(i).getInt("points"));
                            }
                            String homeId = jsonHome.getString("id");
                            String awayId = jsonAway.getString("id");
                            int homeRebounds = homeStats.getInt("rebounds");
                            int homeSteals = homeStats.getInt("steals");
                            int homeBlocks = homeStats.getInt("blocks");
                            int homeTurnovers = homeStats.getInt("turnovers");
                            int homeAssists = homeStats.getInt("assists");
                            int awayRebounds = awayStats.getInt("rebounds");
                            int awaySteals = awayStats.getInt("steals");
                            int awayBlocks = awayStats.getInt("blocks");
                            int awayTurnovers = awayStats.getInt("turnovers");
                            int awayAssists = awayStats.getInt("assists");
                            game = new Game(id, home,  away,  time,  homeScore,  awayScore,
                                    homeId,  awayId,  homeRebounds, homeSteals,  homeBlocks,
                                    homeTurnovers, homeAssists, awayRebounds, awaySteals, awayBlocks, awayTurnovers, awayAssists,
                                    status, clock, quarter, homeQuarters, awayQuarters);
                        } catch (JSONException e){
                        }
                        Log.d("stats", game.getHome() + " vs " + game.getAway() + " is currently " + game.getStatus() +
                                ". The score " + game.getHomeScore() + " to " + game.getAwayScore());
                        if (game.getStatus().equals("closed")) {
                            games.put(id, false);
                        }
                        EventBus.getDefault().postLocal(game);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("main", "GOT HERE" + statusCode);
                    }
                });
            }
        }
    }
}
