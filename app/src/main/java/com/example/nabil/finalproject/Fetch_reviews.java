package com.example.nabil.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.nabil.finalproject.Models.Review;

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

/**
 * Created by Nabil on 4/15/2016.
 */
public class Fetch_reviews extends AsyncTask<String,Void,String>
{

    private static final String LOG_TAG ="" ;
    private final Task task;
    Context context;
    public ArrayList<String>arrayListAuthor=new ArrayList<>();
    public ArrayList<String>arrayListContent=new ArrayList<>();
  public Fetch_reviews(Task task)
  {
      this.task=task;
  }

    protected String doInBackground(String... params) {
        String JsonResponse = null;
        try {

            if (params.length == 0)
                return null;

            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            JsonResponse = null;

            try {
                String baseUrl = "http://api.themoviedb.org/3/movie/";
                String id = params[0];
                String typeOfData = "/reviews";
                String apiKey = "?api_key=8d598e3c33037ea0c1172f7754ac6858";
                String link = baseUrl + id + typeOfData + apiKey;
                URL url = new URL(link);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                StringBuffer json = new StringBuffer();


                if (stream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json.append(line + "\n");
                }
                if (json.length() == 0)
                    return null;

                JsonResponse = json.toString();


            } catch (IOException e)
            {
                Log.e(LOG_TAG, "------>Error");
            }
            finally
            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Error closing stream", e);

                    }
                }
            }

            //finally check if not null to close


        } catch (Exception e) {
            Toast.makeText(context, "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return JsonResponse;
    }
    // take json string  to convert array list
    /// and return this array list
    private ArrayList<Review> getJson (String JsonResponse)
            throws JSONException
    {

        ArrayList<Review> bothArrays = new ArrayList<>();

        JSONObject  data = new JSONObject(JsonResponse);
        JSONArray results= data.getJSONArray("results");
        for (int i = 0 ; i < results.length(); i++)
        {
            JSONObject review = results.getJSONObject(i);
            Review review1 = new Review();
            review1.setAuthor(review.getString("author"));
            review1.setContent(review.getString("content"));
            review1.setLink(review.getString("url"));
            bothArrays.add(review1);

        }

        return bothArrays ;
    }
//convert arraylist to string and concat the string and return it


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ArrayList<Review> lastData= null;
        try {

            // get josn take string and convert it , put it in last data
            lastData = getJson(s);
    // that method finish by object task take the
            task.finish(lastData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
