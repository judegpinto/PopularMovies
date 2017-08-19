package com.example.jp0517.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView poster;
    TextView title;
    TextView rating;
    TextView date;
    TextView summary;

    private final String base = getString(R.string.image_large);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        poster = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.rating);
        date = (TextView) findViewById(R.id.date);
        summary = (TextView) findViewById(R.id.summary);

        Intent intent = getIntent();
        MovieInfo movie = (MovieInfo) intent.getSerializableExtra(getString(R.string.EXTRA_MOVIE_INFO));
        title.setText(movie.getTitle());
        rating.setText(movie.getRating());
        date.setText(movie.getDate());
        summary.setText(movie.getSummary());

        Picasso.with(this).load(base+movie.getImageExt()).into(poster);
    }
}
