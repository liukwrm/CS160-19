
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
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private ArrayList<TeamInformation> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView record;
        public ImageView logo;
        public ImageButton subscribe;
        public TextView name;

        public ViewHolder(View v) {
            super(v);
            record = (TextView) v.findViewById(R.id.record);
            logo = (ImageView) v.findViewById(R.id.logo);
            subscribe = (ImageButton) v.findViewById(R.id.subscribe);
            subscribe.setBackground(null);
            name = (TextView) v.findViewById(R.id.name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TeamAdapter(ArrayList<TeamInformation> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
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
        if (team.subscribed) {
            holder.subscribe.setClickable(false);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}