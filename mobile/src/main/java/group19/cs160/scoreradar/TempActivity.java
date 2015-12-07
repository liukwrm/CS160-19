package group19.cs160.scoreradar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class TempActivity extends AppCompatActivity {

    ArrayList<Game> listOfGames = new ArrayList<Game>();
    ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();
    ArrayList<String> followingGames;
    ArrayList<String> followingTeams;

    private static final String GAMESPATH = "games";
    private static final String TEAMSPATH = "teams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        setUpPreferences();

        Intent intent = getIntent();
        listOfTeams = intent.getParcelableArrayListExtra("teams");
    }

    public static String getTeamsPath() {
        return TEAMSPATH;
    }


    private void setUpPreferences() {
        followingGames = new ArrayList<>();
        followingTeams = new ArrayList<>();
        try {
            FileInputStream gameFile = openFileInput(GAMESPATH);
            int i = 0;
            char c;
            String temp = "";
            while((i=gameFile.read())!=-1)
            {
                // converts integer to character
                c=(char)i;
                temp += c;
            }
            Log.d("SUBSCRIBE", temp);
            try {
                JSONArray games = new JSONArray(temp);

                for(int j = 0; j < games.length(); j++) {
                    followingGames.add(games.getString(j));
                }
            } catch (JSONException e) {

            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }


        try {
            FileInputStream teamFile = openFileInput(TEAMSPATH);
            int i = 0;
            String temp = "";
            char c;
            while ((i = teamFile.read()) != -1) {
                // converts integer to character
                c = (char) i;
                temp += c;
            }
            Log.d("SUBSCRIBE", temp);
            try {
                JSONArray teams = new JSONArray(temp);
                for (int j = 0; j < teams.length(); j++) {
                    followingTeams.add(teams.getString(j));
                }
            } catch (JSONException e) {

            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        listOfGames = intent.getParcelableArrayListExtra("games");
        Intent newIntent = new Intent(this, SportActivity.class);
        newIntent.putParcelableArrayListExtra("games", listOfGames);
        newIntent.putParcelableArrayListExtra("teams", listOfTeams);

        newIntent.putStringArrayListExtra("myTeams", followingTeams);
        newIntent.putStringArrayListExtra("myGames", followingGames);
        startActivity(newIntent);
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


}
