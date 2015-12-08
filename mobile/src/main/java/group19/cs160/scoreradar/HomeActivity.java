package group19.cs160.scoreradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
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
            if (followingGames.contains(g.getId()) || followingTeams.contains(g.getAway()) || followingTeams.contains(g.getHome())) {
                myGames.add(g);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GameAdapter(myGames, null, mRecyclerView, false);
        mRecyclerView.setAdapter(mAdapter);

        ImageButton basketball = (ImageButton) findViewById(R.id.basketball);
        basketball.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                basketball();
            }
        });


    }

    public void basketball() {
        Intent newIntent = new Intent(this, SportActivity.class);
        newIntent.putParcelableArrayListExtra("games", listOfGames);
        newIntent.putParcelableArrayListExtra("teams", listOfTeams);

        newIntent.putStringArrayListExtra("myTeams", followingTeams);
        newIntent.putStringArrayListExtra("myGames", followingGames);
        startActivity(newIntent);
    }

}
