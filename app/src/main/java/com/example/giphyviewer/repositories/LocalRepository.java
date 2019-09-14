package com.example.giphyviewer.repositories;

import com.example.giphyviewer.models.GIFData;

import io.realm.RealmList;

public interface LocalRepository {

    void saveGIFs(RealmList<GIFData> gifData);

    RealmList<GIFData> getGIFs();
}