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
    private GameFragment[] list;

    public GameAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        GameFragment frag1 = GameFragment.newInstance(new Game("333", "Los Angeles Lakers", "Sacramento Kings", "in progress", 66, 55, "4:44"));
        GameFragment frag2 = GameFragment.newInstance(new Game("444", "Los Angeles Lakers", "Sacramento Kings", "4:44", 22, 33,"in progress"));
        GameFragment frag3 = GameFragment.newInstance(new Game("555", "Los Angeles Lakers", "Sacramento Kings", "4:44", 44, 77, "in progress"));
        list = new GameFragment[]{frag1, frag2, frag3};
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount(int i) {
        return 1;
    }

    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        return list[row];
    }

    // Obtain the background image for the row
    @Override
    public Drawable getBackgroundForRow(int row) {
        return mContext.getDrawable(R.drawable.watch_basketball_bg_small);
    }
}