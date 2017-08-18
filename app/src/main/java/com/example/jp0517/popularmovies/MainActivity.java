package com.example.jp0517.popularmovies;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.jp0517.popularmovies.utilities.JsonTools;
import com.example.jp0517.popularmovies.utilities.NetworkUtils;
import com.example.jp0517.popularmovies.view.PosterAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int width = 3;
    private PosterAdapter posterAdapter;
    private RecyclerView posterView;
    private String exampleUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posterView = (RecyclerView) findViewById(R.id.rv);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),width);
        posterView.setLayoutManager(layoutManager);
        posterView.setHasFixedSize(false);

        String query = getString(R.string.dicover_popular);
        String url = getString(R.string.api_base_url) + getString(R.string.movie_key) + query;
        //should look like: https://api.themoviedb.org/3/movie/popular?api_key=0af7c209fbb3336d2aee22d8442bbb4f
        exampleUrl = "https://api.themoviedb.org/3/movie/popular?api_key=0af7c209fbb3336d2aee22d8442bbb4f";
        Uri uri = Uri.parse(url);
        String uriString = uri.toString();
        Log.d(getClass().getSimpleName(),uriString);
        posterAdapter = new PosterAdapter(getApplicationContext());
        posterView.setAdapter(posterAdapter);
        loadMovies(exampleUrl);
    }

    public void loadMovies(String url) {
        new MovieTask().execute(url);
    }

    //TODO: Call this task so image urls are returned in string array, then process in onpostexcute
    public class MovieTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.makeMovieQuery(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MovieInfo[] movies = JsonTools.getMovieInfo(s);
            posterAdapter.setMovieInfo(movies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.movies,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_refresh) {
            loadMovies(exampleUrl);
        }
        return super.onOptionsItemSelected(item);
    }
}
