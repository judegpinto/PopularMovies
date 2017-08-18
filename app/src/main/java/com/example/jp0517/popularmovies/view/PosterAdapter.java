package com.example.jp0517.popularmovies.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jp0517.popularmovies.MainActivity;
import com.example.jp0517.popularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by jp0517 on 8/15/17.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private String[] m_images;
    private Context m_context;
    private final String base = "http://image.tmdb.org/t/p/w185";

    public PosterAdapter(Context context) {
        m_context = context;

    }

    @Override
    public int getItemCount() {
        if(m_images==null) {return 0;}
        return m_images.length;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("adapter","creating view");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_poster,parent,false);
        PosterViewHolder posterViewHolder = new PosterViewHolder(view);
        return posterViewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        Log.d("adapter","loading image");
        Picasso.with(m_context).load(base+m_images[position]).into(holder.imageView);
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PosterViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.movie_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String movie = m_images[getAdapterPosition()];

                }
            });
        }

    }

    public void setImages(String[] images) {
        m_images=images;
        notifyDataSetChanged();
    }
}
