package com.example.jp0517.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.jp0517.popularmovies.utilities.JsonTools;
import com.example.jp0517.popularmovies.utilities.NetworkUtils;
import com.example.jp0517.popularmovies.view.PosterAdapter;
import com.example.jp0517.popularmovies.movie.MovieInfo;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int width = 3;
    private PosterAdapter posterAdapter;
    private RecyclerView posterView;
    private ProgressBar progress;
    private LinearLayout errorMessage;
    Spinner sortOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posterView = (RecyclerView) findViewById(R.id.rv);
        progress = (ProgressBar) findViewById(R.id.progress);
        errorMessage = (LinearLayout) findViewById(R.id.errorMessage);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),width);
        posterView.setLayoutManager(layoutManager);
        posterView.setHasFixedSize(false);

        posterAdapter = new PosterAdapter(getApplicationContext());
        posterView.setAdapter(posterAdapter);

        showProgress();
        loadMoviesPopular();
    }

    private void showProgress()
    {
        progress.setVisibility(View.VISIBLE);
        posterView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showPosters() {
        progress.setVisibility(View.INVISIBLE);
        posterView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        posterView.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    public void loadMoviesPopular() {
        showProgress();
        String popularUrl = getString(R.string.api_base_url) + getString(R.string.option_popular) + getString(R.string.movie_key);
        new MovieTask().execute(popularUrl);
    }

    public void loadMoviesTopRated() {
        showProgress();
        String topRatedUrl = getString(R.string.api_base_url) + getString(R.string.option_top_rated) + getString(R.string.movie_key);
        new MovieTask().execute(topRatedUrl);
    }

    public class MovieTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.makeMovieQuery(params[0]);
        }

        @Override
        protected void onPostExecute(String unparsed) {
            super.onPostExecute(unparsed);
            if(unparsed!=null) {
                MovieInfo[] movies = JsonTools.getMovieInfo(unparsed);
                posterAdapter.setMovieInfo(movies);
                showPosters();
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.sort,menu);
        MenuItem item = menu.findItem(R.id.menuSort);
        sortOption = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sort_array,R.layout.support_simple_spinner_dropdown_item);
        sortOption.setAdapter(adapter);
        sortOption.setOnItemSelectedListener(this);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showProgress();
        switch (position) {
            case 0:
                loadMoviesPopular();
                break;
            case 1:
                loadMoviesTopRated();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
