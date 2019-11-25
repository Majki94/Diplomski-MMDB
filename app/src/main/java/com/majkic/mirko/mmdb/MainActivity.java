package com.majkic.mirko.mmdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.majkic.mirko.mmdb.popular_movies.PopularMoviesFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackStack.setActivityReference(this);
        BackStack.presentFragment(PopularMoviesFragment.newInstance());

    }

    @Override
    public void onBackPressed() {
        BackStack.onBackPressed();
    }
}
