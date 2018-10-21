package com.example.rovermore.popularmovies1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342";

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movieList){

        this.movieList=movieList;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView poster;

        public MyViewHolder(View itemView) {
            super(itemView);

            poster=itemView.findViewById(R.id.main_movie_poster);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);

        //String movieName = currentMovie.getOriginalTitle();

        String posterPath = currentMovie.getPosterPath();
        String url = NetworkUtils.posterUrlBuilder(posterPath);

        /*StringBuilder stringUrlBuilder = new StringBuilder();
        stringUrlBuilder.append(POSTER_BASE_URL);
        stringUrlBuilder.append(posterPath);
        String url = stringUrlBuilder.toString();*/

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.movie_image_placeholder)
                .error(R.drawable.movie_image_placeholder)
                .into(holder.poster);

        Log.v(TAG, "Requested URL " + url);
        Log.v(TAG, "Position " + position);
        Log.v(TAG, "Poster path " + posterPath);
        //Log.v(TAG, "title " + movieName);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
