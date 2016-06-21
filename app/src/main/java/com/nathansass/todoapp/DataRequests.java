package com.nathansass.todoapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nathansass on 6/20/16.
 */
public class DataRequests {

    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String TAG = DataRequests.class.getSimpleName();

    public void fetchImageUrlsInBackground(String searchTags, GetImageUrlsCallback callback) {
        new FetchImageUrlsAsyncTask(searchTags, callback).execute();
    }

    public class FetchImageUrlsAsyncTask extends AsyncTask<Void, Void, JSONArray> {

        String searchTags;
        GetImageUrlsCallback callback;
        JSONArray resultUrls;
        protected String apiKey = "53f0467d174b9080cbd9e2dd871e60d0";

        public FetchImageUrlsAsyncTask(String searchTags, GetImageUrlsCallback callback) {
            this.searchTags = searchTags;
            this.callback = callback;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {

            String urlStr = "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
                    "&api_key=" + apiKey +
                    "&tags=" + "cats" +
                    "&safe_search=1&per_page=10&format=json&nojsoncallback=1";

//            https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=53f0467d174b9080cbd9e2dd871e60d0&tags=cats"&safe_search=1&per_page=10&format=json&nojsoncallback=1";

            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");


                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                JSONObject objResults = new JSONObject(response.toString());

                resultUrls = objResults.getJSONObject("photos").getJSONArray("photo");

//                Log.v(TAG, resultUrls.toString());

                return resultUrls;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


            return resultUrls;
        }

        @Override
        protected void onPostExecute(JSONArray resultUrls) {
            super.onPostExecute(resultUrls);
            callback.done(resultUrls);
        }
    }
}
