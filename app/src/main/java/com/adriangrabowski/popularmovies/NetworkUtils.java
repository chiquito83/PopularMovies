package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Adrian on 03/10/2017.
 */

public class NetworkUtils {





    public static URL buildURL(Context context, String sortingType) {

        String apiKey = context.getString(R.string.api_key);
        String baseUrl =context.getString(R.string.base_url);

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("api_key", apiKey)
                .appendEncodedPath(sortingType)
                .build();





        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

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
