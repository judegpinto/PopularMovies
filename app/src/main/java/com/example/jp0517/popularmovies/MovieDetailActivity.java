package com.example.jp0517.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jp0517.popularmovies.movie.MovieInfo;
import com.example.jp0517.popularmovies.movie.TrailerInfo;
import com.example.jp0517.popularmovies.utilities.JsonTools;
import com.example.jp0517.popularmovies.utilities.NetworkUtils;
import com.example.jp0517.popularmovies.view.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title;
    private TextView rating;
    private TextView date;
    private TextView summary;
    private ListView listView;
    private TrailerAdapter trailerAdapter;
    private ProgressBar loadingProgress;
    private LinearLayout errorMessage;
    private ScrollView detailView;

    private String base;

    TrailerInfo[] m_trailers;

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
        listView = (ListView) findViewById(R.id.trailer_list);
        trailerAdapter = new TrailerAdapter(MovieDetailActivity.this);
        listView.setAdapter(trailerAdapter);

        Intent intent = getIntent();
        MovieInfo movie = (MovieInfo) intent.getSerializableExtra(getString(R.string.EXTRA_MOVIE_INFO));
        title.setText(movie.getTitle());
        rating.setText(movie.getRating());
        date.setText(movie.getDate());
        summary.setText(movie.getSummary());
        String movieId = movie.getMovieId();

        loadingProgress = (ProgressBar) findViewById(R.id.progress_detail);
        errorMessage = (LinearLayout) findViewById(R.id.detail_error_message);
        detailView = (ScrollView) findViewById(R.id.detail_view);

        loadDetails(movieId);

        Picasso.with(this).load(base+movie.getImageExt()).into(poster);
    }

    private class TrailerTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.makeMovieQuery(params[0]);
        }

        @Override
        protected void onPostExecute(String unparsed) {
            super.onPostExecute(unparsed);
            if(unparsed!=null) {
                Log.d(getClass().getSimpleName(),unparsed);
                m_trailers = JsonTools.getTrailerInfo(unparsed);
                trailerAdapter.setTrailerInfo(m_trailers);
                showDetail();
            } else {
                showErrorMessage();
            }
        }
    }

    private String getTrailerQueryString(String id) {
        return getString(R.string.api_base_url) +
                        id +
                        getString(R.string.videos_extension) +
                        getString(R.string.movie_key);
    }

    private void loadDetails(String movieId) {
        showProgress();
        new TrailerTask().execute(getTrailerQueryString(movieId));
    }

    private void showProgress()
    {
        detailView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
        loadingProgress.setVisibility(View.VISIBLE);
    }

    private void showDetail() {
        loadingProgress.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
        detailView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        detailView.setVisibility(View.INVISIBLE);
        loadingProgress.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }


}
