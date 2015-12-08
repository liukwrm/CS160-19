package group19.cs160.scoreradar;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import pl.tajchert.buswear.EventBus;

public class MainActivity extends AppCompatActivity {

    ArrayList<Game> listOfGames = new ArrayList<Game>();
    ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();
    static final String KEY = "uunmhwurg5munkwu3s59q9r8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        EventBus.getDefault().register(this);

        getTeamsList();

        listOfGames = new ArrayList<Game>();
        startSportActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTeamsList() {
        String url = "http://api.sportradar.us/nba-t3/seasontd/2015/reg/standings.json?api_key=" + KEY;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray conferences = new JSONObject(new String(responseBody)).getJSONArray("conferences");
                    for (int i = 0; i < conferences.length(); i++) {
                        JSONArray divisions = conferences.getJSONObject(i).getJSONArray("divisions");
                        for (int j = 0; j < divisions.length(); j++) {
                            JSONArray teams = divisions.getJSONObject(j).getJSONArray("teams");
                            for (int k = 0; k < teams.length(); k++) {
                                JSONObject team = teams.getJSONObject(k);
                                listOfTeams.add(new TeamInformation(team.getString("market") + " " + team.getString("name"),
                                        team.getInt("wins"), team.getInt("losses"), team.getString("id"), false));
                            }
                        }
                    }
                } catch (JSONException e){
                }
                for (TeamInformation teamInformation : listOfTeams) {
                    Log.d("TEAMS", teamInformation.getName() + " is " + String.valueOf(teamInformation.getWins()) + " " +
                            String.valueOf(teamInformation.getLosses()));
                }
                Intent intent = new Intent(MainActivity.this, TempActivity.class);
                intent.putParcelableArrayListExtra("teams", listOfTeams);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void startSportActivity() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = "http://api.sportradar.us/nba-t3/games/" + String.valueOf(year) + "/" +
                String.valueOf(month) + "/" + String.valueOf(day) + "/schedule.json?api_key=" + KEY;
        Log.d("main", url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ArrayList<Game> games = new ArrayList<Game>();
                try {
                    JSONArray jsonArray = new JSONObject(new String(responseBody)).getJSONArray("games");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String time = jsonObject.getString("scheduled");
                        String home = jsonObject.getJSONObject("home").getString("name");
                        String away = jsonObject.getJSONObject("away").getString("name");
                        games.add(new Game(id, home, away, time));
                    }
                } catch (JSONException e){
                }
                for (Game game : games){
                    Log.d("games", game.getHome() + " vs " + game.getAway() + " at " + game.getTime());
                    Log.d("id", game.getId());
                }
//                Intent intent = new Intent(MainActivity.this, SportActivity.class);
//                intent.putParcelableArrayListExtra("games", games);
//                startActivity(intent);
                getScores(games, 0, games.size());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void getScores(final ArrayList<Game> games, final int cur, final int total) {
        final Game game = games.get(cur);
        String url = "http://api.sportradar.us/nba-t3/games/" + game.getId() + "/summary.json?api_key=" + KEY;

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("main", url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Game g = new Game();
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    String status = jsonObject.getString("status");
                    game.setStatus(status);
                    if (status.equals("scheduled")) {
                        listOfGames.add(game);
                    } else {
                        String time = jsonObject.getString("scheduled");
                        String id = jsonObject.getString("id");
                        JSONObject jsonHome = jsonObject.getJSONObject("home");
                        JSONObject jsonAway = jsonObject.getJSONObject("away");
                        String home = jsonHome.getString("market") + " " + jsonHome.getString("name");
                        String away = jsonAway.getString("market") + " " + jsonAway.getString("name");
                        int homeScore = jsonHome.getInt("points");
                        int awayScore = jsonAway.getInt("points");
                        g = new Game(id, home, away, time, homeScore, awayScore, status);
                        listOfGames.add(g);
                    }
                } catch (JSONException e){
                }
                if (cur == total - 1) {
                    Intent intent = new Intent(MainActivity.this, TempActivity.class);
                    intent.putParcelableArrayListExtra("games", listOfGames);
                    startActivity(intent);
                } else {
                    getScores(games, cur + 1, total);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("main", "GOT HERE" + statusCode);
            }
        });
    }

    public void onEvent(Game game){
        EventBus.getDefault().postRemote(game, this);
    }
}
