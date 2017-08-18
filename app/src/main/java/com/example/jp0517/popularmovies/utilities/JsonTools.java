package com.example.jp0517.popularmovies.utilities;

import android.util.Log;

import com.example.jp0517.popularmovies.MovieInfo;

import org.json.*;

/**
 * Created by jp0517 on 8/13/17.
 */

public class JsonTools {

    public static MovieInfo[] getMovieInfo(String unparsedJson) {
        try {
            JSONObject moviesData = new JSONObject(unparsedJson);
            String jsonArrayUnparsed = moviesData.getString("results");
            JSONArray movieArray = new JSONArray(jsonArrayUnparsed);
            MovieInfo[] movies = new MovieInfo[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++) {
                String title = movieArray.getJSONObject(i).getString("title");
                String image = movieArray.getJSONObject(i).getString("poster_path");
                String synopsis = movieArray.getJSONObject(i).getString("overview");
                String userRating = movieArray.getJSONObject(i).getString("vote_average");;
                String date = movieArray.getJSONObject(i).getString("release_date");
                movies[i] = new MovieInfo(title,image,synopsis,userRating,date);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

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
