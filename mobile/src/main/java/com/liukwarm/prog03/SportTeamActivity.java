package com.liukwarm.prog03;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by liukwarm on 11/19/15.
 */
public class SportTeamActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_team);

        ImageButton button = (ImageButton) findViewById(R.id.top);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                top();
            }
        });
        ImageButton button2 = (ImageButton) findViewById(R.id.bottom);
        button2.setBackgroundColor(Color.TRANSPARENT);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bottom();
            }
        });
    }

    public void top() {
        Intent intent = new Intent(this, SpecificGameActivity.class);
        startActivity(intent);
    }

    public void bottom() {
        Intent intent = new Intent(this, SpecificGameActivity.class);
        startActivity(intent);
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
