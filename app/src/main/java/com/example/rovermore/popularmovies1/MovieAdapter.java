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
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);
        String posterPath = currentMovie.getPosterPath();
        String url = NetworkUtils.posterUrlBuilder(posterPath);

        Picasso.with(context)
                .load(url)
                .into(holder.poster);
        Log.v(TAG, "Recuested URL " + url);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
