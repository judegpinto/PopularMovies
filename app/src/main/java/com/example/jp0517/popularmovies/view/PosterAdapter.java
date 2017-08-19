package com.example.jp0517.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jp0517.popularmovies.MovieDetailActivity;
import com.example.jp0517.popularmovies.MovieInfo;
import com.example.jp0517.popularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by jp0517 on 8/15/17.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private MovieInfo[] m_movies;
    private Context m_context;
    private String base;

    public PosterAdapter(Context context) {
        m_context = context;
        base = m_context.getString(R.string.image_small);
    }

    @Override
    public int getItemCount() {
        if(m_movies==null) {return 0;}
        return m_movies.length;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_poster,parent,false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        String imageExt = m_movies[position].getImageExt();
        Picasso.with(m_context).load(base+imageExt).into(holder.imageView);
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PosterViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.movie_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(m_context, MovieDetailActivity.class);
                    intent.putExtra(m_context.getString(R.string.EXTRA_MOVIE_INFO),m_movies[getAdapterPosition()]);
                    m_context.startActivity(intent);
                }
            });
        }
    }

    public void setMovieInfo(MovieInfo[] movies) {
        m_movies = movies;
        notifyDataSetChanged();
    }
}
