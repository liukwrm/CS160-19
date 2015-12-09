package group19.cs160.scoreradar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import pl.tajchert.buswear.EventBus;

public class SportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();
    ArrayList<Game> listOfGames = new ArrayList<Game>();

    ArrayList<String> followingGames;
    ArrayList<String> followingTeams;

    HashMap<String, Integer> gamesMap;
    int countGames = 0;
//    boolean getTeamsListDone = false;
//    boolean getGamesListDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        gamesMap = new HashMap<String, Integer>();
        for (int i = 0; i < listOfGames.size(); i++) {
            gamesMap.put(listOfGames.get(i).getId(), i);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    public class TeamComparator implements Comparator<TeamInformation> {
        public int compare(TeamInformation ti1, TeamInformation ti2) {
            return ti1.getName().compareTo(ti2.getName());
        }
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

    public void onEvent(Integer i) {
        countGames = i;
    }

    public void onEvent(Game game) {
        listOfGames.set(gamesMap.get(game.getId()), game);
        countGames -= 1;
        if (countGames == 0) {
            Gson gson = new Gson();
            Type listOfObject = new TypeToken<ArrayList<Game>>(){}.getType();
            String json = gson.toJson(listOfGames, listOfObject);
            EventBus.getDefault().post("WearGames" + json, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}