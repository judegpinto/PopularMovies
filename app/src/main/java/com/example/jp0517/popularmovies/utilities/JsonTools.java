package com.example.jp0517.popularmovies.utilities;

import com.example.jp0517.popularmovies.movie.MovieInfo;
import com.example.jp0517.popularmovies.movie.ReviewInfo;
import com.example.jp0517.popularmovies.movie.TrailerInfo;

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
    private static final String getMovieId = "id";

    private static final String getVideoKey = "key";
    private static final String getVideoName = "name";
    private static final String getSite = "site";

    private static final String getMovieLength = "runtime";

    private static final String getAuthor = "author";
    private static final String getContent = "content";

    public static MovieInfo[] getMovieInfo(String unparsedJson) {

        try {
            JSONObject moviesData = new JSONObject(unparsedJson);
            String jsonArrayUnparsed = moviesData.getString(getResults);
            JSONArray movieArray = new JSONArray(jsonArrayUnparsed);
            MovieInfo[] movies = new MovieInfo[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObject = movieArray.getJSONObject(i);
                String title = movieObject.getString(getTitle);
                String image = movieObject.getString(getPosterPath);
                String synopsis = movieObject.getString(getOverview);
                String userRating = movieObject.getString(getVoteAverage);
                String date = movieObject.getString(getReleaseDate);
                String id = movieObject.getString(getMovieId);
                movies[i] = new MovieInfo(title,image,synopsis,userRating,date,id,null);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TrailerInfo[] getTrailerInfo(String unparsedMovie) {
        try {
            JSONObject movieData = new JSONObject(unparsedMovie);
            String jsonArrayUnparsed = movieData.getString(getResults);
            JSONArray movieArray = new JSONArray(jsonArrayUnparsed);
            TrailerInfo[] trailers = new TrailerInfo[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObject = movieArray.getJSONObject(i);
                String key = movieObject.getString(getVideoKey);
                String name = movieObject.getString(getVideoName);
                String site = movieObject.getString(getSite);
                trailers[i] = new TrailerInfo(name, key, site);
            }
            return trailers;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ReviewInfo[] getReviewInfo(String unparsedReviewInfo) {
        try {
            JSONObject reviewData = new JSONObject(unparsedReviewInfo);
            String jsonArrayUnparsed = reviewData.getString(getResults);
            JSONArray reviewArray = new JSONArray(jsonArrayUnparsed);
            ReviewInfo[] reviews = new ReviewInfo[reviewArray.length()];
            for(int i = 0; i < reviewArray.length(); i++) {
                JSONObject reviewObject = reviewArray.getJSONObject(i);
                String author = reviewObject.getString(getAuthor);
                String content = reviewObject.getString(getContent);
                reviews[i] = new ReviewInfo(author, content);
            }
            return reviews;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //this function is only used to get runtime
    //a wrapper class is needed for more than 1 parsed JSON item
    public static String getDetailInfo(String unparsedMovie) {
        try {
            JSONObject movieData = new JSONObject(unparsedMovie);
            String length = movieData.getString(getMovieLength);
            return length;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
