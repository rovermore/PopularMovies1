package com.example.rovermore.popularmovies1;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
@Entity(tableName = "favMovies")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private double voteAverage;
    private String releaseDate;
    //id from online database
    private int dbId;

    @Ignore
    public Movie(String originalTitle, String posterPath, String overview, double voteAverage, String releaseDate, int dbId){
        this.originalTitle=originalTitle;
        this.posterPath=posterPath;
        this.overview=overview;
        this.voteAverage=voteAverage;
        this.releaseDate=releaseDate;
        this.dbId=dbId;
    }

    public Movie(int id, String originalTitle, String posterPath, String overview, double voteAverage, String releaseDate, int dbId){
        this.id=id;
        this.originalTitle=originalTitle;
        this.posterPath=posterPath;
        this.overview=overview;
        this.voteAverage=voteAverage;
        this.releaseDate=releaseDate;
        this.dbId=dbId;
    }

    public int getId() { return id; }

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

    public int getDbId(){ return dbId; }

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
        dest.writeInt(dbId);
    }

    protected Movie(Parcel in) {
        originalTitle = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
        dbId = in.readInt();
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
