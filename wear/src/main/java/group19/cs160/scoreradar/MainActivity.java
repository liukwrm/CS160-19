package group19.cs160.scoreradar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.usage.UsageEvents;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import pl.tajchert.buswear.EventBus;

public class MainActivity extends WearableActivity implements GameFragment.OnFragmentInteractionListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    public static ArrayList<Game> listOfGames = new ArrayList<>();
    private GridViewPager mGamesView;
    private GameAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);

        EventBus.getDefault().register(this);

        mGamesView = (GridViewPager) findViewById(R.id.pager);
        GameAdapter adapter = new GameAdapter(this, getFragmentManager(), listOfGames);
        mGamesView.setAdapter(adapter);

        // pull current games, get list. Iterate through and add each game into mGamesLayout

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void populateLinks(LinearLayout ll) {

        if (listOfGames.size() > 0) {
            LinearLayout llAlso = new LinearLayout(this);
            llAlso.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSample = new TextView(this);


            llAlso.addView(txtSample);
            txtSample.measure(0, 0);

            int widthSoFar = txtSample.getMeasuredWidth();
        }
    }

    public void onEventMainThread(String temp) {
        if (temp.startsWith("WearActivity")) {
            Gson gson = new Gson();
            Type listOfTemp = new TypeToken<ArrayList<Game>>() {
            }.getType();
            ArrayList<Game> tempGames = gson.fromJson(temp.substring(12), listOfTemp);
            Log.v("temp json", tempGames.get(0).toString());
            if (!tempGames.equals(listOfGames)) {
                System.out.println("In listOfGames = tempList");

                listOfGames = tempGames;
                mGamesView = (GridViewPager) findViewById(R.id.pager);
                GameAdapter adapter = new GameAdapter(this, getFragmentManager(), tempGames);
                mGamesView.setAdapter(adapter);
            }

        }
    }

    public void onEvent(String temp) {
        if (temp.startsWith("GameSend")) {
            EventBus.getDefault().post(temp, this);
        }
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {

    }

}
