package com.example.jp0517.popularmovies.movie;

/**
 * Created by jp0517 on 1/9/18.
 */

public class ReviewInfo {
    private String m_author;
    private String m_content;

    public ReviewInfo(String author, String content) {
        m_author = author;
        m_content = content;
    }

    public String getAuthor() {
        return m_author;
    }

    public String getContent() {
        return m_content;
    }

}
