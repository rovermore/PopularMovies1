package com.example.rovermore.popularmovies1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterClickHandler {

    //Create variables to save layout views
    private TextView tvOriginalTitle;
    private ImageView ivPoster;
    private TextView tvRate;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private ImageButton favButton;
    private Button reviewsButton;

    private static final boolean NOT_FAVORITED_TAG = false;
    private static final boolean FAVORITED_TAG = true;

    // Member variable for the Database
    private AppDatabase mDb;
    private int movieDbId;
    private String stringMovieDbId;
    private Movie favMovie;
    private TrailerAdapter trailerAdapter;




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
        reviewsButton = findViewById(R.id.reviews_button);
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewsActivity.class);
                intent.putExtra("id",stringMovieDbId);
                startActivity(intent);
            }
        });

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
        stringMovieDbId = String.valueOf(movieDbId);
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

                //Checks if movie is in favourites table and add it if clicked
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

        new FetchTrailers().execute(stringMovieDbId);
    }

    @Override
    public void onClickHandler(Trailer currentTrailer) {
        //throw intent to open youtube when clicked
        String trailerKey = currentTrailer.getKey();
        String youtubeUrl = NetworkUtils.youtubeUrlBuilder(trailerKey);
        Uri youtubeUri = Uri.parse(youtubeUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW,youtubeUri);
        startActivity(intent);
    }

    //Fetches trailer list from MovieDb
    private class FetchTrailers extends AsyncTask<String, Void, List<Trailer>>{


        @Override
        protected List<Trailer> doInBackground(String... strings) {

            List<Trailer> trailerList;

            URL url = NetworkUtils.trailerUrlBuilder(strings[0]);

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);

                trailerList = NetworkUtils.parseJsonTrailer(jsonResponse);

                return trailerList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Trailer> trailerList) {
            super.onPostExecute(trailerList);

            createRecyclerViewUI(trailerList);
        }
    }

    //Sets the recycler view layout for the trailer list
    private void createRecyclerViewUI(List<Trailer> trailerList) {
        trailerAdapter = new TrailerAdapter(trailerList, getApplicationContext(), this);

        RecyclerView detailRecyclerView = findViewById(R.id.recycler_view_detail);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        detailRecyclerView.setLayoutManager(layoutManager);
        detailRecyclerView.setHasFixedSize(true);
        detailRecyclerView.setAdapter(trailerAdapter);

    }
}
