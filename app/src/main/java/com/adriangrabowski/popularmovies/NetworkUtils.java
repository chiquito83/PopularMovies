package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * Created by Adrian on 03/10/2017.
 */

public class NetworkUtils {

    public static String TAG = "**** NetworkUtils ****";


    public static URL buildURL(Context context, String sortingType) {

        String apiKey = context.getString(R.string.api_key);
        String baseUrl = context.getString(R.string.base_url);


        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("api_key", apiKey)
                .appendEncodedPath(sortingType)
                .build();


        URL url = null;
        try {
            url = new URL(URLDecoder.decode(builtUri.toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;

    }


    public static URL buildFetchTrailersUrl(Context context, long movieId) {
        String apiKey = context.getString(R.string.api_key);
        String baseUrl = context.getString(R.string.base_url);

        String movieIdString = Long.toString(movieId);

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendEncodedPath(movieIdString)
                .appendEncodedPath("videos")
                .appendQueryParameter("api_key", apiKey)
                .build();

        URL url = null;

        try {

            url = new URL(URLDecoder.decode(builtUri.toString(), "UTF-8"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return url;

    }


    public static URL buildFetchReviewsUrl(Context context, long movieId) {
        String apiKey = context.getString(R.string.api_key);
        String baseUrl = context.getString(R.string.base_url);

        String movieIdString = Long.toString(movieId);

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendEncodedPath(movieIdString)
                .appendEncodedPath("reviews")
                .appendQueryParameter("api_key", apiKey)
                .build();

        URL url = null;

        try {

            url = new URL(URLDecoder.decode(builtUri.toString(), "UTF-8"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return url;
    }


    public static Uri buildYoutubeUrl(Context context, String youtubeKey) {
        String baseUrl = "https://www.youtube.com/watch";

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("v", youtubeKey)
                .build();
        URL url = null;

        try {
            url = new URL(URLDecoder.decode(builtUri.toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return builtUri;

    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
