package com.adriangrabowski.popularmovies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Adrian on 26/11/2017.
 */

public final class FavouriteMovieContract {

    public static final String AUTHORITY = "com.adriangrabowski.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVOURITE_MOVIES = "movies";


    private FavouriteMovieContract() {
    }

    public static class FavouriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build();

        public static final String TABLE_NAME = "favourite_movie";
        public static final String COLUMN_NAME_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_OVERVIEW = "movie_overview";
        public static final String COLUMN_NAME_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_NAME_POSTER_URL = "movie_poster_path";
        public static final String COLUMN_NAME_MOVIE_RATING = "movie_rating";
    }
}
