package com.example.jp0517.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jp0517 on 12/18/17.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.jp0517.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_POSTER = "poster";

        public static final String COLUMN_THUMBNAIL = "thumbnail";

        public static final String COLUMN_SYNOPSIS = "synopsis";

        public static final String COLUMN_RATING = "rating";

        public static final String COLUMN_DATE = "date";

    }
}
