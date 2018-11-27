package com.example.rovermore.popularmovies1.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rovermore.popularmovies1.viewmodels.MainViewModel;
import com.example.rovermore.popularmovies1.adapters.MovieAdapter;
import com.example.rovermore.popularmovies1.network.NetworkUtils;
import com.example.rovermore.popularmovies1.R;
import com.example.rovermore.popularmovies1.datamodel.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private final String LIST_STATE_KEY = "recycler_view_state";
    private Parcelable mListState;
    private final String CONTENT_KEY = "data_state";
    private int contentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this, 2);


        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            layoutManager.onRestoreInstanceState(mListState);
            contentData = savedInstanceState.getInt(CONTENT_KEY);

            switch (contentData){
                case 0:
                    fetchPopular();
                    break;
                case 1:
                    fetchTop();
                    break;
                case 2:
                    fetchFav();
                    break;
            }

        }else {

            if(NetworkUtils.isInternetAvailable(getApplicationContext())){
                fetchTop();

            } else {
                Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();
            }

        }
    }

    //Sets RecyclerView and his corresponding adapter to create the layout
    private void createUI(List<Movie> listMovies){

        movieAdapter = new MovieAdapter(getApplicationContext(),listMovies,this);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.fetch_popular){
            //fetch data with popular query
            if(NetworkUtils.isInternetAvailable(getApplicationContext())) {
                fetchPopular();
            }else{
                Toast.makeText(getApplicationContext(),"There is no internet available",Toast.LENGTH_SHORT).show();
            }

        }
        if(itemId==R.id.fetch_top_rated){
            if(NetworkUtils.isInternetAvailable(getApplicationContext())) {
                fetchTop();
            }else{
                Toast.makeText(getApplicationContext(),"There is no internet available",Toast.LENGTH_SHORT).show();
            }

        }
        if(itemId==R.id.fav_movies){
            fetchFav();

        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchFav() {
        //loads favourite list from database
        setTitle(R.string.FAVOURITES);
        contentData = 2;
        setUpViewModel();
    }

    private void fetchTop() {
        setTitle(R.string.TOP_RATED);
        //fetch data with top_rated query
        contentData = 1;
        setUpFetchViewModel(NetworkUtils.TOP_RATED_PATH);
    }

    private void fetchPopular() {
        setTitle(R.string.POPULAR);
        contentData = 0;
        //fetch data with popular query
        setUpFetchViewModel(NetworkUtils.POPULAR_PATH);
    }

    private void setUpViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
                if(movieList!= null && !movieList.isEmpty()) {
                    createUI(movieList);
                }else {
                    Toast.makeText(getApplicationContext(),
                            "There's no favourite movies found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setUpFetchViewModel(String listToFetch){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPopularMovies(listToFetch).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                if(movieList!= null && !movieList.isEmpty()) {
                    createUI(movieList);
                }else  {
                    Toast.makeText(getApplicationContext(),
                            "No movies found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //to save the RecyclerView state in case of rotation
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        mListState = layoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
        state.putInt(CONTENT_KEY,contentData);

    }

    //to restore the RecyclerView state in case of rotation
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if(state != null) {
            mListState = state.getParcelable(LIST_STATE_KEY);
            contentData = state.getInt(CONTENT_KEY);
        }
    }

}
