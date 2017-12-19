package com.adriangrabowski.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adrian on 03/10/2017.
 */

public final class JSONMovieUtils {


    public static JSONArray makeJSONObjectArray(String s) throws JSONException {

        JSONObject jsonObject = new JSONObject(s);

        JSONArray results = jsonObject.getJSONArray("results");


        return results;
    }

    public static JSONArray makeJSONReviewsArray(String s) throws JSONException {

        JSONArray reviews = null;


        JSONObject jsonObject = new JSONObject(s);

        JSONObject reviewsObject = jsonObject.getJSONObject("reviews");

        reviews = reviewsObject.getJSONArray("results");


        return reviews;
    }

    public static JSONArray makeJSONTrailersArray(String s) throws JSONException {
        JSONArray trailersArray = null;
        JSONObject jsonObject = new JSONObject(s);

        JSONObject trailersObject = jsonObject.getJSONObject("videos");

        trailersArray = trailersObject.getJSONArray("results");

        return trailersArray;
    }


}
