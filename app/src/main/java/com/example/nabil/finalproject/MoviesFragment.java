package com.example.nabil.finalproject;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nabil.finalproject.Adapters.MoviesAdapter;
import com.example.nabil.finalproject.Data.DataBaseController;
import com.example.nabil.finalproject.Models.ModelMovies;

import java.util.ArrayList;


public class MoviesFragment extends Fragment  {

        MoviesAdapter moviesAdapter;
        GridView gridView;
        private Fetch_Data fetch_data;
         Movie_Listener movie_listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

     {

         View view = inflater.inflate(R.layout.content_movies_fragment, container, false);
         fetch_data = new Fetch_Data(this);
          gridView = (GridView) view.findViewById(R.id.gridview);
         //----------------------------
         if (Check_Connection.isInternetConnected(getActivity()))
         {
             String s ="http://api.themoviedb.org/3/movie/popular?api_key=8d598e3c33037ea0c1172f7754ac6858";
             fetch_data.setTaskListener(new TaskListener() {
                 public void ontaskfinsh(ArrayList<ModelMovies> arrayList) {
                     moviesAdapter = new MoviesAdapter(getActivity(), arrayList);
                     gridView.setAdapter(moviesAdapter);
                 }
             });
             fetch_data.execute(s);


         }
         else {

             DataBaseController dataBaseController=new DataBaseController(getActivity());
             dataBaseController.openData();
             ArrayList<ModelMovies>array=new ArrayList<>();
             array=dataBaseController.getData();
             moviesAdapter=new MoviesAdapter(getActivity(),array);
             gridView.setAdapter(moviesAdapter);
             dataBaseController.close();
             Toast.makeText(getActivity(), "internet failed , you are in favoirate", Toast.LENGTH_SHORT).show();

         }

//----------------------------------------


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelMovies movie = (ModelMovies) moviesAdapter.getItem(position);
                movie_listener.selected_movie(movie);

            }
        });


        return view;
    }


 public  void setListener(Movie_Listener listener)
 {
     movie_listener=listener;
 }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_favoirate)
        {

            DataBaseController dataBaseController=new DataBaseController(getActivity());
            dataBaseController.openData();
            ArrayList<ModelMovies>array=new ArrayList<>();
            array=dataBaseController.getData();
            moviesAdapter=new MoviesAdapter(getActivity(),array);
            gridView.setAdapter(moviesAdapter);
            dataBaseController.close();

            return true;
        }
         Fetch_Data fetch_data=new Fetch_Data(this);
        if (id == R.id.action_popular)
        {
            if (Check_Connection.isInternetConnected(getActivity())) {
                String s = "http://api.themoviedb.org/3/movie/popular?api_key=8d598e3c33037ea0c1172f7754ac6858";
                fetch_data.setTaskListener(new TaskListener() {
                    public void ontaskfinsh(ArrayList<ModelMovies> arrayList) {
                        moviesAdapter = new MoviesAdapter(getActivity(), arrayList);
                        gridView.setAdapter(moviesAdapter);
                    }
                });

                fetch_data.execute(s);
                fetch_data = null;
            }
            else
            {
                Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

            return true;
        }



      if (id==R.id.action_rate)
        {
            if (Check_Connection.isInternetConnected(getActivity())) {
                final String s = "http://api.themoviedb.org/3/movie/top_rated?api_key=8d598e3c33037ea0c1172f7754ac6858";
                fetch_data.setTaskListener(new TaskListener() {
                    public void ontaskfinsh(ArrayList<ModelMovies> arrayList) {
                        moviesAdapter = new MoviesAdapter(getActivity(), arrayList);
                        gridView.setAdapter(moviesAdapter);
                    }
                });

                fetch_data.execute(s);
            }
            else
            {
                Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }



        return super.onOptionsItemSelected(item);
    }




}
