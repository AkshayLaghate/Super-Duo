package barqsoft.footballscores.Services;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by Andrés on 9/15/15.
 */
public class FetchData {

    FootballDataApi mApi;

    public FetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.football-data.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        mApi = retrofit.create(FootballDataApi.class);
    }

    public Team getTeamInformation(String apiKey, String teamId) throws IOException {
        return mApi.getTeamInformation(apiKey, teamId).execute().body();
    }

    private interface FootballDataApi {
        @GET("/alpha/teams/{team_id}")
        Call<Team> getTeamInformation(
                @Header("X-Auth-Token") String apiKey,
                @Path("team_id") String teamId
        );
    }

}
