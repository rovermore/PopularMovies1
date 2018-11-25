package com.example.rovermore.popularmovies1;

public class Trailer {

    private int idDb;
    private String key;
    private String trailerName;

    public Trailer(String key, String trailerName){

        this.idDb=idDb;
        this.key=key;
        this.trailerName=trailerName;
    }

    public int getIdDb() {
        return idDb;
    }

    public String getKey() {
        return key;
    }

    public String getTrailerName() {
        return trailerName;
    }
}
