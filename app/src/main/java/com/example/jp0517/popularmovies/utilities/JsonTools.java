package com.example.jp0517.popularmovies.utilities;

import android.util.Log;

import com.example.jp0517.popularmovies.MovieInfo;

import org.json.*;

/**
 * Created by jp0517 on 8/13/17.
 */

public class JsonTools {
    private static final String getResults = "results";
    private static final String getTitle = "title";
    private static final String getPosterPath = "poster_path";
    private static final String getOverview = "overview";
    private static final String getVoteAverage = "vote_average";
    private static final String getReleaseDate = "release_date";


    public static MovieInfo[] getMovieInfo(String unparsedJson) {

        try {
            JSONObject moviesData = new JSONObject(unparsedJson);
            String jsonArrayUnparsed = moviesData.getString(getResults);
            JSONArray movieArray = new JSONArray(jsonArrayUnparsed);
            MovieInfo[] movies = new MovieInfo[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++) {
                String title = movieArray.getJSONObject(i).getString(getTitle);
                String image = movieArray.getJSONObject(i).getString(getPosterPath);
                String synopsis = movieArray.getJSONObject(i).getString(getOverview);
                String userRating = movieArray.getJSONObject(i).getString(getVoteAverage);
                String date = movieArray.getJSONObject(i).getString(getReleaseDate);
                movies[i] = new MovieInfo(title,image,synopsis,userRating,date);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
