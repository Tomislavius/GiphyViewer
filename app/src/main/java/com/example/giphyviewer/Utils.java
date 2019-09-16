package com.example.giphyviewer;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static String getErrorMessage(String code) {
        switch (code) {
            case "400":
                return "Your request was formatted incorrectly or missing a required parameter(s).";
            case "403":
                return "You weren't authorized to make your request; most likely this indicates " +
                        "an issue with your API Key.";
            case "404":
                return "The particular GIF or Sticker you are requesting was not found. This occurs," +
                        " for example, if you request a GIF by using an id that does not exist.";
            case "429":
                return "Your API Key is making too many requests. " +
                        "Read about requesting a Production Key to upgrade your API Key rate limits.";
            default:
                return "Something went wrong!";
        }
    }
}