package com.example.rovermore.popularmovies1.network;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rovermore.popularmovies1.datamodel.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonLiveData extends LiveData<List<Movie>> {

    //private final Context context;

    public JsonLiveData(String listOfMoviesToFetch) {
        //this.context = context;
        loadData(listOfMoviesToFetch);
    }

    private void loadData(String listOfMovies) {

        new AsyncTask < String, Void, List < Movie >>() {

        @Override
        protected List<Movie> doInBackground (String...path){

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
        protected void onPostExecute (List < Movie > movies) {
            super.onPostExecute(movies);

                setValue(movies);

        }
    }.execute(listOfMovies);

    }
}
