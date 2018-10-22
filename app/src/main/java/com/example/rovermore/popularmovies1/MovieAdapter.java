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
    private MovieAdapterClickHandler mClickhandler;

    public interface MovieAdapterClickHandler{
        void onClick(Movie currentMovie);
    }

    public MovieAdapter(Context context, List<Movie> movieList, MovieAdapterClickHandler clickHandler){

        this.movieList=movieList;
        this.context=context;
        mClickhandler=clickHandler;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView poster;

        public MyViewHolder(View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.main_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie currentMovie = movieList.get(position);
            mClickhandler.onClick(currentMovie);
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

        String posterPath = currentMovie.getPosterPath();
        String url = NetworkUtils.posterUrlBuilder(posterPath);

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.movie_image_placeholder)
                .error(R.drawable.movie_image_placeholder)
                .into(holder.poster);

        Log.v(TAG, "Requested URL " + url);
        Log.v(TAG, "Position " + position);
        Log.v(TAG, "Poster path " + posterPath);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
