package com.example.nabil.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

    public class MainActivity extends AppCompatActivity
    {


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //get the activity fragment to main activity
            getFragmentManager().beginTransaction().add(android.R.id.content,new MoviesFragment()).commit();
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;


        }
    
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
        int id1=item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        if (id1==R.id.action_favoirate)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
