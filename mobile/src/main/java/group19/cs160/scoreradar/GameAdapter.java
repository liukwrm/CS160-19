package group19.cs160.scoreradar;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.TimeZone;

import pl.tajchert.buswear.EventBus;

/**
 * Created by liukwarm on 12/3/15.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private ArrayList<Game> mDataset;
    private static HashSet<String> myGames;
    private static RecyclerView mRecyclerView;
    private boolean hasSubscribe;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView score1;
        public TextView score2;
        public ImageView team1;
        public ImageView team2;
        public ImageButton subscribe;
        public TextView aux;
        private GameAdapter.ViewHolder.IMyViewHolderClicks mListener;

        public ViewHolder(View v, GameAdapter.ViewHolder.IMyViewHolderClicks mListener) {
            super(v);
            score1 = (TextView) v.findViewById(R.id.score1);
            score2 = (TextView) v.findViewById(R.id.score2);
            team1 = (ImageView) v.findViewById(R.id.team1);
            team2 = (ImageView) v.findViewById(R.id.team2);
            aux = (TextView) v.findViewById(R.id.aux);
            subscribe = (ImageButton) v.findViewById(R.id.subscribe);
            subscribe.setBackground(null);
            subscribe.setOnClickListener(this);
            this.mListener = mListener;
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Log.d("ONCLICK", v.toString());
            if (v instanceof ImageButton) {
                mListener.click((ImageButton) v, this);
                return;
            }
            mListener.wholeClick(this);

        }

        public static interface IMyViewHolderClicks {
            public void click(ImageButton caller, ViewHolder v);
            public void wholeClick(ViewHolder v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameAdapter(ArrayList<Game> myDataset, HashSet<String> myGames, RecyclerView mrv, boolean hasSubscribe) {
        mDataset = myDataset;
        this.myGames = myGames;
        mRecyclerView = mrv;
        this.hasSubscribe = hasSubscribe;
        EventBus.getDefault().register(this);


    }


    public void onEvent(Game game){
        return;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v;
        if (hasSubscribe) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_item_home, parent, false);
        }
        ViewHolder vh = new ViewHolder(v, new GameAdapter.ViewHolder.IMyViewHolderClicks() {
            public void click(ImageButton v, ViewHolder vh) {
                addTeam(v, vh);
            }
            public void wholeClick(ViewHolder vh) {
                openGame(vh);
            }
        });
        return vh;
    }

    private void openGame(GameAdapter.ViewHolder vh) {
        int itemPosition = vh.getAdapterPosition();
        Intent intent = new Intent(mRecyclerView.getContext(), SpecificGame.class);
        intent.putExtra("game", mDataset.get(itemPosition));
        mRecyclerView.getContext().startActivity(intent);
    }

    private void addTeam(View v, ViewHolder vh) {
        int itemPosition = vh.getAdapterPosition();
        if (myGames.contains(mDataset.get(itemPosition).getId())) {
            Log.d("REMOVEGAME", "" + itemPosition);
            myGames.remove(mDataset.get(itemPosition).getId());

            try {
                FileOutputStream op = mRecyclerView.getContext().openFileOutput(TempActivity.getGamesPath(), Context.MODE_PRIVATE);
                JSONArray array = new JSONArray();
                for (String s : myGames) {
                    array.put(s);
                }
                op.write(array.toString().getBytes());
                Log.d("REMOVEGAMESAVE", array.toString());
                op.close();

            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }

            ((ImageButton) v).setImageResource(R.drawable.button_subscribe);
        } else {
            Log.d("ADDGAME", "" + itemPosition);
            myGames.add(mDataset.get(itemPosition).getId());

            try {
                FileOutputStream op = mRecyclerView.getContext().openFileOutput(TempActivity.getGamesPath(), Context.MODE_PRIVATE);
                JSONArray array = new JSONArray();
                for (String s : myGames) {
                    array.put(s);
                }
                op.write(array.toString().getBytes());
                Log.d("ADDGAMESAVE", array.toString());
                op.close();

            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }

            ((ImageButton) v).setImageResource(R.drawable.button_unsubscribe);
        }
        ArrayList<String> array = new ArrayList<>();
        JSONArray games = new JSONArray();
        for (String s : myGames) {
            games.put(s);
        }
        EventBus.getDefault().post("game sub" + games.toString(), mRecyclerView.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Game game = mDataset.get(position);
        holder.score1.setText("" + game.getHomeScore());
        holder.score2.setText("" + game.getAwayScore());

        switch(game.getStatus()) {
            case "created":
                holder.aux.setText("New");
                break;
            case "scheduled":
                try {
                    String temp = game.getTime().substring(0,19);
                    Log.d("TIME", temp);
                    DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date = utcFormat.parse(temp);

                    DateFormat pstFormat = new SimpleDateFormat("hh:mm:ss");
                    pstFormat.setTimeZone(TimeZone.getTimeZone("PST"));

                    holder.aux.setText("Starts at\n" + pstFormat.format(date));
                } catch (ParseException e) {
                    Log.d("Date", "You shouldn't have trusted internset");
                }
                break;
            case "closed":
                holder.aux.setText("Final Score");
                break;
            case "inprogress":
                holder.aux.setText("Q" + game.quarter + "\n" + game.getClock() + " left");
                break;
        }
        holder.team1.setImageResource(GameInformation.getLogo(game.getHome()));
        holder.team2.setImageResource(GameInformation.getLogo(game.getAway()));
        //need to change for personal things
        if (hasSubscribe) {
            if (myGames.contains(game.getId())) {
                holder.subscribe.setImageResource(R.drawable.button_unsubscribe);
            } else {
                holder.subscribe.setImageResource(R.drawable.button_subscribe);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}