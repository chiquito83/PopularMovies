package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private Button showReviewsButton;
    private LinearLayout trailersLayout;
    private Context context;

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
        showReviewsButton = (Button) findViewById(R.id.show_reviews_button);
        trailersLayout = (LinearLayout) findViewById(R.id.trailers_linear_layout);
        starCheckBox = (CheckBox) findViewById(R.id.star_checkbox);





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

        movie.clearTrailersAndReviews();

        new DetailsQueryTask().execute(NetworkUtils.buildMovieDetailsURL(this, movie.getId()));


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
        //favsHelper.close();
        super.onDestroy();
    }

    private class DetailsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL searchURL = urls[0];
            String searchResults = null;

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {



                try {
                    JSONArray reviewsArray = JSONMovieUtils.makeJSONReviewsArray(s);


                    for (int i=0; i < reviewsArray.length(); i++ ) {

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
                    JSONArray trailersArray = JSONMovieUtils.makeJSONTrailersArray(s);

                    for (int i=0; i < trailersArray.length(); i++) {

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

            }



            HashSet<Movie.Review> reviews = (HashSet<Movie.Review>) movie.getReviews();

            if (!reviews.isEmpty()) {
                showReviewsButton.setText("Show reviews (" + reviews.size() + ")");
                showReviewsButton.setVisibility(View.VISIBLE);
            }


            ArrayList<Movie.Trailer> movieTrailers = new ArrayList<>(movie.getTrailers());

            for (Movie.Trailer trailer : movieTrailers
                 ) {


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




        }
    }


}
