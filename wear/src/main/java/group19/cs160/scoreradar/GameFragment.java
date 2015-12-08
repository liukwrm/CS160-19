package group19.cs160.scoreradar;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.wearable.view.DismissOverlayView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import pl.tajchert.buswear.EventBus;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Game g;
    private String id;
    private String home;
    private String away;
    private String time;
    private int homeScore;
    private int awayScore;
    private String status;
    private String clock;
    private int quarter;

    public String getIdValue() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(Game game) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putParcelable("game", game);
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            g = getArguments().getParcelable("game");
            this.home = g.getHome();
            this.away = g.getAway();
            this.homeScore = g.getHomeScore();
            this.awayScore = g.getAwayScore();
            this.time = String.valueOf(g.getQuarter()) + "Q " + g.getClock();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_game, container, false);
        TextView homeTeam = (TextView) myView.findViewById(R.id.score1);
        TextView awayTeam = (TextView) myView.findViewById(R.id.score2);
        TextView time = (TextView) myView.findViewById(R.id.time);
        ImageView homelogo = (ImageView) myView.findViewById(R.id.team1logo);
        ImageView awaylogo = (ImageView) myView.findViewById(R.id.team2logo);

        homeTeam.setText(""+homeScore);
        awayTeam.setText(""+awayScore);
        time.setText(this.time);
        homelogo.setImageResource(GameInformation.getLogo(home));
        awaylogo.setImageResource(GameInformation.getLogo(away));

        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    Gson gson = new Gson();
                    String json = gson.toJson(g, Game.class);
                    EventBus.getDefault().postLocal("GameSend" + json);
                }
                return true;
            }
        });

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
