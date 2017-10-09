package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView poster;
    private TextView title;
    private TextView overview;
    private TextView rating;
    private TextView releaseDate;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = getApplicationContext();

        poster = (ImageView) findViewById(R.id.detail_poster_image_view);
        title = (TextView) findViewById(R.id.detail_title_text_view);
        overview = (TextView) findViewById(R.id.detail_overview_text_view);
        rating = (TextView) findViewById(R.id.detail_user_rating_text_view);
        releaseDate = (TextView) findViewById(R.id.detail_release_date_text_view);



        Intent intent = getIntent();

        movie = (Movie) intent.getSerializableExtra("MOVIE");

        Picasso.with(context).load(movie.getPosterUrl()).into(poster);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        rating.setText(movie.getRating().toString());
        releaseDate.setText(movie.getReleaseDate());




    }
}
