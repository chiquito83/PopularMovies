package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adriangrabowski.popularmovies.database.FavouriteMovieContract;

/**
 * Created by Adrian on 03/12/2017.
 */

public class FavouriteMovieAdapter extends CursorAdapter {
    public FavouriteMovieAdapter(Context context, Cursor cursor) {


        super(context, cursor, 0);


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.favourites_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView tvTitle = (TextView) view.findViewById(R.id.fav_movie_title);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_TITLE));
        Long id = cursor.getLong(cursor.getColumnIndexOrThrow(FavouriteMovieContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID));

        tvTitle.setText(title);

        view.setTag(new Long(id));

    }


}
