package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilities {
    public static final String LOG_TAG = Utilities.class.getSimpleName();

    public static final int BUNDESLIGA1 = 394;
    public static final int BUNDESLIGA2 = 395;
    public static final int BUNDESLIGA3 = 403;
    public static final int LIGUE1 = 396;
    public static final int LIGUE2 = 397;
    public static final int PREMIER_LEAGUE = 398;
    public static final int PRIMERA_DIVISION = 399;
    public static final int SEGUNDA_DIVISION = 400;
    public static final int SERIE_A = 401;
    public static final int PRIMERA_LIGA = 402;
    public static final int EREDIVISIE = 404;

    public static String getLeague(Context context, int leagueId) {

        switch (leagueId) {
            case BUNDESLIGA1:
            case BUNDESLIGA2:
            case BUNDESLIGA3:
                return context.getString(R.string.bundesliga);

            case PREMIER_LEAGUE:
                return context.getString(R.string.premierleague);

            case SERIE_A:
                return context.getString(R.string.seriaa);

            case PRIMERA_DIVISION:
                return context.getString(R.string.primeradivison);


            default:
                return "Unknown";
        }
    }


}