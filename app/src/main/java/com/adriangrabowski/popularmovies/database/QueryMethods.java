package com.adriangrabowski.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.adriangrabowski.popularmovies.Movie;

/**
 * Created by Adrian on 04/12/2017.
 */

public class QueryMethods {

    public static Cursor getAllMovies(Context context) {

        Cursor cursor;

        cursor = context.getContentResolver().query(
                FavouriteMovieContract.FavouriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );


        return cursor;

    }

    public static Cursor getMovieWithId(Context context, long id) {
        Cursor cursor;

        String selection = FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID + " LIKE ?";
        String selectionArgs[] = {Long.toString(id)};


        cursor = context.getContentResolver().query(
                FavouriteMovieContract.FavouriteMovieEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null
        );

        return cursor;

    }

    public static void addToFavourites(Context context, Movie movie) {

        ContentValues values = new ContentValues();

        values.put(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID, movie.getId());
        values.put(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_TITLE, movie.getTitle());
        values.put(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        values.put(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_RATING, movie.getRating());
        values.put(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        values.put(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_POSTER_URL, movie.getPosterUrl());

        context.getContentResolver().insert(FavouriteMovieContract.FavouriteMovieEntry.CONTENT_URI,
                values);

    }

    public static void removeFromFavourites(Context context, Movie movie) {

        Long id = movie.getId();

        Uri uri = FavouriteMovieContract.FavouriteMovieEntry.CONTENT_URI;

        uri = uri.buildUpon().appendPath(id.toString()).build();


        context.getContentResolver().delete(uri, null, null);

    }

    public static boolean isFavourite(Context context, Movie movie) {

        Cursor cursor = getMovieWithId(context, movie.getId());


        if (cursor == null || cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
