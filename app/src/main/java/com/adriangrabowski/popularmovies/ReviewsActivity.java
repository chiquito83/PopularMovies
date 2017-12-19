package com.adriangrabowski.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        tvReviews = (TextView) findViewById(R.id.reviews_text_view);

        Intent intent = getIntent();

        if (intent == null) {
            finish();
        }

        movie = (Movie) intent.getSerializableExtra("MOVIE");

        ArrayList<Movie.Review> reviews = new ArrayList<>(movie.getReviews());

        for (Movie.Review review : reviews) {
            tvReviews.append("\n" + review.getAuthor() + ":" + "\n" + review.getContent() + "\n\n");
        }


    }
}
