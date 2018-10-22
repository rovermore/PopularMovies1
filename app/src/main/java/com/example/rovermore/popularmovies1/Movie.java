package com.example.rovermore.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
    }

    protected Movie(Parcel in) {
        originalTitle = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
