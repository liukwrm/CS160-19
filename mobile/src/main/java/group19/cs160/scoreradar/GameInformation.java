package group19.cs160.scoreradar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liukwarm on 12/4/15.
 */
public class GameInformation {

    String team1, team2;
    int score1, score2;
    int logo1, logo2;
    boolean subscribed;

    private static Map<String, Integer> logoMap = new HashMap<String, Integer>();
    static {
        logoMap.put("", R.drawable.atlanta_hawks);
        logoMap.put("", R.drawable.boston_celtics);
        logoMap.put("", R.drawable.brooklyn_nets);
        logoMap.put("", R.drawable.charlotte_hornets);
        logoMap.put("", R.drawable.chicago_bulls);
        logoMap.put("", R.drawable.cleveland_cavaliers);
        logoMap.put("", R.drawable.dallas_mavericks);
        logoMap.put("", R.drawable.denver_nuggets);
        logoMap.put("", R.drawable.detroit_pistons);
        logoMap.put("", R.drawable.golden_state_warriors);
        logoMap.put("", R.drawable.houston_rockets);
        logoMap.put("", R.drawable.indiana_pacers);
        logoMap.put("", R.drawable.los_angeles_clippers);
        logoMap.put("", R.drawable.los_angeles_lakers);
        logoMap.put("", R.drawable.memphis_grizzlies);
        logoMap.put("", R.drawable.miami_heat);
        logoMap.put("", R.drawable.milwaukee_bucks);
        logoMap.put("", R.drawable.minnesota_timberwolves);
        logoMap.put("", R.drawable.new_orleans_pelicans);
        logoMap.put("", R.drawable.new_york_knicks);
        logoMap.put("", R.drawable.oklahoma_city_thunder);
        logoMap.put("", R.drawable.orlando_magic);
        logoMap.put("", R.drawable.philadelphia_76ers);
        logoMap.put("", R.drawable.phoenix_suns);
        logoMap.put("", R.drawable.portland_trail_blazers);
        logoMap.put("", R.drawable.sacramento_kings);
        logoMap.put("", R.drawable.san_antonio_spurs);
        logoMap.put("", R.drawable.toronto_raptors);
        logoMap.put("", R.drawable.utah_jazz);
        logoMap.put("", R.drawable.washington_wizards);

    }


    public GameInformation(String team1, String team2, int score1, int score2, boolean subscribed) {
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.subscribed = subscribed;

        logo1 = logoMap.get(team1);
        logo2 = logoMap.get(team2);
    }

    public static int getLogo(String team) {
        return logoMap.get(team);
    }
}
