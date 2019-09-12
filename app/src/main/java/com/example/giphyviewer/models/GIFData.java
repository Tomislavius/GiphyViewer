package com.example.giphyviewer.models;

import java.io.Serializable;

import io.realm.RealmObject;

public class GIFData {

    private Images images;

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}