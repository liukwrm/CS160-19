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
        logoMap.put("Atlanta Hawks", R.drawable.atlanta_hawks);
        logoMap.put("Boston Celtics", R.drawable.boston_celtics);
        logoMap.put("Brooklyn Nets", R.drawable.brooklyn_nets);
        logoMap.put("Charlotte Hornets", R.drawable.charlotte_hornets);
        logoMap.put("Chicago Bulls", R.drawable.chicago_bulls);
        logoMap.put("Cleveland Cavaliers", R.drawable.cleveland_cavaliers);
        logoMap.put("Dallas Mavericks", R.drawable.dallas_mavericks);
        logoMap.put("Denver Nuggets", R.drawable.denver_nuggets);
        logoMap.put("Detroit Pistons", R.drawable.detroit_pistons);
        logoMap.put("Golden State Warriors", R.drawable.golden_state_warriors);
        logoMap.put("Houston Rockets", R.drawable.houston_rockets);
        logoMap.put("Indiana Pacers", R.drawable.indiana_pacers);
        logoMap.put("Los Angeles Clippers", R.drawable.los_angeles_clippers);
        logoMap.put("Los Angeles Lakers", R.drawable.los_angeles_lakers);
        logoMap.put("Memphis Grizzlies", R.drawable.memphis_grizzlies);
        logoMap.put("Miami Heat", R.drawable.miami_heat);
        logoMap.put("Milwaukee Bucks", R.drawable.milwaukee_bucks);
        logoMap.put("Minnesota Timberwolves", R.drawable.minnesota_timberwolves);
        logoMap.put("New Orleans Pelicans", R.drawable.new_orleans_pelicans);
        logoMap.put("New York Knicks", R.drawable.new_york_knicks);
        logoMap.put("Oklahoma City Thunder", R.drawable.oklahoma_city_thunder);
        logoMap.put("Orlando Magic", R.drawable.orlando_magic);
        logoMap.put("Philadelphia 76ers", R.drawable.philadelphia_76ers);
        logoMap.put("Phoenix Suns", R.drawable.phoenix_suns);
        logoMap.put("Portland Trail Blazers", R.drawable.portland_trail_blazers);
        logoMap.put("Sacramento Kings", R.drawable.sacramento_kings);
        logoMap.put("San Antonio Spurs", R.drawable.san_antonio_spurs);
        logoMap.put("Toronto Raptors", R.drawable.toronto_raptors);
        logoMap.put("Utah Jazz", R.drawable.utah_jazz);
        logoMap.put("Washington Wizards", R.drawable.washington_wizards);

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