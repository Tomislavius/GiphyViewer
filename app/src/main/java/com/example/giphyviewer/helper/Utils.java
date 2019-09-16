package com.example.giphyviewer.helper;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.giphyviewer.R;

public class Utils {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static String getErrorMessage(Context context, String code) {
        switch (code) {
            case "400":
                return context.getString(R.string.error_400);
            case "403":
                return context.getString(R.string.error_403);
            case "404":
                return context.getString(R.string.error_404);
            case "429":
                return context.getString(R.string.error_429);
            default:
                return context.getString(R.string.default_error);
        }
    }
}