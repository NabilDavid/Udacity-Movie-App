package com.example.nabil.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.nabil.finalproject.Models.ModelMovies;

public class MainActivity extends AppCompatActivity implements Movie_Listener {
    private boolean mTwoPane;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        FrameLayout fl_panel_2 = (FrameLayout) findViewById(R.id.fL_panel_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if (null == fl_panel_2) {
            mTwoPane = false;
        } else {
            mTwoPane = true;
        }

        if (null == savedInstanceState) {

            MoviesFragment moviesFragment = new MoviesFragment();
            moviesFragment.setListener(this);

            getFragmentManager().beginTransaction().replace(R.id.fL_panel_one, moviesFragment).commit();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void selected_movie(ModelMovies modelMovies) {

        if (mTwoPane) {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle extras = new Bundle();
            extras.putSerializable("nabil", modelMovies);
            detailsFragment.setArguments(extras);
            getFragmentManager().beginTransaction().replace(R.id.fL_panel_two, detailsFragment).commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("nabil", modelMovies);
            startActivity(intent);
        }
    }
}