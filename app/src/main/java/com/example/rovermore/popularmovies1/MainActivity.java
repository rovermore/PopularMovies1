package com.example.rovermore.popularmovies1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.rovermore.popularmovies1.NetworkUtils.POPULAR_PATH;
import static com.example.rovermore.popularmovies1.NetworkUtils.TOP_RATED_PATH;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMovies().execute(POPULAR_PATH);
    }

    //Sets RecyclerView and his corresponding adapter to create the layout
    private void createUI(List<Movie> movieList){

        movieAdapter = new MovieAdapter(this,movieList,this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
    }

    //Starts the detail activity with the detailed info from the movie clicked
    @Override
    public void onClick(Movie currentMovie) {

        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("movieData",currentMovie);
        startActivity(intent);
    }

    //Fetches movies list from database and saves it into a List
    public class FetchMovies extends AsyncTask<String, Void, List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... path) {

            List<Movie> movieList = new ArrayList<>();

            URL url = NetworkUtils.urlBuilder(path[0]);

            try {
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(url);

                movieList = NetworkUtils.parseJson(jsonMoviesResponse);

                return movieList;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            if(movies!=null) {
                createUI(movies);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.fetch_popular){
            setTitle(R.string.POPULAR);
            //fetch data with popular query
            new FetchMovies().execute(POPULAR_PATH);
        }
        if(itemId==R.id.fetch_top_rated){
            setTitle(R.string.TOP_RATED);
            //fetch data with top_rated query
            new FetchMovies().execute(TOP_RATED_PATH);
        }
        if(itemId==R.id.fav_movies){
            //loads favourite list from database
            setTitle(R.string.FAVOURITES);
            setUpViewModel();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
                movieAdapter.setMovies(movieList);
            }
        });
    }
}
