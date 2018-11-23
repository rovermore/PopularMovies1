package com.example.rovermore.popularmovies1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    //Create variables to save layout views
    private TextView tvOriginalTitle;
    private ImageView ivPoster;
    private TextView tvRate;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private ImageButton favButton;
    private Context context = getBaseContext();
    private static final boolean NOT_FAVORITED_TAG = false;
    private static final boolean FAVORITED_TAG = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Movie detailMovie = getIntent().getParcelableExtra("movieData");
        String path = detailMovie.getPosterPath();
        String url = NetworkUtils.posterUrlBuilder(path);

        //Bind the views with the variables
        tvOriginalTitle = findViewById(R.id.tv_original_title);
        ivPoster = findViewById(R.id.iv_poster);
        tvRate = findViewById(R.id.tv_rate);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvOverview = findViewById(R.id.tv_overview);
        favButton = findViewById(R.id.ib_fav_movie);

        //Setting the values in the layout
        tvOriginalTitle.setText(detailMovie.getOriginalTitle());
        tvRate.setText(String.valueOf(detailMovie.getVoteAverage()));
        tvReleaseDate.setText(detailMovie.getReleaseDate());
        tvOverview.setText(detailMovie.getOverview());
        if(favButton.getTag()==null){
            favButton.setTag(NOT_FAVORITED_TAG);
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favButton.getTag().equals(NOT_FAVORITED_TAG)){
                //Toast.makeText(context,"Movie was added to favourites",Toast.LENGTH_SHORT).show();
                favButton.setBackgroundResource(0);
                favButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                favButton.setTag(FAVORITED_TAG);
                }else{
                    //Toast.makeText(context,"Movie was removed from favourites", Toast.LENGTH_SHORT).show();
                    favButton.setBackgroundResource(0);
                    favButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    favButton.setTag(NOT_FAVORITED_TAG);
                }
            }
        });

        Picasso.with(this)
                .load(url)
                .into(ivPoster);

    }
}
