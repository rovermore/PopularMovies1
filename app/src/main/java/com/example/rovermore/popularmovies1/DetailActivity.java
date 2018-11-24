package com.example.rovermore.popularmovies1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    //Create variables to save layout views
    private TextView tvOriginalTitle;
    private ImageView ivPoster;
    private TextView tvRate;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private ImageButton favButton;

    private static final boolean NOT_FAVORITED_TAG = false;
    private static final boolean FAVORITED_TAG = true;

    // Member variable for the Database
    private AppDatabase mDb;
    int movieDbId;
    Movie favMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mDb = AppDatabase.getInstance(getApplicationContext());

        final Movie detailMovie = getIntent().getParcelableExtra("movieData");
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
        Picasso.with(this)
                .load(url)
                .into(ivPoster);
        //saving id from online db in a variable
        movieDbId = detailMovie.getDbId();
        //checks if the movie is already in the AppDatabase and marks ImageButton view as favourited
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favMovie = mDb.movieDao().loadMovieByOnLineDbId(movieDbId);
                if (favMovie != null) {

                    favButton.setTag(FAVORITED_TAG);
                    favButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);

                } else {
                    favButton.setTag(NOT_FAVORITED_TAG);
                    favButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(favButton.getTag().equals(NOT_FAVORITED_TAG)){
                    //inserting movie into favMovie table
                favButton.setBackgroundResource(0);
                favButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                favButton.setTag(FAVORITED_TAG);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.movieDao().insertMovie(detailMovie);

                    }
                });
                    Toast.makeText(getApplicationContext(),"Movie saved in favorites",Toast.LENGTH_SHORT).show();
                }else{
                    //deleting movie from favourites table
                    favButton.setBackgroundResource(0);
                    favButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    favButton.setTag(NOT_FAVORITED_TAG);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            mDb.movieDao().deleteMovie(favMovie);

                        }
                    });
                    Toast.makeText(getApplicationContext(),"Movie removed from favorites",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
