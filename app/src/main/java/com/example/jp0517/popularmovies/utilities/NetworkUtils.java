package com.example.jp0517.popularmovies.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by jp0517 on 8/13/17.
 */

public class NetworkUtils {

    public static String makeMovieQuery(String urlString) {
        try {
            URL url = new URL(urlString);
            return getResponseFromHttpUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void launchTrailer(Context context, String base, String key) {
        String urlString = base + key;
        Intent videoIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
        if(videoIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(videoIntent);
        }
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int millisecondsTimeout = 5000;
        urlConnection.setConnectTimeout(millisecondsTimeout);
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
