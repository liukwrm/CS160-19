package group19.cs160.scoreradar.Json;

import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import group19.cs160.scoreradar.MainActivity;
import group19.cs160.scoreradar.Pojo.Game;

/**
 * Created by Michael on 11/19/2015.
 */
public class ScheduleParser {

    String url;

    public ScheduleParser(String url) {
        this.url = url;
    }

    public ArrayList<Game> getGames() {

        final ArrayList<Game> games = new ArrayList<Game>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return games;
    }
}
