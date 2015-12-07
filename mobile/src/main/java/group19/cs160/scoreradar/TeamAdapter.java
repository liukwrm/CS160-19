
package group19.cs160.scoreradar;
import android.content.Context;
import android.content.DialogInterface;
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
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by liukwarm on 12/3/15.
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private static ArrayList<TeamInformation> mDataset;
    private static HashSet<String> myTeams;
    private static RecyclerView mRecyclerView;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView record;
        public ImageView logo;
        public ImageButton subscribe;
        public TextView name;
        private IMyViewHolderClicks mListener;


        public ViewHolder(View v, IMyViewHolderClicks mListener) {
            super(v);
            record = (TextView) v.findViewById(R.id.record);
            logo = (ImageView) v.findViewById(R.id.logo);
            subscribe = (ImageButton) v.findViewById(R.id.subscribe);
            subscribe.setOnClickListener(this);
            subscribe.setBackground(null);
            name = (TextView) v.findViewById(R.id.name);
            this.mListener = mListener;

        }

        public void onClick(View v) {
            Log.d("TEAMCLICK", v.toString());
            if (v instanceof ImageButton) {
                mListener.click((ImageButton) v, this);
            }
        }

        public static interface IMyViewHolderClicks {
            public void click(ImageButton caller, ViewHolder v);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public TeamAdapter(ArrayList<TeamInformation> myDataset, HashSet<String> myTeams, RecyclerView mrv) {
        mDataset = myDataset;
        this.myTeams = myTeams;
        mRecyclerView = mrv;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_item, parent, false);
        ViewHolder vh = new ViewHolder(v, new TeamAdapter.ViewHolder.IMyViewHolderClicks() {
            public void click(ImageButton v, ViewHolder vh) {
                addTeam(v, vh);
            }
        });
        return vh;
    }

    private void addTeam(View v, ViewHolder vh) {
        int itemPosition = vh.getAdapterPosition();
        Log.d("ADD", "" + itemPosition);
        if (myTeams.contains(mDataset.get(itemPosition).getId())) {
            Log.d("REMOVETEAM", "" + itemPosition);
            myTeams.remove(mDataset.get(itemPosition).getId());

            try {
                FileOutputStream op = mRecyclerView.getContext().openFileOutput(TempActivity.getGamesPath(), Context.MODE_PRIVATE);
                JSONArray array = new JSONArray();
                for (String s : myTeams) {
                    array.put(s);
                }
                op.write(array.toString().getBytes());
                Log.d("REMOVETEAMSAVE", array.toString());
                op.close();

            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }

            ((ImageButton) v).setImageResource(R.drawable.button_subscribe);
        } else {
            Log.d("ADDTEAM", "" + itemPosition);
            myTeams.add(mDataset.get(itemPosition).getId());

            try {
                FileOutputStream op = mRecyclerView.getContext().openFileOutput(TempActivity.getGamesPath(), Context.MODE_PRIVATE);
                JSONArray array = new JSONArray();
                for (String s : myTeams) {
                    array.put(s);
                }
                op.write(array.toString().getBytes());
                Log.d("ADDTEAMSAVE", array.toString());
                op.close();

            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }

            ((ImageButton) v).setImageResource(R.drawable.button_unsubscribe);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TeamInformation team = mDataset.get(position);
        holder.name.setText(team.getName());
        holder.record.setText(String.valueOf(team.getWins()) + " - " + String.valueOf(team.getLosses()));
        holder.logo.setImageResource(GameInformation.getLogo(team.getName()));
        if (myTeams.contains(team.getId())) {
            holder.subscribe.setImageResource(R.drawable.button_unsubscribe);
        } else {
            holder.subscribe.setImageResource(R.drawable.button_subscribe);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}