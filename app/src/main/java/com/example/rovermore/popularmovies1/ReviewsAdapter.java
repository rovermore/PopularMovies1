package com.example.rovermore.popularmovies1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyReviewsViewHolder> {

    private List<Review> reviewsList;
    private Context context;

    public ReviewsAdapter(List<Review> reviewList, Context context){
        this.reviewsList=reviewList;
        this.context=context;
    }


    @Override
    public MyReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item_list,parent,false);
        return new MyReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyReviewsViewHolder holder, int position) {

        Review currentReview = reviewsList.get(position);

        holder.author.setText(currentReview.getAuthor());
        holder.content.setText(currentReview.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }


    public class MyReviewsViewHolder extends RecyclerView.ViewHolder{

        public TextView author;
        public TextView content;

        public MyReviewsViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.tv_author);
            content = itemView.findViewById(R.id.tv_content);

        }
    }

}
