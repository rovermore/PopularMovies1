package com.example.rovermore.popularmovies1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rovermore.popularmovies1.datamodel.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favMovies")
    LiveData<List<Movie>> loadAllMovies();

    @Insert
    void insertMovie(Movie Movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie Movie);

    @Delete
    void deleteMovie(Movie Movie);

    @Query("DELETE FROM favMovies WHERE dbId = :dbId")
    int deleteBydbId(int dbId);

    @Query("SELECT * FROM favMovies WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);

    @Query("SELECT * FROM favMovies WHERE dbId = :dbId")
    Movie loadMovieByOnLineDbId(int dbId);

}
