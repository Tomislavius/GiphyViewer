package com.example.giphyviewer.repositories;

import com.example.giphyviewer.models.GIFData;

import java.util.ArrayList;

import io.realm.Realm;

public class LocalRepositoryImpl implements LocalRepository {

    private Realm realm;

    public LocalRepositoryImpl() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void saveGIFs(ArrayList<GIFData> gifData) {
/*        realm.beginTransaction();
        realm.insertOrUpdate((RealmModel) gifData);
        realm.commitTransaction();*/
    }

    @Override
    public void getGIFs() {

    }
}
