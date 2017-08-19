package com.example.jp0517.popularmovies;

import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView poster;
    TextView title;
    TextView rating;
    TextView date;
    TextView summary;

    private String base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        base = getString(R.string.image_large);

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
