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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
                if (day != prevDay) {
                    prevYear = year;
                    prevMonth = month;
                    prevDay = day;
                    String url = "http://api.sportradar.us/nba-t3/games/" + String.valueOf(year) + "/" +
                            String.valueOf(month) + "/" + String.valueOf(day) + "/schedule.json?api_key=kcrfkb6hwmfqzecw76tgxepp";

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
        for (final String id : games.keySet()) {
            if (games.get(id)) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String url = "http://api.sportradar.us/nba-t3/games/" + id + "/summary.json?api_key=kcrfkb6hwmfqzecw76tgxepp";

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
                            String homeId = jsonHome.getString("id");
                            String awayId = jsonAway.getString("id");
                            int homeScore = jsonHome.getInt("points");
                            int awayScore = jsonAway.getInt("points");
                            game = new Game(id, home, away, time, homeScore, awayScore, homeId, awayId, status, clock, quarter);
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