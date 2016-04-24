package com.example.nabil.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nabil.finalproject.R;
import com.example.nabil.finalproject.Models.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nabil on 4/17/2016.
 */
public class ReviewAdapter extends BaseAdapter
{
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Review> mObjects ;

    public ReviewAdapter(Context context, ArrayList<Review> objects) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObjects = objects;
    }

    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Review getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = mInflater.inflate(R.layout.cell_review, parent, false);
        Review modelMovies = mObjects.get(position);
        TextView textView = (TextView) view.findViewById(R.id.review_author);
        TextView textView1 = (TextView) view.findViewById(R.id.review_content);
        TextView textView2=   (TextView) view.findViewById(R.id.review_link);
        textView.setText(modelMovies.getAuthor());
        textView1.setText(modelMovies.getContent());
        textView2.setText(modelMovies.getLink());


        return view;
    }
    }
