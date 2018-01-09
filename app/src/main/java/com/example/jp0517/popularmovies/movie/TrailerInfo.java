package com.example.jp0517.popularmovies.movie;

/**
 * Created by jp0517 on 12/16/17.
 */

public class TrailerInfo {

    private String m_name;
    private String m_key;
    private String m_site;

    public TrailerInfo(String name, String key, String site) {
        m_name = name;
        m_key = key;
        m_site = site;
    }

    public String getName() {
        return m_name;
    }

    public String getKey() {
        return m_key;
    }

}
