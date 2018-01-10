package com.example.jp0517.popularmovies.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jp0517.popularmovies.R;
import com.example.jp0517.popularmovies.movie.TrailerInfo;
import com.example.jp0517.popularmovies.utilities.NetworkUtils;

/**
 * Created by jp0517 on 12/16/17.
 */

public class TrailerAdapter extends BaseAdapter {

    private TrailerInfo[] m_trailers;
    private Context m_context;

    public TrailerAdapter(Context context) {
        m_context = context;
    }

    @Override
    public int getCount() {
        if(m_trailers!=null) {
            return m_trailers.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(m_trailers != null) {
            return m_trailers[position];
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
        View view = inflater.inflate(R.layout.trailer_view,null);
        TextView name = (TextView) view.findViewById(R.id.trailer_name);
        name.setText(m_trailers[position].getName());
        ImageView play = (ImageView) view.findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.launchTrailer(m_context,m_context.getString(R.string.video_base_url),m_trailers[position].getKey());
            }
        });
        return view;
    }

    public void setTrailerInfo(TrailerInfo[] trailers) {
        m_trailers = trailers;
        notifyDataSetChanged();
    }
}
