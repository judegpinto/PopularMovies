package com.example.jp0517.popularmovies;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jp0517 on 8/17/17.
 */

public class MovieInfo implements Serializable {
    private String m_title;
    private String m_imageExt;
    private String m_synopsis;
    private String m_userRating;
    private String m_releaseDate;

    public MovieInfo(String title, String imageExt, String synopsis, String userRating, String releaseDate) {
        m_title=title;
        m_imageExt=imageExt;
        m_synopsis=synopsis;
        m_userRating=userRating;
        m_releaseDate=releaseDate;
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

}
