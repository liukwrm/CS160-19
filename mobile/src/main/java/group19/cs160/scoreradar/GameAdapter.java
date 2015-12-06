package group19.cs160.scoreradar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by liukwarm on 12/3/15.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private ArrayList<Game> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView score1;
        public TextView score2;
        public ImageView team1;
        public ImageView team2;
        public ImageButton subscribe;
        public TextView aux;

        public ViewHolder(View v) {
            super(v);
            score1 = (TextView) v.findViewById(R.id.score1);
            score2 = (TextView) v.findViewById(R.id.score2);
            team1 = (ImageView) v.findViewById(R.id.team1);
            team2 = (ImageView) v.findViewById(R.id.team2);
            aux = (TextView) v.findViewById(R.id.aux);
            subscribe = (ImageButton) v.findViewById(R.id.subscribe);
            subscribe.setBackground(null);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameAdapter(ArrayList<Game> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Game game = mDataset.get(position);
        holder.score1.setText("" + game.getHomeScore());
        holder.score2.setText("" + game.getAwayScore());
        holder.aux.setText(game.getStatus());
        holder.team1.setImageResource(GameInformation.getLogo(game.getHome()));
        holder.team2.setImageResource(GameInformation.getLogo(game.getAway()));
        //need to change for personal things
        holder.subscribe.setClickable(false);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}