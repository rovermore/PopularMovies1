package com.example.rovermore.popularmovies1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.rovermore.popularmovies1.NetworkUtils.POPULAR_PATH;
import static com.example.rovermore.popularmovies1.NetworkUtils.TOP_RATED_PATH;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMovies().execute(POPULAR_PATH);
    }

    private void createUI(List<Movie> movieList){

        MovieAdapter movieAdapter = new MovieAdapter(this,movieList);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
    }

    public class FetchMovies extends AsyncTask<String, Void, List<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... path) {

            List<Movie> movieList = new ArrayList<>();

            URL url = NetworkUtils.urlBuilder(path[0]);

            try {
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(url);

                movieList = NetworkUtils.parseJson(jsonMoviesResponse);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);

            createUI(movies);
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

        return super.onOptionsItemSelected(item);
    }
}
