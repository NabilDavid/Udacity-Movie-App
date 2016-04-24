package com.example.nabil.finalproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Fetch_Data extends AsyncTask<String, Void, String> {
    private TaskListener taskListener;

    Fetch_Data(MoviesFragment activity) {
    }

    Parser parser = new Parser();

    public void setTaskListener(TaskListener taskListener)
    {
        this.taskListener = taskListener;
    }

    protected String doInBackground(String... params)
    {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = params[0];


            try {

                URL url = new URL(forecastJsonStr);

                // Create the request to OpenWeatherMap, and open the connection
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
                Log.d("json", forecastJsonStr);
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }


    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        try {
            parser.parseDataFromJson(s);
            taskListener.ontaskfinsh(parser.parseDataFromJson(s));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
