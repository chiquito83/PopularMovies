package com.adriangrabowski.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adriangrabowski.popularmovies.database.FavouriteMovieContract.FavouriteMovieEntry;


/**
 * Created by Adrian on 26/11/2017.
 */

public class FavouriteMovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavouriteMovie.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavouriteMovieEntry.TABLE_NAME + " (" +
                    FavouriteMovieEntry._ID + " INTEGER PRIMARY KEY," +
                    FavouriteMovieEntry.COLUMN_NAME_MOVIE_TITLE + " TEXT," +
                    FavouriteMovieEntry.COLUMN_NAME_OVERVIEW + " TEXT," +
                    FavouriteMovieEntry.COLUMN_NAME_RELEASE_DATE + " TEXT," +
                    FavouriteMovieEntry.COLUMN_NAME_POSTER_URL + " TEXT," +
                    FavouriteMovieEntry.COLUMN_NAME_MOVIE_RATING + " FLOAT," +
                    FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID + " BIGINT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavouriteMovieEntry.TABLE_NAME;


    public FavouriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
