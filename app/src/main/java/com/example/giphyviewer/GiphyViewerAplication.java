package com.example.giphyviewer;

import android.app.Application;

import io.realm.Realm;

public class GiphyViewerAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}