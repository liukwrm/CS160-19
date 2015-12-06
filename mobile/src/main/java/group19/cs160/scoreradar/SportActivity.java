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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    final ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();
    ArrayList<Game> listOfGames = new ArrayList<Game>();
    boolean getTeamsListDone = false;
    boolean getGamesListDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        ArrayList<Game> games = intent.getParcelableArrayListExtra("games");
        getTeamsList();
        parseScheduledGames(games);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GameTab(), "game");
        adapter.addFragment(new TeamTab(), "sport");
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

    public void parseScheduledGames(final ArrayList<Game> games) {
        listOfGames = new ArrayList<Game>();
        for (Game g : games) {
            try {
                if (games.indexOf(g) == 0) {
                    Thread.sleep(3000);
                } else {
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String url = "http://api.sportradar.us/nba-t3/games/" + g.getId() + "/summary.json?api_key=kcrfkb6hwmfqzecw76tgxepp";

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
                        String id = jsonObject.getString("id");
                        JSONObject jsonHome = jsonObject.getJSONObject("home");
                        JSONObject jsonAway = jsonObject.getJSONObject("away");
                        String home = jsonHome.getString("market") + " " + jsonHome.getString("name");
                        String away = jsonAway.getString("market") + " " + jsonAway.getString("name");
                        int homeScore = jsonHome.getInt("points");
                        int awayScore = jsonAway.getInt("points");
                        game = new Game(id, home, away, time, homeScore, awayScore, status);
                        listOfGames.add(game);
                    } catch (JSONException e){
                    }
                    if (listOfGames.size() == games.size()) {
                        getGamesListDone = true;
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("main", "GOT HERE" + statusCode);
                }
            });
        }
    }

    public void getTeamsList() {
        String url = "http://api.sportradar.us/nba-t3/seasontd/2015/reg/standings.json?api_key=kcrfkb6hwmfqzecw76tgxepp";

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
                getTeamsListDone = true;
                for (TeamInformation teamInformation : listOfTeams) {
                    Log.d("TEAMS", teamInformation.getName() + " is " + String.valueOf(teamInformation.getWins()) + " " +
                    String.valueOf(teamInformation.getLosses()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}