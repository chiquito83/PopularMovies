package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Adrian on 04/10/2017.
 */

public class Movie implements Serializable {

    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String posterUrl;
    private Double rating;

    public Movie(JSONObject jsonObject, Context context)  {




        try {
            title = jsonObject.getString("title");
            posterPath = jsonObject.getString("poster_path");
            overview = jsonObject.getString("overview");
            releaseDate = jsonObject.getString("release_date");
            rating = jsonObject.getDouble("vote_average");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        String baseUrl = context.getString(R.string.base_url_for_poster_image);
        String size = context.getString(R.string.poster_image_size);

        Uri myUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(size).appendEncodedPath(posterPath).build();

        posterUrl = myUri.toString();

    }

    public String getPosterUrl(){
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getRating() {
        return rating;
    }





}
