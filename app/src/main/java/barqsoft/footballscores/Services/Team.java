package barqsoft.footballscores.Services;

import com.google.gson.Gson;

/**
 * Created by Andrés on 9/15/15.
 */
public class Team {

    public String name;
    public String code;
    public String shortName;
    public String squadMarketValue;
    public String crestUrl;

    public String toJson() {
        return new Gson().toJson(this);
    }

}
