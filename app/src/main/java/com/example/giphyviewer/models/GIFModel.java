package com.example.giphyviewer.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GIFModel extends RealmObject {

    @SerializedName("data")
    private RealmList<GIFData> gifData;

    public RealmList<GIFData> getGifData() {
        return gifData;
    }
}