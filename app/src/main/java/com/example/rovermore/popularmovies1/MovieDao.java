package com.example.rovermore.popularmovies1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM favMovies WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);
}
