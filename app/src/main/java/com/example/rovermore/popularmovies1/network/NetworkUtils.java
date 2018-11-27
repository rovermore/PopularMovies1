package com.example.rovermore.popularmovies1.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.rovermore.popularmovies1.BuildConfig;
import com.example.rovermore.popularmovies1.datamodel.Movie;
import com.example.rovermore.popularmovies1.datamodel.Review;
import com.example.rovermore.popularmovies1.datamodel.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String POPULAR_PATH = "popular";
    public static final String TOP_RATED_PATH = "top_rated";
    private static final String API_PARAM = "api_key";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342";
    private final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";
    private final static String TRAILER_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String VIDEO_PATH ="videos";
    private final static String REVIEWS_PATH ="reviews";

    private NetworkUtils(){}

    /**
     * Builds the URL used to talk to the MovieDB server.
     */
    public static URL urlBuilder(String path){

        //Implement the Url builder
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(path)
                .appendQueryParameter(API_PARAM, BuildConfig.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;

    }

    /**
     * Builds the URL used to extract trailer json from MovieDB server.
     */
    public static URL trailerUrlBuilder(String path){

        //Implement the Url builder
        Uri buildUri = Uri.parse(TRAILER_BASE_URL).buildUpon()
                .appendPath(path)
                .appendPath(VIDEO_PATH)
                .appendQueryParameter(API_PARAM, BuildConfig.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;

    }

    /**
     * Builds the URL used to extract trailer json from MovieDB server.
     */
    public static URL reviewsUrlBuilder(String path){

        //Implement the Url builder
        Uri buildUri = Uri.parse(TRAILER_BASE_URL).buildUpon()
                .appendPath(path)
                .appendPath(REVIEWS_PATH)
                .appendQueryParameter(API_PARAM, BuildConfig.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;

    }

    /**
     * Builds the URL used to talk to download the movie poster.
     */
    public static String posterUrlBuilder(String posterPath){

        //Implement the Url builder
        Uri buildUri = Uri.parse(POSTER_BASE_URL).buildUpon()
                .appendEncodedPath(posterPath)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        String stringUrl = url.toString();

        return stringUrl;

    }

    /**
     * Builds the URL used to talk to create youtube trailer intent
     */
    public static String youtubeUrlBuilder(String youtubePath){

        //Implement the Url builder
        Uri buildUri = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendQueryParameter("v",youtubePath)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        String stringUrl = url.toString();

        return stringUrl;

    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Parses the JSON and saves into a Movie object.
     */
    public static List<Movie> parseJson (String json) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        JSONObject resultJson = null;
        try {
            resultJson = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray resultArray = resultJson.optJSONArray("results");
        for(int i = 0;i<resultArray.length();i++){

            JSONObject jsonMovie = resultArray.getJSONObject(i);
            String originalTitle = jsonMovie.optString("original_title");
            String posterPath = jsonMovie.optString("poster_path");
            String overview = jsonMovie.optString("overview");
            double voteAverage = jsonMovie.optDouble("vote_average");
            String releaseDate = jsonMovie.optString("release_date");
            int dbId = jsonMovie.optInt("id");

            Movie movie = new Movie(originalTitle,posterPath, overview, voteAverage,releaseDate,dbId);

            Log.v(TAG, "title " + originalTitle);
            Log.v(TAG, "poster " + posterPath);

            movieList.add(movie);
        }

        return movieList;
    }

    /**
     * Parses the JSON and saves into a Trailer object.
     */
    public static List<Trailer> parseJsonTrailer (String json) throws JSONException{
        List<Trailer> trailerList = new ArrayList<>();

        JSONObject jsonObject;

        jsonObject = new JSONObject(json);

        JSONArray results = jsonObject.optJSONArray("results");

        for(int i=0;i<results.length();i++){
            JSONObject jsonTrailer = results.optJSONObject(i);
            String key = jsonTrailer.optString("key");
            String name = jsonTrailer.optString("name");

            Trailer trailer = new Trailer(key,name);
            trailerList.add(trailer);
        }

        return trailerList;
    }

    /**
     * Parses the JSON and saves into a Trailer object.
     */
    public static List<Review> parseJsonReview(String json) throws JSONException{

        List<Review> reviewList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        JSONArray results = jsonObject.optJSONArray("results");

        for(int i=0;i<results.length();i++){
            JSONObject currentReview = results.getJSONObject(i);

            String author = currentReview.optString("author");
            String content = currentReview.optString("content");

            Review review = new Review(author,content);

            reviewList.add(review);
        }

        return reviewList;
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return  true;
        }else{
            return false;
        }
    }

}
