package com.example.jp0517.popularmovies.movie;

import java.io.Serializable;
import java.text.DateFormatSymbols;

/**
 * Created by jp0517 on 8/17/17.
 */

public class MovieInfo implements Serializable {
    private String m_title;
    private String m_imageExt;
    private String m_synopsis;
    private String m_userRating;
    private String m_releaseDate;
    private String m_movieId;

    public MovieInfo(String title,
                     String imageExt,
                     String synopsis,
                     String userRating,
                     String releaseDate,
                     String id) {
        m_title=title;
        m_imageExt=imageExt;
        m_synopsis=synopsis;
        m_userRating=parseRating(userRating);
        m_releaseDate=parseDate(releaseDate);
        m_movieId = id;
    }

    public String getImageExt() {
        return m_imageExt;
    }

    public String getTitle() {
        return m_title;
    }

    public String getSummary() {
        return m_synopsis;
    }

    public String getRating() {
        return m_userRating;
    }

    public String getDate() {
        return m_releaseDate;
    }

    public String getMovieId() {
        return m_movieId;
    }

    private String parseDate(String textDate) {
        int year = Integer.valueOf(textDate.substring(0,4));
        int month = Integer.valueOf(textDate.substring(5,7));
        int day = Integer.valueOf(textDate.substring(8,10));
        String monthString = new DateFormatSymbols().getMonths()[month-1];
        StringBuilder date = new StringBuilder();
        date.append(monthString)
                .append(" ")
                .append(day)
                .append(" ")
                .append(year);
        return date.toString();
    }

    private String parseRating(String textRating) {
        StringBuilder rating = new StringBuilder()
                .append("Rating: ")
                .append(textRating)
                .append("/10");
        return rating.toString();
    }
}
