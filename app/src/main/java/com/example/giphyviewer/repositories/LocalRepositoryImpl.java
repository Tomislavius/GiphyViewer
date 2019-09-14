package com.example.giphyviewer.repositories;

import com.example.giphyviewer.models.GIFData;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LocalRepositoryImpl implements LocalRepository {

    private Realm realm;

    LocalRepositoryImpl() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void saveGIFs(RealmList<GIFData> gifData) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(gifData);
        realm.commitTransaction();
    }

    @Override
    public RealmList<GIFData> getGIFs() {
        RealmList <GIFData> results = new RealmList<>();
        results.addAll(realm.where(GIFData.class).findAll());
        return results;
    }
}