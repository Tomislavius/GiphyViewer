package com.example.giphyviewer.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Images extends RealmObject {
    @SerializedName("fixed_height")
    private FixedHeight fixedHeight;
    @SerializedName("preview_gif")
    private Preview preview;

    public FixedHeight getFixedHeight() {
        return fixedHeight;
    }

    public Preview getPreview() {
        return preview;
    }
}