package com.example.nabil.finalproject;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class MoviesFragment extends Fragment  {

        MoviesAdapter moviesAdapter;
    GridView gridView;
    //private MoviesAdapter mImages;


    public void onCreate(Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.content_movies_fragment, container, false);
        Fetch_Data fetch_data = new Fetch_Data(this);
        gridView = (GridView) view.findViewById(R.id.gridview);


        fetch_data.setTaskListener(new TaskListener() {
            @Override
            public void ontaskfinsh(ArrayList<ModelMovies> arrayList) {
                moviesAdapter = new MoviesAdapter(getActivity(), arrayList);
                gridView.setAdapter(moviesAdapter);

            }
        });
        fetch_data.execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelMovies movie = (ModelMovies) moviesAdapter.getItem(position);
//                ((Callback) getActivity()).onItemSelected(movie);

                Intent intent = new Intent(getActivity(), DetailsActivity.class).putExtra("nabil", (Parcelable) movie);
                startActivity(intent);

            }
        });

        return view;
    }
    //-----------------------------------------





}
