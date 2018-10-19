package com.example.rovermore.popularmovies1;

public class Movie {

    public static String originalTitle;
    public static String posterPath;
    public static String overview;
    public static double voteAverage;
    public static String releaseDate;

    public Movie(String originalTitle, String posterPath, String overview, double voteAverage, String releaseDate){
        this.originalTitle=originalTitle;
        this.posterPath=posterPath;
        this.overview=overview;
        this.voteAverage=voteAverage;
        this.releaseDate=releaseDate;

    }

    public static String getOriginalTitle() {
        return originalTitle;
    }

    public static String getPosterPath() {
        return posterPath;
    }

    public static String getOverview() {
        return overview;
    }

    public static double getVoteAverage() {
        return voteAverage;
    }

    public static String getReleaseDate() {
        return releaseDate;
    }
}
