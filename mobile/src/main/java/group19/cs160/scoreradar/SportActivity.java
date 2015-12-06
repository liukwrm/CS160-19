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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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

    public void parseScheduledGames(ArrayList<Game> games) {
        for (Game g : games) {
            try {
                Thread.sleep(1500);
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
                    Log.d("main", "GOT HERE" + statusCode);
                }
            });
        }
    }
}