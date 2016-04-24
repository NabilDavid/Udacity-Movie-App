package com.example.nabil.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nabil on 4/5/2016.
 */

public class MoviesAdapter extends BaseAdapter {
    Parser parser = new Parser();
    Context mconext;
    private final LayoutInflater Inflater;
    public ArrayList<ModelMovies> arraylist;

    public MoviesAdapter(Context context, ArrayList<ModelMovies> arraylist) {
        this.Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = arraylist;
        this.mconext = context;
    }


    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = Inflater.inflate(R.layout.cellview, parent, false);
        ImageView imageview = (ImageView) view.findViewById(R.id.imageView);
        ModelMovies modelMovies = arraylist.get(position);
        Picasso.with(mconext).load(parser.formatImageURL(modelMovies.getPhoto())).into(imageview);
        //http://image.tmdb.org/t/p/w185/6bCplVkhowCjTHXWv49UjRPn0eK.jpg

 //-------------------------------------------------------

        return view;
    }
}
