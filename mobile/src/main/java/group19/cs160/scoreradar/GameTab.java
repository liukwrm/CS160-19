package group19.cs160.scoreradar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;

import pl.tajchert.buswear.EventBus;

/**
 * Created by liukwarm on 12/3/15.
 */
public class GameTab extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Game> games;
    private HashSet<Game> gamesSet;
    private HashSet<String> myGames;

    public GameTab() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        games = getActivity().getIntent().getExtras().getParcelableArrayList("games");
        ArrayList<String> temp = getArguments().getStringArrayList("myGames");
        myGames = new HashSet<>();
        if (temp != null) {
            for (String i : temp) {
                myGames.add(i);
            }
        }

        gamesSet = new HashSet<>();
        for (Game g : games) {
            gamesSet.add(g);
        }
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(Game game) {
        gamesSet.add(game);
        games.clear();
        for (Game g : gamesSet) {
            games.add(g);
        }
        mRecyclerView.swapAdapter(new GameAdapter(games, myGames, mRecyclerView), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.game, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View rootView = getView();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GameAdapter(games, myGames, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }


}
