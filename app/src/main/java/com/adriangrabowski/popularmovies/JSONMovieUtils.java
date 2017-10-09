package com.adriangrabowski.popularmovies;

import android.util.Log;

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




}
