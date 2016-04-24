package com.example.nabil.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        ModelMovies movie = intent.getParcelableExtra("nabil");

        ImageView mImageView = (ImageView) findViewById(R.id.detail_image);

        TextView mTitleView = (TextView) findViewById(R.id.detail_title);
        TextView mOverviewView = (TextView) findViewById(R.id.detail_desc);
        TextView mDateView = (TextView) findViewById(R.id.detail_date);


        mTitleView.setText(movie.title);


    }
}
