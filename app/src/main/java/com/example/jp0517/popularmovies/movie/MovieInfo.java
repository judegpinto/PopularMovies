package com.example.jp0517.popularmovies.movie;

import android.content.Context;
import android.util.Log;

import com.example.jp0517.popularmovies.R;

import java.io.Serializable;
import java.text.DateFormatSymbols;

/**
 * Created by jp0517 on 8/17/17.
 */

public class MovieInfo implements Serializable {
    public final String RATING_SUFFIX = "/10";
    private String m_title;
    private String m_imageExt;
    private String m_synopsis;
    private String m_userRating;
    private String m_releaseDate;
    private String m_movieId;
    private String m_thumbnail;

    public MovieInfo(String title,
                     String imageExt,
                     String synopsis,
                     String userRating,
                     String releaseDate,
                     String id,
                     String thumbnail) {
        m_title=title;
        m_imageExt=imageExt;
        m_synopsis=synopsis;
        m_userRating=userRating;
        m_releaseDate=releaseDate;
        m_movieId = id;
        m_thumbnail = thumbnail;
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

    public String getThumbnail() {
        return m_thumbnail;
    }
}
