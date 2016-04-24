package com.example.nabil.finalproject;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabil.finalproject.Adapters.ReviewAdapter;
import com.example.nabil.finalproject.Data.DataBaseController;
import com.example.nabil.finalproject.Models.ModelMovies;
import com.example.nabil.finalproject.Models.Review;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DetailsFragment extends Fragment implements Task
{
    ModelMovies movie=new ModelMovies();
    Task task;
    Fetch_reviews fetch_reviews;
    Parser parser;
    TextView textViewReview;
    ImageView Trailer_Button;
    ListView listView;
    FetchTrailer fetchTrailer;

    String YOUTUBE_BASE_URL="http://www.youtube.com/watch?v=";
    String getTrailer_uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        movie= (ModelMovies) getArguments().getSerializable("nabil");

        RatingBar mRateView = (RatingBar) view.findViewById(R.id.ratingBar);
        ImageView mImageView = (ImageView)view.findViewById(R.id.detail_image);
        TextView mTitleView = (TextView) view.findViewById(R.id.detail_title);
        Trailer_Button= (ImageView) view.findViewById(R.id.button_trailer);
        TextView mOverviewView = (TextView) view.findViewById(R.id.detail_desc);
        TextView mDateView = (TextView) view.findViewById(R.id.detail_date);
        textViewReview =(TextView)view.findViewById(R.id.review);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        listView= (ListView) view.findViewById(R.id.review_list);


        //-------------------------------

        parser=new Parser();
        Picasso.with(getActivity()).load(parser.formatImageURL(movie.getPhoto())).into(mImageView);
        Toast.makeText(getActivity(), "Rate is " + movie.getRate(), Toast.LENGTH_SHORT).show();
        mRateView.setRating((Float.parseFloat(movie.getRate())/2));
                mDateView.setText(movie.getDate());
        mTitleView.setText(movie.getTitle());
        mOverviewView.setText(movie.getDesc());

        imageView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.star2);
                DataBaseController dataBaseController = new DataBaseController(getActivity());
                dataBaseController.openData();
                dataBaseController.insertDataBase(movie);
                dataBaseController.close();
            }
        });



        if (Check_Connection.isInternetConnected(getActivity()))
        {

                fetch_reviews = new Fetch_reviews(this);

                fetchTrailer = new FetchTrailer();
                String S = movie.getID();
                fetch_reviews.execute(S);
               fetchTrailer.execute(S);



        }
        else
        {
            // if no internet connection
            Toast.makeText(getActivity(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return view;
    }


    // the function come with ArrayList (Review ) and put it to List in xml to revirew the user


    @Override
    public void finish(ArrayList<Review> s) {

        ReviewAdapter reviewAdapter=new ReviewAdapter(getActivity(),s);
        listView.setAdapter(reviewAdapter);

    }



    public class FetchTrailer extends AsyncTask<String, Void, String>
    {

        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            try {

                URL url = new URL("http://api.themoviedb.org/3/movie/" + params[0] + "/videos?api_key=3eaf25965e78bf12e550365b00e183a7");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {

                return null;
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try
            {
                return JsonTrailer(forecastJsonStr);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String list) {

            watchYoutubeVideo(list);
            getTrailer_uri=list;
            super.onPostExecute(list);
        }
    }
    public void watchYoutubeVideo(final String id)
    {
        Trailer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id == null) {
                    Toast.makeText(getActivity(), "No Review or Trailer found ", Toast.LENGTH_SHORT).show();
                    Trailer_Button.setVisibility(View.GONE);

                } else
                {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException ex) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + id));
                        startActivity(intent);
                    }
                }
            }
        });
    }
    public String JsonTrailer(String forecastJsonStr)
            throws JSONException {
        try {
            JSONObject jsonRootObject = new JSONObject(forecastJsonStr);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonMoviesArray = jsonRootObject.optJSONArray("results");
            String code = "";
            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                JSONObject jsonObject = jsonMoviesArray.getJSONObject(i);
                String type = jsonObject.optString("type");

                if (type.equalsIgnoreCase("trailer"))
                    code = jsonObject.optString("key");
                Log.v("Nabil", code);
            }
            return code;
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
            return null;
        }

    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        inflater.inflate(R.menu.share_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();


        if (id == R.id.action_share)
        {


            if(getTrailer_uri==null)
            {
                Toast.makeText(getActivity(), " bad connection", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "From Movies app ");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Movie Name: " + movie.title + "\nRelease Data: " + movie.getDate() +
                        "\nTrailer Link: " + YOUTUBE_BASE_URL + getTrailer_uri + "\nDescription:\n" + movie.getDesc()+ "\n");
                sendIntent.setType("text/plain");
                String title = "Share …";
                startActivity(Intent.createChooser(sendIntent, title));

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}







