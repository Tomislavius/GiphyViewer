package com.example.giphyviewer.repositories;

import com.example.giphyviewer.models.GifData;

import io.realm.RealmList;

public interface LocalRepository {

    void saveTrendingGifsToLocalDatabase(RealmList<GifData> gifData);

    RealmList<GifData> getTrendingGifsFromLocalDatabase();
}