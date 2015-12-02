package group19.cs160.scoreradar;

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
import android.widget.EditText;

import com.google.android.gms.games.Games;

import java.util.ArrayList;
import java.util.List;

import group19.cs160.scoreradar.Json.ScheduleParser;
import group19.cs160.scoreradar.Pojo.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    public void go(View view){
        int year = Integer.valueOf(((EditText) findViewById(R.id.year)).getText().toString());
        int month = Integer.valueOf(((EditText) findViewById(R.id.month)).getText().toString());
        int day = Integer.valueOf(((EditText) findViewById(R.id.day)).getText().toString());
        getScheduledGames(year, month, day);

    }

    public void getScheduledGames(Integer year, Integer month, Integer day) {
        String url = "http://api.sportradar.us/nba-t3/games/" + String.valueOf(year) + "/" +
        String.valueOf(month) + "/" + String.valueOf(day) + "/schedule.json?api_key=kcrfkb6hwmfqzecw76tgxepp";
        ScheduleParser scheduleParser = new ScheduleParser(url);
        scheduleParser.getGames();
    }
}
