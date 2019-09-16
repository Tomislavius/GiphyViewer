package com.example.giphyviewer.repositories;

import com.example.giphyviewer.models.GifData;

import io.realm.Realm;
import io.realm.RealmList;

public class LocalRepositoryImpl implements LocalRepository {

    private Realm realm;

    LocalRepositoryImpl() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void saveTrendingGifsToLocalDatabase(RealmList<GifData> gifData) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(gifData);
        realm.commitTransaction();
    }

    @Override
    public RealmList<GifData> getTrendingGifsFromLocalDatabase() {
        RealmList <GifData> results = new RealmList<>();
        results.addAll(realm.where(GifData.class).findAll());
        return results;
    }
}