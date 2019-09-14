package com.example.giphyviewer.trendinggifs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.giphyviewer.models.GIFData;
import com.example.giphyviewer.repositories.RemoteRepositoryImpl;

import io.realm.RealmList;

public class TrendingGifsViewModel extends ViewModel {

    public TrendingGifsViewModel() {
        loadTrendingGifs();
    }

    private RemoteRepositoryImpl remoteRepository = new RemoteRepositoryImpl();

    LiveData<RealmList<GIFData>> getData() {
        return remoteRepository.getGifData();
    }

    void getResultsFromSearch(String userInput){
        remoteRepository.searchGIF(userInput);
    }

    void loadTrendingGifs() {
        remoteRepository.loadTrendingGifs();
    }

    LiveData<Boolean> getLoadingGifsError(){
        return remoteRepository.getLoadingGifsError();
    }
}