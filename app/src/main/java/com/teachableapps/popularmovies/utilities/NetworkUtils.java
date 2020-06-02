package com.teachableapps.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private final static String BASE_URL = "http://image.tmdb.org/t/p/";

    private final static String WIDTH = "w185";
    private final static String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie";
    private final static String PARAM_API_KEY = "api_key";

    public static URL buildUrl(String movieSearchQuery, String apiKey) {
//        Log.d(TAG,"searhquery: " + movieSearchQuery);
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath(movieSearchQuery)
                .appendQueryParameter(PARAM_API_KEY,apiKey)
                .build();
//        Log.d(TAG,"URI: "+ builtUri.toString());

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

    public static String buildPosterUrl(String poster) {

        String finalPath = BASE_URL + WIDTH + "/" + poster;
//        Log.d(TAG, "Building PosterURL (" + poster + ") Final: " + finalPath);
        return finalPath;

    }

}
