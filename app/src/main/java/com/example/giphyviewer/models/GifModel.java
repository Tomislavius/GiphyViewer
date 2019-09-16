package com.example.giphyviewer.models;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmList;
import io.realm.RealmObject;

public class GifModel extends RealmObject {

    private boolean isFromSearch;
    @SerializedName("data")
    private RealmList<GifData> gifData;

    public RealmList<GifData> getGifData() {
        return gifData;
    }

    public boolean isFromSearch() {
        return isFromSearch;
    }

    public void setFromSearch(boolean fromSearch) {
        isFromSearch = fromSearch;
    }
}