package com.example.rovermore.popularmovies1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.fetch_popular){
            title.setText(R.string.POPULAR);
            //fetch data with popular query
        }
        if(itemId==R.id.fetch_top_rated){
            title.setText(R.string.TOP_RATED);
            //fetch data with top_rated query
        }

        return super.onOptionsItemSelected(item);
    }
}
