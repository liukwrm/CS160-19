package group19.cs160.scoreradar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
        EventBus.getDefault().register(this);

        mGamesView = (GridViewPager) findViewById(R.id.pager);

        GameAdapter adapter = new GameAdapter(this, getFragmentManager());
        mGamesView.setAdapter(adapter);
        // pull current games, get list. Iterate through and add each game into mGamesLayout

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            // Display message
            Log.d("message received", message);
            Gson gson = new Gson();
            Type listOfTemp = new TypeToken<ArrayList<Game>>(){}.getType();
            String json2 = gson.toJson(listOfGames, listOfTemp);
            ArrayList<Game> temp = gson.fromJson(json2, listOfTemp);
            Log.d("message translated", temp.toString());
            listOfGames = temp;
        }
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

    public void onEventMainThread(Game game) {
        System.out.println("In OnEvent");
        listOfGames.add(game);
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
