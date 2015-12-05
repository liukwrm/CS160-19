package group19.cs160.scoreradar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by liukwarm on 12/3/15.
 */
public class TeamTab extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TeamInformation> myDataset;

    public TeamTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDataset();
    }

    private void createDataset() {
        myDataset = new ArrayList<>();
        //Insert objects!
        myDataset.add(new TeamInformation("Atlanta Hawks", "7-10", false));
        myDataset.add(new TeamInformation("Atlanta Hawks", "7-10", false));
        myDataset.add(new TeamInformation("Atlanta Hawks", "7-10", false));
        myDataset.add(new TeamInformation("Atlanta Hawks", "7-10", false));
        myDataset.add(new TeamInformation("Atlanta Hawks", "7-10", false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        mAdapter = new TeamAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

}
