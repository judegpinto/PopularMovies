package com.example.jp0517.popularmovies.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jp0517.popularmovies.R;
import com.example.jp0517.popularmovies.movie.ReviewInfo;

/**
 * Created by jp0517 on 1/9/18.
 */

public class ReviewAdapter extends BaseAdapter {
    private ReviewInfo[] m_reviews;
    private Context m_context;

    public ReviewAdapter(Context context) {
        m_context = context;
    }

    @Override
    public int getCount() {
        if(m_reviews !=null) {
            return m_reviews.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(m_reviews != null) {
            return m_reviews[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(m_context);
        View view = inflater.inflate(R.layout.review_view,null);
        TextView content = (TextView) view.findViewById(R.id.review_content);
        TextView author = (TextView) view.findViewById(R.id.review_author);
        content.setText(m_reviews[position].getContent());
        author.setText("-" + m_reviews[position].getAuthor());
        return view;
    }

    public void setReviewInfo(ReviewInfo[] reviews) {
        m_reviews = reviews;
        notifyDataSetChanged();
    }
}
