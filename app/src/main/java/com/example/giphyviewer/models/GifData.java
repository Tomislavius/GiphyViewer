package com.example.giphyviewer.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GifData extends RealmObject {

    private Images images;
    @PrimaryKey
    private String id;

    public Images getImages() {
        return images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}