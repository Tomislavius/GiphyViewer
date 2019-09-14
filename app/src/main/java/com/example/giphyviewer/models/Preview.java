package com.example.giphyviewer.models;

import io.realm.RealmObject;

public class Preview extends RealmObject {

    private String url;

    public String getUrl() {
        return url;
    }
}