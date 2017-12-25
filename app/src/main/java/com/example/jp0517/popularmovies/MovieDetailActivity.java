package com.example.jp0517.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jp0517.popularmovies.data.MovieContract;
import com.example.jp0517.popularmovies.movie.MovieInfo;
import com.example.jp0517.popularmovies.movie.TrailerInfo;
import com.example.jp0517.popularmovies.utilities.JsonTools;
import com.example.jp0517.popularmovies.utilities.NetworkUtils;
import com.example.jp0517.popularmovies.view.TrailerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
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
    private TextView length;
    private ImageView favorite;
    private String movieId;

    private String base;
    private String imageExt;

    TrailerInfo[] m_trailers;

    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        poster = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.rating);
        date = (TextView) findViewById(R.id.date);
        summary = (TextView) findViewById(R.id.summary);
        length = (TextView) findViewById(R.id.length);
        listView = (ListView) findViewById(R.id.trailer_list);
        trailerAdapter = new TrailerAdapter(MovieDetailActivity.this);
        listView.setAdapter(trailerAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of list view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        loadingProgress = (ProgressBar) findViewById(R.id.progress_detail);
        errorMessage = (LinearLayout) findViewById(R.id.detail_error_message);
        detailView = (ScrollView) findViewById(R.id.detail_view);
        favorite = (ImageView) findViewById(R.id.favorite);


        isFavorite = isMovieFavorite(movieId);

        if(isFavorite) {
            Log.d("debug", "loading movie poster");
            Picasso.with(this).load(getMoviePath(movieId)).into(poster);
            Cursor moviesCursor = queryOffline();
            MovieInfo movieInfo = new MovieInfo(
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)),
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)),
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_SYNOPSIS)),
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)),
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE)),
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)),
                    moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL))
            );
        } else {
            Intent intent = getIntent();
            MovieInfo movie = (MovieInfo) intent.getSerializableExtra(getString(R.string.EXTRA_MOVIE_INFO));
            title.setText(movie.getTitle());
            rating.setText(movie.getRating());
            date.setText(movie.getDate());
            summary.setText(movie.getSummary());
            movieId = movie.getMovieId();
            base = getString(R.string.image_large);
            imageExt = movie.getImageExt();
            loadDetails(movieId);
            Picasso.with(this).load(base+imageExt).into(poster);
        }

        initializeFavoriteImage(isFavorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite) {
                    removeFavorite();
                } else {
                    setFavorite();
                }
            }
        });
    }

    private class TrailerTask extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String[] results = new String[2];
            results[0] = NetworkUtils.makeMovieQuery(params[0]);
            results[1] = NetworkUtils.makeMovieQuery(params[1]);
            return results;
        }

        @Override
        protected void onPostExecute(String[] unparsed) {
            super.onPostExecute(unparsed);
            if( (unparsed[0]!=null) && (unparsed[1]!=null)) {
                Log.d(getClass().getSimpleName(),unparsed[0]);
                m_trailers = JsonTools.getTrailerInfo(unparsed[0]);
                trailerAdapter.setTrailerInfo(m_trailers);

                Log.d(getClass().getSimpleName(),unparsed[1]);
                String runtime = JsonTools.getDetailInfo(unparsed[1]);
                length.setText(runtime + "mins");

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

    private String getDetailQueryString(String id) {
        return getString(R.string.api_base_url) +
                id +
                getString(R.string.movie_key);
    }

    private void loadDetails(String movieId) {
        showProgress();
        new TrailerTask().execute(getTrailerQueryString(movieId),getDetailQueryString(movieId));
    }

    //ui management
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

    //favoriting
    private void removeFavorite() {
        isFavorite = false;
        favorite.setImageDrawable(getDrawable(R.drawable.star_outline));

        Uri queryUri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movieId).build();
        Cursor queryMovie = getContentResolver().query(queryUri,null,null,null,null);
        int posterIndex = queryMovie.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);
        int thumbnailIndex = queryMovie.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL);
        if(queryMovie.moveToFirst()) {
            do {
                String path = queryMovie.getString(posterIndex);
                boolean deleted = new File(path).delete();
                Log.d("debug", "file deleted is " + deleted);
                String pathThumbnail = queryMovie.getString(thumbnailIndex);
                boolean deletedThumbnail = new File(pathThumbnail).delete();
                Log.d("debug", "file deleted is " + deletedThumbnail);
            } while (queryMovie.moveToNext());
        }
        queryMovie.close();
        Uri deleteUri = MovieContract.MovieEntry.CONTENT_URI;
        deleteUri = deleteUri.buildUpon().appendPath(movieId).build();
        getContentResolver().delete(deleteUri,null,null);
    }

    private void setFavorite() {
        isFavorite = true;
        addData(getApplicationContext());
        favorite.setImageDrawable(getDrawable(R.drawable.star_solid));
    }

    private void addData(Context context) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_ID,Integer.valueOf(movieId));
        values.put(MovieContract.MovieEntry.COLUMN_NAME,title.getText().toString());
        values.put(MovieContract.MovieEntry.COLUMN_DATE,date.getText().toString());
        values.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS,summary.getText().toString());
        values.put(MovieContract.MovieEntry.COLUMN_RATING,rating.getText().toString());

        File file = savePosterImage(context);
        File fileSmall = saveThumbnailImage(getString(R.string.image_small) + imageExt,context);

        values.put(MovieContract.MovieEntry.COLUMN_POSTER,file.getAbsolutePath());
        values.put(MovieContract.MovieEntry.COLUMN_THUMBNAIL,fileSmall.getAbsolutePath());

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

        if(uri == null) {
            throw new RuntimeException("Empty Return URI");
        }
    }

    boolean isMovieFavorite(String id) {
        String[] projection = new String[] {MovieContract.MovieEntry.COLUMN_ID};
        String selection = MovieContract.MovieEntry.COLUMN_ID + "=?";
        String[] selectionArgs = new String[] {id};
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        return ( (cursor != null) && (cursor.moveToFirst()) );
    }



    void initializeFavoriteImage(boolean isFavorite) {
        if(isFavorite) {
            favorite.setImageDrawable(getDrawable(R.drawable.star_solid));
        } else {
            favorite.setImageDrawable(getDrawable(R.drawable.star_outline));
        }
    }

    private File savePosterImage(Context context) {
        Bitmap bitmap = ((BitmapDrawable) poster.getDrawable()).getBitmap();
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File file = contextWrapper.getDir("Pictures", MODE_PRIVATE);
        file = new File(file, title.getText().toString() + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File saveThumbnailImage(String url, Context context) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File file = contextWrapper.getDir("Pictures", MODE_PRIVATE);
        file = new File(file, title.getText().toString() + "_small" + ".jpg");
        Picasso.with(this).load(url).into(picassoImageTarget(file));
        return file;
    }

    //load thumbnail from url
    private Target picassoImageTarget(final File file) {
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + file.getAbsolutePath());
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }

    private String getMoviePath(String id) {
        String[] projection = new String[] {MovieContract.MovieEntry.COLUMN_ID, MovieContract.MovieEntry.COLUMN_POSTER};
        String selection = MovieContract.MovieEntry.COLUMN_ID + "=?";
        String[] selectionArgs = new String[] {id};
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        if(cursor == null || !cursor.moveToFirst()) {
            return null;
        }

        return cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER));
    }

    private Cursor queryOffline(String id) {
        String[] projection = new String[] {MovieContract.MovieEntry.COLUMN_ID, MovieContract.MovieEntry.COLUMN_POSTER};
        String selection = MovieContract.MovieEntry.COLUMN_ID + "=?";
        String[] selectionArgs = new String[] {id};
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        if(cursor == null || !cursor.moveToFirst()) {
            return null;
        }
    }
}
