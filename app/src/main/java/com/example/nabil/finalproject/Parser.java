package com.example.nabil.finalproject;



import com.example.nabil.finalproject.Models.ModelMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nabil on 4/5/2016.
 */
public class Parser {


    ArrayList<ModelMovies> parseDataFromJson(String string)
            throws JSONException {
        final String MDB_MOVIE_POSTER = "poster_path";
        final String MDB_Movie_title ="title";
        final String MDB_MOVIE_date = "release_date";
        final String Mdb_Movie_Desc = "overview";
        final String MDB_Movie_rate = "vote_average";
        final String results = "results";
        final String MDB_ID="id";


           ArrayList<ModelMovies> movies = new ArrayList<>();
            JSONObject movieListJson = new JSONObject(string);
          JSONArray movieArray = movieListJson.getJSONArray(results);

        String[] imageUrls = new String[movieArray.length()];
        for (int i = 0; i < movieArray.length(); i++) {

            ModelMovies modelMovies = new ModelMovies();
            JSONObject movie = movieArray.getJSONObject(i);
            String ID_Movie =movie.getString(MDB_ID);
            String photo_Movie = movie.getString(MDB_MOVIE_POSTER);
            String title_Movie = movie.getString(MDB_Movie_title);
            String date_Movie = movie.getString(MDB_MOVIE_date);
            String desc_Movie = movie.getString(Mdb_Movie_Desc);
            String  rate_Movie = movie.getString(MDB_Movie_rate);
            //-----------------------------------------
            modelMovies.setTitle(title_Movie);
            modelMovies.setDate(date_Movie);
            modelMovies.setDesc(desc_Movie);
            modelMovies.setRate(rate_Movie);
            modelMovies.setPhoto(photo_Movie);
            modelMovies.setID(ID_Movie);

            //------------------------------------------------------------
            movies.add(modelMovies);
        }

        return movies;
    }


    public String formatImageURL(String imageUrl)
    {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMG_SIZE = "w500/";
        return BASE_URL + IMG_SIZE + imageUrl;

    }


}
