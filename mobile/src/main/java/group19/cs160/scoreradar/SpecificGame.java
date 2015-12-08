package group19.cs160.scoreradar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by liukwarm on 12/7/15.
 */
public class SpecificGame extends AppCompatActivity {

    Game game;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_game);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        game = intent.getParcelableExtra("game");
        ImageView logo1 = (ImageView) findViewById(R.id.logo1);
        logo1.setImageResource(GameInformation.getLogo(game.getHome()));
        TextView name1 = (TextView) findViewById(R.id.name1);
        name1.setText("" + game.getHome());
        TextView first1 = (TextView) findViewById(R.id.first1);
        first1.setText("" + game.homeQuaters.get(0));
        TextView second1 = (TextView) findViewById(R.id.second1);
        second1.setText("" + game.homeQuaters.get(1));
        TextView third1 = (TextView) findViewById(R.id.third1);
        third1.setText("" + game.homeQuaters.get(2));
        TextView fourth1 = (TextView) findViewById(R.id.fourth1);
        fourth1.setText("" + game.homeQuaters.get(3));
        TextView total1 = (TextView) findViewById(R.id.total1);
        total1.setText("" + game.getHomeScore());
        TextView name1a = (TextView) findViewById(R.id.name1a);
        name1a.setText("" + game.getHome());
        TextView rebounds1 = (TextView) findViewById(R.id.rebounds1);
        rebounds1.setText("" + game.homeRebounds);
        TextView assists1 = (TextView) findViewById(R.id.assists1);
        assists1.setText("" + game.getHome());
        TextView blocks1 = (TextView) findViewById(R.id.blocks1);
        blocks1.setText("" + game.homeBlocks);
        TextView steals1 = (TextView) findViewById(R.id.steals1);
        steals1.setText("" + game.homeSteals);
        TextView turnovers1 = (TextView) findViewById(R.id.turnovers1);
        turnovers1.setText("" + game.homeTurnovers);
        ImageView logo2 = (ImageView) findViewById(R.id.logo2);
        logo2.setImageResource(GameInformation.getLogo(game.getAway()));
        TextView name2 = (TextView) findViewById(R.id.name2);
        name2.setText("" + game.getAway());
        TextView first2 = (TextView) findViewById(R.id.first2);
        first2.setText("" + game.homeQuaters.get(0));
        TextView second2 = (TextView) findViewById(R.id.second2);
        second2.setText("" + game.homeQuaters.get(1));


        TextView third2 = (TextView) findViewById(R.id.third2);
        third2.setText("" + game.homeQuaters.get(2));
        TextView fourth2 = (TextView) findViewById(R.id.fourth2);
        fourth2.setText("" + game.homeQuaters.get(3));
        TextView total2 = (TextView) findViewById(R.id.total2);
        total2.setText("" + game.getAwayScore());
        TextView name2a = (TextView) findViewById(R.id.name2a);
        name2a.setText("" + game.getAway());


        TextView rebounds2 = (TextView) findViewById(R.id.rebounds2);
        rebounds2.setText("" + game.awayRebounds);
        TextView assists2 = (TextView) findViewById(R.id.assists2);
        assists2.setText("" + game.getAway());





        TextView blocks2 = (TextView) findViewById(R.id.blocks2);
        blocks2.setText("" + game.awayBlocks);
        TextView steals2 = (TextView) findViewById(R.id.steals2);
        steals2.setText("" + game.awaySteals);
        TextView turnovers2 = (TextView) findViewById(R.id.turnovers2);
        turnovers2.setText("" + game.awayTurnovers);


    }
}
