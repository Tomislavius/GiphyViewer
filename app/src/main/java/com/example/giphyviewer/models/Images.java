package com.example.giphyviewer.models;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("fixed_height")
    private FixedHeight fixedHeight;

    public FixedHeight getFixedHeight() {
        return fixedHeight;
    }

    public void setFixedHeight(FixedHeight fixedHeight) {
        this.fixedHeight = fixedHeight;
    }
}