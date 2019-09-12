package com.example.giphyviewer.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GIFModel {

    @SerializedName("data")
    private ArrayList<GIFData> gifData;

    public ArrayList<GIFData> getGifData() {
        return gifData;
    }

    public void setGifData(ArrayList<GIFData> gifData) {
        this.gifData = gifData;
    }
}