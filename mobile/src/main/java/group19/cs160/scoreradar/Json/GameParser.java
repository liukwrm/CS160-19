package group19.cs160.scoreradar.Json;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import group19.cs160.scoreradar.Pojo.Game;

/**
 * Created by Michael on 11/19/2015.
 */
public class GameParser {

    String url;

    public GameParser(String url) {
        this.url = url;
    }

    public void getGame() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Game game = new Game();
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    String status = jsonObject.getString("status");
                    String time = jsonObject.getString("scheduled");
                    String id = jsonObject.getString("id");
                    JSONObject jsonhome = jsonObject.getJSONObject("home");
                    JSONObject jsonaway = jsonObject.getJSONObject("away");
                    String home = jsonhome.getString("market") + " " + jsonhome.getString("name");
                    String away = jsonaway.getString("market") + " " + jsonaway.getString("name");
                    int homescore = jsonhome.getInt("points");
                    int awayscore = jsonaway.getInt("points");
                    game = new Game(id, home, away, time, homescore, awayscore, status);
                } catch (JSONException e){
                }
                Log.d("stats", game.getHome() + " vs " + game.getAway() + " is currently " + game.getStatus() +
                ". The score " + game.getHomeScore() + " to " + game.getAwayScore());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
