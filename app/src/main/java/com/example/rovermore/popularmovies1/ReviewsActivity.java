package com.example.rovermore.popularmovies1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private String idDb;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_activity);

        Intent intent = getIntent();
        idDb = intent.getStringExtra("id");

        new FetchReviews().execute(idDb);

    }


    private class FetchReviews extends AsyncTask<String, Void, List<Review>>{

        @Override
        protected List<Review> doInBackground(String... strings) {

            List<Review> reviewList = new ArrayList<>();

            URL url = NetworkUtils.reviewsUrlBuilder(strings[0]);

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);

                reviewList = NetworkUtils.parseJsonReview(jsonResponse);

                return reviewList;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);

            createUI(reviews);
        }
    }

    private void createUI(List<Review> reviews) {

        reviewsAdapter =new ReviewsAdapter(reviews,getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_reviews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(reviewsAdapter);

    }
}
