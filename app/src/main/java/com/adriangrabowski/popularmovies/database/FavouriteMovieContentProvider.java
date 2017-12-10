package com.adriangrabowski.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Adrian on 29/11/2017.
 */

public class FavouriteMovieContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private FavouriteMovieDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();

        dbHelper = new FavouriteMovieDbHelper(context);

        return true;
    }

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavouriteMovieContract.AUTHORITY, FavouriteMovieContract.PATH_FAVOURITE_MOVIES, MOVIES);
        uriMatcher.addURI(FavouriteMovieContract.AUTHORITY, FavouriteMovieContract.PATH_FAVOURITE_MOVIES + "/#", MOVIE_WITH_ID);

        return uriMatcher;


    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {

            case MOVIES:

                retCursor = db.query(FavouriteMovieContract.FavouriteMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case MOVIE_WITH_ID:

                String id = uri.getPathSegments().get(1);

                String mSelection = FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID;
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(FavouriteMovieContract.FavouriteMovieEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);


                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);


        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;


        switch (match) {
            case MOVIES:
                long id = db.insert(FavouriteMovieContract.FavouriteMovieEntry.TABLE_NAME, null, contentValues);

                if (id > 0) {

                    returnUri = ContentUris.withAppendedId(FavouriteMovieContract.FavouriteMovieEntry.CONTENT_URI, id);

                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        getContext().getContentResolver().notifyChange(uri, null);


        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int moviesDeleted;


        switch (match) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);




                moviesDeleted = db.delete(FavouriteMovieContract.FavouriteMovieEntry.TABLE_NAME,
                        FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID + "=?",
                        new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (moviesDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }



        return moviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
