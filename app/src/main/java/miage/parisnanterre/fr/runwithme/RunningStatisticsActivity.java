package miage.parisnanterre.fr.runwithme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static miage.parisnanterre.fr.runwithme.MainActivity.user;

public class RunningStatisticsActivity extends AppCompatActivity {

    private ListView statisticsListView;
    static List<RunningStatistics> statistics;
    static RunningStatisticsAdapter adapter;

    static Button button_record_time;
    static Button button_record_dist;
    static Button button_record_speed;
    final DatabaseStats db = new DatabaseStats(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        //statistics = new ArrayList<RunningStatistics>();
        statistics = db.getAllStats();
        statisticsListView = (ListView) findViewById(R.id.statisticsListView);
        adapter = new RunningStatisticsAdapter(RunningStatisticsActivity.this,statistics);
        statisticsListView.setAdapter(adapter);

        button_record_dist = (Button) findViewById(R.id.button_record_dist);
        button_record_speed = (Button) findViewById(R.id.button_record_speed);
        button_record_time = (Button) findViewById(R.id.button_record_time);

        //PullStatsBackgroundTask load = new PullStatsBackgroundTask(this);
        //load.execute();

        ImageView img_level = (ImageView) findViewById(R.id.imageView10);
        switch (user.getLevel()){
            case 1:
                img_level.setImageResource(R.mipmap.ic_level_one_foreground);
                break;
            case 2:
                img_level.setImageResource(R.mipmap.ic_level_two_foreground);
                break;
            case 3:
                img_level.setImageResource(R.mipmap.ic_level3_foreground);
                break;
            case 4:
                img_level.setImageResource(R.mipmap.ic_level4_foreground);
                break;
            case 5:
                img_level.setImageResource(R.mipmap.ic_level5_foreground);
                break;
            case 6:
                img_level.setImageResource(R.mipmap.ic_level6_foreground);
                break;
            default:
                img_level.setImageResource(R.mipmap.ic_levelinfinite_foreground);
        }
        int best_distance = 0;
        int best_rythme = 0;
        int best_temps = 0;
        for(RunningStatistics rs : statistics){
            best_distance = Integer.parseInt(rs.getDistance()) > best_distance ? Integer.parseInt(rs.getDistance()) : best_distance;
            best_rythme = Integer.parseInt(rs.getRythme()) > best_rythme ? Integer.parseInt(rs.getRythme()) : best_rythme;
            best_temps = Integer.parseInt(rs.getDuree()) > best_temps ? Integer.parseInt(rs.getDuree()) : best_temps;

        }

        button_record_time.setText(best_temps+"m.");
        button_record_speed.setText(best_rythme+"km/h");
        button_record_dist.setText(best_distance+"m.");
    }

}
