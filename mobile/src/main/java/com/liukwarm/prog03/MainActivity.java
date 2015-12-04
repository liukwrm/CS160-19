package com.liukwarm.prog03;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent intent = new Intent(this, SportGameActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);
        Log.d("Movement", "Made motion");
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d("Movement", "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d("Movement","Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d("Movement","Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d("Movement", "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d("Movement","Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
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
