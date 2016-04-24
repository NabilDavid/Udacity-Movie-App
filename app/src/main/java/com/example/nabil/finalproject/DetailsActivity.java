package com.example.nabil.finalproject;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nabil.finalproject.Models.ModelMovies;

public class DetailsActivity extends AppCompatActivity  {
    Parser parser = new Parser();
    ModelMovies movie;
    Task task;
  Fetch_reviews fetch_reviews=new Fetch_reviews(task);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        getSupportActionBar().setTitle("Details Activity");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DetailsFragment detailsFragment=new DetailsFragment();
        detailsFragment.setArguments(bundle);
        ft.add(R.id.details_cotainer, detailsFragment).commit();




    }

}
