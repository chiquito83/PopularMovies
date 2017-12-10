package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.adriangrabowski.popularmovies.database.QueryMethods;

public class FavouriteMovies extends AppCompatActivity {

    ListView listView;

    Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        context = this;



        listView = (ListView) findViewById(R.id.favourite_movies_list);


        Cursor cursorAll = QueryMethods.getAllMovies(context);

        FavouriteMovieAdapter favouriteMovieAdapter = new FavouriteMovieAdapter(this, cursorAll);

        listView.setAdapter(favouriteMovieAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                long id = (long) view.getTag();




                Cursor movieCursor = QueryMethods.getMovieWithId(context, id);



                Movie movie = getMovieFromCursor(movieCursor);









                Intent intent = new Intent(view.getContext(), DetailActivity.class);

                intent.putExtra("MOVIE", movie);





                startActivity(intent);



            }
        });

    }

    public Movie getMovieFromCursor(Cursor cursor) {

        long id;
        String title;
        String overview;
        String releaseDate;
        String posterPath;
        double rating;
        Movie returnMovie = null;

        while (cursor.moveToNext()) {

            id = cursor.getLong(cursor.getColumnIndexOrThrow("movie_id"));
            title = cursor.getString(cursor.getColumnIndexOrThrow("movie_title"));
            overview = cursor.getString(cursor.getColumnIndexOrThrow("movie_overview"));
            releaseDate = cursor.getString(cursor.getColumnIndexOrThrow("movie_release_date"));
            posterPath = cursor.getString(cursor.getColumnIndexOrThrow("movie_poster_path"));
            rating = cursor.getDouble(cursor.getColumnIndexOrThrow("movie_rating"));

            returnMovie = new Movie(id, title, posterPath, overview, releaseDate, rating, context);

        }


        return returnMovie;
    }
}
