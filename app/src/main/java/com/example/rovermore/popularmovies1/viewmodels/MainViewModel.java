package com.example.rovermore.popularmovies1.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.rovermore.popularmovies1.database.AppDatabase;
import com.example.rovermore.popularmovies1.datamodel.Movie;
import com.example.rovermore.popularmovies1.network.JsonLiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    private JsonLiveData fetchedMovies;

    public MainViewModel(Application application) {
        super(application);
        // COMPLETED (4) In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movies = database.movieDao().loadAllMovies();

    }

    public MainViewModel(Application application, String listMoviesToFetch) {
        super(application);
        // COMPLETED (4) In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");


    }

    //getter for the movies variable
    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<Movie>> getPopularMovies(String listMoviesToFetch){

        fetchedMovies = new JsonLiveData(listMoviesToFetch);
        return fetchedMovies;
    }
}
