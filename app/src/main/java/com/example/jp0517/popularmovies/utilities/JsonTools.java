package com.example.jp0517.popularmovies.utilities;

import android.util.Log;

import org.json.*;

/**
 * Created by jp0517 on 8/13/17.
 */

public class JsonTools {

    public static String[] getMovieNames(String unparsedJson) {

        JSONObject moviesData = null;
        try {
            moviesData = new JSONObject(unparsedJson);
            String moviesJsonArray = moviesData.getString("results");
            JSONArray movieArray = new JSONArray(moviesJsonArray);
            String[] movieNames = new String[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject jsonObject = movieArray.getJSONObject(i);
                movieNames[i]=jsonObject.getString("poster_path");
            }
            return movieNames;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
