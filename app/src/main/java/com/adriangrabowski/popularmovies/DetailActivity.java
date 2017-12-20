package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adriangrabowski.popularmovies.database.QueryMethods;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class DetailActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView poster;
    private TextView title;
    private TextView overview;
    private TextView rating;
    private TextView releaseDate;
    private LinearLayout trailersLayout;
    private Context context;
    private TextView reviewsTextView;
    private TextView reviewsHeader;

    CheckBox starCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        poster = (ImageView) findViewById(R.id.detail_poster_image_view);
        title = (TextView) findViewById(R.id.detail_title_text_view);
        overview = (TextView) findViewById(R.id.detail_overview_text_view);
        rating = (TextView) findViewById(R.id.detail_user_rating_text_view);
        releaseDate = (TextView) findViewById(R.id.detail_release_date_text_view);
        trailersLayout = (LinearLayout) findViewById(R.id.trailers_linear_layout);
        starCheckBox = (CheckBox) findViewById(R.id.star_checkbox);
        reviewsTextView = (TextView) findViewById(R.id.reviews_text_view2);
        reviewsHeader = (TextView) findViewById(R.id.reviews_header);


        Intent intent = getIntent();


        if (intent == null) {
            finish();
        }


        movie = (Movie) intent.getSerializableExtra("MOVIE");


        Picasso.with(context).load(movie.getPosterUrl()).into(poster);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        rating.setText(movie.getRating().toString());
        releaseDate.setText(movie.getReleaseDate());

        //movie.clearTrailersAndReviews();

        //new DetailsQueryTask().execute(NetworkUtils.buildMovieDetailsURL(this, movie.getId()));

        URL[] trailersAndReviewsUrls = {NetworkUtils.buildFetchTrailersUrl(this, movie.getId()), NetworkUtils.buildFetchReviewsUrl(this, movie.getId())};

        new TrailersAndReviewsTask().execute(trailersAndReviewsUrls);


        if (QueryMethods.isFavourite(context, movie)) {
            starCheckBox.setChecked(true);
        } else {
            starCheckBox.setChecked(false);
        }


    }

    public void toggleFavouriteMovie(View view) {

        if (QueryMethods.isFavourite(context, movie)) {
            QueryMethods.removeFromFavourites(context, movie);
            starCheckBox.setChecked(false);
        } else {

            QueryMethods.addToFavourites(context, movie);
            starCheckBox.setChecked(true);
        }


    }

    public void showReviews(View view) {
        Intent intent = new Intent(context, ReviewsActivity.class);

        intent.putExtra("MOVIE", movie);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class TrailersAndReviewsTask extends AsyncTask<URL, Void, String[]> {

        @Override
        protected String[] doInBackground(URL... urls) {

            URL trailersUrl = urls[0];
            URL reviewsUrl = urls[1];

            String[] searchResults = new String[2];


            try {
                searchResults[0] = NetworkUtils.getResponseFromHttpUrl(trailersUrl);
                searchResults[1] = NetworkUtils.getResponseFromHttpUrl(reviewsUrl);


            } catch (IOException e) {
                e.printStackTrace();
            }


            return searchResults;
        }

        @Override
        protected void onPostExecute(String[] result) {


            if (result != null && result.length > 0) {

                if (!(result[0].equals("") || result[1].equals(""))) {


                    try {
                        JSONArray reviewsArray = JSONMovieUtils.makeJSONObjectArray(result[1]);


                        for (int i = 0; i < reviewsArray.length(); i++) {

                            JSONObject jo = reviewsArray.getJSONObject(i);

                            String id = jo.getString("id");
                            String author = jo.getString("author");
                            String content = jo.getString("content");


                            movie.addReview(id, author, content);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        JSONArray trailersArray = JSONMovieUtils.makeJSONObjectArray(result[0]);

                        for (int i = 0; i < trailersArray.length(); i++) {

                            JSONObject jo = trailersArray.getJSONObject(i);

                            String id = jo.getString("id");
                            String name = jo.getString("name");
                            String siteName = jo.getString("site");
                            String key = jo.getString("key");

                            movie.addTrailer(id, name, siteName, key);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    HashSet<Movie.Review> reviews = (HashSet<Movie.Review>) movie.getReviews();

                    if (!reviews.isEmpty()) {

                        reviewsHeader.setVisibility(View.VISIBLE);
                    }


                    ArrayList<Movie.Trailer> movieTrailers = new ArrayList<>(movie.getTrailers());

                    for (Movie.Trailer trailer : movieTrailers) {


                        if (trailer.getSiteName().equals("YouTube")) {


                            final Uri uri = NetworkUtils.buildYoutubeUrl(context, trailer.getKey());

                            Button trailerButton = new Button(context);
                            trailerButton.setText(trailer.getName());
                            trailerButton.setVisibility(View.VISIBLE);
                            trailerButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);

                                }
                            });

                            trailersLayout.addView(trailerButton);

                        }


                    }


                    for (Movie.Review review : reviews) {
                        reviewsTextView.append("\n" + review.getAuthor() + ":" + "\n" + review.getContent() + "\n\n");

                    }


                }

            }


        }
    }


}
