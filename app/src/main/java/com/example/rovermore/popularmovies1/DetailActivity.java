package com.example.rovermore.popularmovies1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    //Create variables to save layout views
    TextView tvOriginalTitle;
    ImageView ivPoster;
    TextView tvRate;
    TextView tvReleaseDate;
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Movie detailMovie = (Movie) getIntent().getParcelableExtra("movieData");
        String path = detailMovie.getPosterPath();
        String url = NetworkUtils.posterUrlBuilder(path);

        //Bind the views with the variables
        tvOriginalTitle = findViewById(R.id.tv_original_title);
        ivPoster = findViewById(R.id.iv_poster);
        tvRate = findViewById(R.id.tv_rate);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvOverview = findViewById(R.id.tv_overview);

        //Setting the values in the layout
        tvOriginalTitle.setText(detailMovie.getOriginalTitle());
        tvRate.setText(String.valueOf(detailMovie.getVoteAverage()));
        tvReleaseDate.setText(detailMovie.getReleaseDate());
        tvOverview.setText(detailMovie.getOverview());

        Picasso.with(this)
                .load(url)
                .into(ivPoster);

    }
}
