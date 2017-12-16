package com.example.jp0517.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jp0517.popularmovies.movie.MovieInfo;
import com.example.jp0517.popularmovies.movie.TrailerInfo;
import com.example.jp0517.popularmovies.utilities.JsonTools;
import com.example.jp0517.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

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
        String movieId = movie.getMovieId();

        new TrailerTask().execute(getTrailerQueryString(movieId));

        Picasso.with(this).load(base+movie.getImageExt()).into(poster);
    }

    public class TrailerTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.makeMovieQuery(params[0]);
        }

        @Override
        protected void onPostExecute(String unparsed) {
            super.onPostExecute(unparsed);
            if(unparsed!=null) {
                Log.d(getClass().getSimpleName(),unparsed);
                TrailerInfo[] trailers = JsonTools.getTrailerInfo(unparsed);
            } else {

            }
        }
    }

    private String getTrailerQueryString(String id) {
        return getString(R.string.api_base_url) +
                        id +
                        getString(R.string.videos_extension) +
                        getString(R.string.movie_key);
    }
}
