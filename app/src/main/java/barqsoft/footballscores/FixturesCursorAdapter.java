package barqsoft.footballscores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.samples.svg.SvgDecoder;
import com.bumptech.glide.samples.svg.SvgDrawableTranscoder;
import com.bumptech.glide.samples.svg.SvgSoftwareLayerSetter;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import barqsoft.footballscores.Data.FixtureAndTeam;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yehya khaled on 2/26/2015.
 */
public class FixturesCursorAdapter extends CursorAdapter {

    public FixturesCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public FixtureAndTeam getItem(int position) {
        Cursor cursor = getCursor();
        if (cursor == null || cursor.getCount() == 0)
            return null;

        return FixtureAndTeam.fromCursor(cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder mHolder = new ViewHolder(view);
        view.setTag(mHolder);

        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder mHolder = (ViewHolder) view.getTag();

        FixtureAndTeam fixtureAndTeam = FixtureAndTeam.fromCursor(cursor);

        //Match data
        String leagueAndMatchDay = Utilities.getLeague(context, fixtureAndTeam.leagueId) + "\n" + "Matchday " + fixtureAndTeam.matchDay;
        mHolder.leagueAndMatchDay.setText(leagueAndMatchDay);
        mHolder.matchTime.setText(fixtureAndTeam.matchTime);
        mHolder.matchScore.setText(getScore(fixtureAndTeam.homeTeamGoals, fixtureAndTeam.awayTeamGoals));

        //Home team data
        mHolder.homeTeamName.setText(fixtureAndTeam.homeTeamName);
        mHolder.homeTeamCrest.setContentDescription(fixtureAndTeam.homeTeamName);
        if (fixtureAndTeam.homeCrestUrlAvailable())
            Glide.with(context)
                    .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .listener(new SvgSoftwareLayerSetter<Uri>())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(Uri.parse(fixtureAndTeam.homeTeamCrestUrl))
                    .placeholder(R.drawable.ic_black_crest_hi)
                    .error(R.drawable.ic_black_crest_hi)
                    .into(mHolder.homeTeamCrest);
        else
            mHolder.homeTeamCrest.setImageResource(R.drawable.ic_black_crest_hi);

        //Away team data
        mHolder.awayTeamName.setText(fixtureAndTeam.awayTeamName);
        mHolder.awayTeamCrest.setContentDescription(fixtureAndTeam.awayTeamName);
        if (fixtureAndTeam.awayCrestUrlAvailable())
            Glide.with(context)
                    .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .listener(new SvgSoftwareLayerSetter<Uri>())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(Uri.parse(fixtureAndTeam.awayTeamCrestUrl))
                    .placeholder(R.drawable.ic_black_crest_hi)
                    .error(R.drawable.ic_black_crest_hi)
                    .into(mHolder.awayTeamCrest);
        else
            mHolder.awayTeamCrest.setImageResource(R.drawable.ic_black_crest_hi);

    }

    private CharSequence getScore(int homeTeamGoals, int awayTeamGoals) {

        if (homeTeamGoals < 0 || awayTeamGoals < 0) {
            return "0 - 0";
        } else
            return homeTeamGoals + " - " + awayTeamGoals;
    }


    /**
     * ViewHolder
     */
    public static class ViewHolder {

        @Bind(R.id.home_team_name)
        TextView homeTeamName;
        @Bind(R.id.away_team_name)
        TextView awayTeamName;
        @Bind(R.id.match_score)
        TextView matchScore;
        @Bind(R.id.match_date)
        TextView matchTime;
        @Bind(R.id.home_team_crest)
        ImageView homeTeamCrest;
        @Bind(R.id.away_team_crest)
        ImageView awayTeamCrest;
        @Bind(R.id.league_and_match_day)
        TextView leagueAndMatchDay;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
