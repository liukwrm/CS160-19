package group19.cs160.scoreradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import pl.tajchert.buswear.EventBus;

/**
 * Created by liukwarm on 12/7/15.
 */
public class HomeActivity extends AppCompatActivity {

    ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();
    ArrayList<Game> listOfGames = new ArrayList<Game>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<String> followingGames;
    ArrayList<String> followingTeams;
    private Toolbar toolbar;
    HashSet<String> hashGames;

    HashMap<String, Integer> gamesMap;
    int countGames = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        listOfTeams = intent.getParcelableArrayListExtra("teams");
        listOfGames = intent.getParcelableArrayListExtra("games");
        followingGames = intent.getStringArrayListExtra("myGames");
        followingTeams = intent.getStringArrayListExtra("myTeams");

        ArrayList<Game> myGames = new ArrayList<>();
        //FILTERING
        for (Game g: listOfGames) {
            if (followingGames.contains(g.getId()) || followingTeams.contains(g.getAwayId()) || followingTeams.contains(g.getHomeId())) {
                myGames.add(g);
            }
        }
        if (myGames.size() == 0) {
            TextView title = (TextView)findViewById(R.id.hint);
            title.setText("Select a sport to begin following!");
        } else {
            TextView title = (TextView)findViewById(R.id.hint);
            title.setText("You are currently following...");
        }
        hashGames = new HashSet<>();
        for (String g : followingGames) {
            hashGames.add(g);
        }

        gamesMap = new HashMap<String, Integer>();
        for (int i = 0; i < listOfGames.size(); i++) {
            gamesMap.put(listOfGames.get(i).getId(), i);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GameAdapter(myGames, hashGames, mRecyclerView, false);
        mRecyclerView.setAdapter(mAdapter);

        ImageButton basketball = (ImageButton) findViewById(R.id.basketball);
        basketball.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                basketball();
            }
        });
        basketball.setBackground(null);

        ImageButton baseball = (ImageButton) findViewById(R.id.baseball);
        baseball.setBackground(null);
        ImageButton football = (ImageButton) findViewById(R.id.football);
        football.setBackground(null);
        ImageButton soccer = (ImageButton) findViewById(R.id.soccer);
        soccer.setBackground(null);

        sendData();

        // Sends Notification to watch upon launch
        Gson gson = new Gson();
        Type listOfObject = new TypeToken<ArrayList<Game>>(){}.getType();

        String json = gson.toJson(myGames, listOfObject);
        EventBus.getDefault().post("WearUpdate" + json, this);

        EventBus.getDefault().register(this);
    }

    public void basketball() {
        Intent newIntent = new Intent(this, SportActivity.class);
        newIntent.putParcelableArrayListExtra("games", listOfGames);
        newIntent.putParcelableArrayListExtra("teams", listOfTeams);

        newIntent.putStringArrayListExtra("myTeams", followingTeams);
        newIntent.putStringArrayListExtra("myGames", followingGames);
        startActivity(newIntent);
    }


    public void sendData() {
        Gson gson = new Gson();
        Type listOfTemp = new TypeToken<ArrayList<Game>>(){}.getType();
        ArrayList<Game> myGames = new ArrayList<>();
        //FILTERING
        for (Game g: listOfGames) {
            if (followingGames.contains(g.getId()) || followingTeams.contains(g.getAwayId()) || followingTeams.contains(g.getHomeId())) {
                myGames.add(g);
            }
        }
        String json = gson.toJson(myGames, listOfTemp);
        EventBus.getDefault().post("WearActivity" + json, this);
    }


    public void onEvent(Integer i) {
        countGames = i;
    }

    public void onEvent(Game game) {
        listOfGames.set(gamesMap.get(game.getId()), game);
        countGames -= 1;
        if (countGames == 0) {
            Gson gson = new Gson();
            Type listOfObject = new TypeToken<ArrayList<Game>>(){}.getType();
            ArrayList<Game> myGames = new ArrayList<>();
            //FILTERING
            for (Game g: listOfGames) {
                if (followingGames.contains(g.getId()) || followingTeams.contains(g.getAwayId()) || followingTeams.contains(g.getHomeId())) {
                    myGames.add(g);
                }
            }
            String json = gson.toJson(myGames, listOfObject);
            EventBus.getDefault().post("WearUpdate" + json, this);
        }
    }

    public void onEvent (String text) {
        if (text.startsWith("GameSend")) {
            text = text.substring(8);
            Gson gson = new Gson();
            Game game = gson.fromJson(text, Game.class);
            Intent intent = new Intent(this, SpecificGame.class);
            intent.putExtra("game", game);
            startActivity(intent);
        }
        if (text.startsWith("game sub")){
            try {
                JSONArray temp = new JSONArray(text.substring(8, text.length()));
                ArrayList<String> newGames = new ArrayList<>();
                for(int i = 0; i < temp.length(); i++) {
                    newGames.add(temp.getString(i));
                }
                followingGames = newGames;
                ArrayList<Game> myGames = new ArrayList<>();
                for (Game g: listOfGames) {
                    if (followingGames.contains(g.getId()) || followingTeams.contains(g.getAwayId()) || followingTeams.contains(g.getHomeId())) {
                        myGames.add(g);
                    }
                }
                if (myGames.size() == 0) {
                    TextView title = (TextView)findViewById(R.id.hint);
                    title.setText("Select a sport to begin following!");
                } else {
                    TextView title = (TextView)findViewById(R.id.hint);
                    title.setText("You are currently following...");
                }
                hashGames = new HashSet<>();
                for (String g : followingGames) {
                    hashGames.add(g);
                }

                mRecyclerView.swapAdapter(new GameAdapter(myGames, hashGames, mRecyclerView, false), false);
            } catch(JSONException e) {

            }
        }
        if (text.startsWith("team sub")){
            try {
                JSONArray temp = new JSONArray(text.substring(8, text.length()));
                ArrayList<String> newTeams = new ArrayList<>();
                for(int i = 0; i < temp.length(); i++) {
                    newTeams.add(temp.getString(i));
                    Log.d("TEAMSUB", temp.getString(i));
                }
                followingTeams = newTeams;
                ArrayList<Game> myGames = new ArrayList<>();
                for (Game g: listOfGames) {
                    if (followingGames.contains(g.getId()) || followingTeams.contains(g.getAwayId()) || followingTeams.contains(g.getHomeId())) {
                        myGames.add(g);
                    }
                }
                if (myGames.size() == 0) {
                    TextView title = (TextView)findViewById(R.id.hint);
                    title.setText("Select a sport to begin following!");
                } else {
                    TextView title = (TextView)findViewById(R.id.hint);
                    title.setText("You are currently following...");
                }
                hashGames = new HashSet<>();
                for (String g : followingGames) {
                    hashGames.add(g);
                }

                mRecyclerView.swapAdapter(new GameAdapter(myGames, hashGames, mRecyclerView, false), false);
            } catch(JSONException e) {

            }

        }
    }


}
