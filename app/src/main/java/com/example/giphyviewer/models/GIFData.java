package com.example.giphyviewer.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GIFData extends RealmObject {

    private Images images;
    @PrimaryKey
    private String id;

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}