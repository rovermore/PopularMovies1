package com.example.rovermore.popularmovies1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private List<Trailer> trailerList;
    private Context context;
    private TrailerAdapterClickHandler mClickHandler;

    public TrailerAdapter(List<Trailer> trailerList, Context context, TrailerAdapterClickHandler mClickHandler){
        this.trailerList=trailerList;
        this.context=context;
        this.mClickHandler=mClickHandler;
    }

    public interface TrailerAdapterClickHandler{
        void onClickHandler(Trailer currentTrailer);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trailerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Trailer currentTrailer = trailerList.get(position);
            mClickHandler.onClickHandler(currentTrailer);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Trailer currentTrailer = trailerList.get(position);

        holder.trailerName.setText(currentTrailer.getTrailerName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
