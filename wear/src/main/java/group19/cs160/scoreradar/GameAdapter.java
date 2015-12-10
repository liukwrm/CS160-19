package group19.cs160.scoreradar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends FragmentGridPagerAdapter {

    private Context mContext;
    private List mRows;
    private ArrayList<Game> mDataset;
    private ArrayList<GameFragment> list = new ArrayList<>();

    public GameAdapter(Context ctx, FragmentManager fm, ArrayList<Game> games) {
        super(fm);
        mContext = ctx;
        Log.d("in gameAdapter", games.toString());
        GameFragment game1 = new GameFragment().newInstance(new Game("a", "Cleveland Cavaliers", "Portland Trail Blazers", "00:00", 105, 100, "closed"));
        GameFragment game2 = new GameFragment().newInstance(new Game("a", "Brooklyn Nets", "Houston Rockets", "00:00", 110, 105, "closed"));
        GameFragment game3 = new GameFragment().newInstance(new Game("a", "Denver Nuggets", "Orlando Magic", "00:00", 74, 85, "closed"));
        //ArrayList<GameFragment> temp = new ArrayList<GameFragment>();
        list.add(game1);
        list.add(game2);
        list.add(game3);
//        for(GameFragment game : list) {
//            //GameFragment frag = GameFragment.newInstance(game);
//            list.add(game);
//        }
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount(int i) {
        return 1;
    }

    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        return list.get(row);
    }

    // Obtain the background image for the row
    @Override
    public Drawable getBackgroundForRow(int row) {
        return mContext.getDrawable(R.drawable.watch_basketball_bg_small);
    }
}