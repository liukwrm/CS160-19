package group19.cs160.scoreradar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;


public class TempActivity extends AppCompatActivity {

    ArrayList<Game> listOfGames = new ArrayList<Game>();
    ArrayList<TeamInformation> listOfTeams = new ArrayList<TeamInformation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Intent intent = getIntent();
        listOfTeams = intent.getParcelableArrayListExtra("teams");
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        listOfGames = intent.getParcelableArrayListExtra("games");
        Intent newIntent = new Intent(this, SportActivity.class);
        newIntent.putParcelableArrayListExtra("games", listOfGames);
        newIntent.putParcelableArrayListExtra("teams", listOfTeams);
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
