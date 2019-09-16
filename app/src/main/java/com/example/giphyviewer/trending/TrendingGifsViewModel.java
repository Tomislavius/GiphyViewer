package com.example.giphyviewer.trending;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.giphyviewer.models.GifData;
import com.example.giphyviewer.models.GifModel;
import com.example.giphyviewer.repositories.RemoteRepository;
import com.example.giphyviewer.repositories.RemoteRepositoryImpl;

import io.realm.RealmList;

public class TrendingGifsViewModel extends ViewModel {

    private RemoteRepository remoteRepository = new RemoteRepositoryImpl();

    public TrendingGifsViewModel() {
        loadTrendingGifs(0);
    }

    LiveData<RealmList<GifData>> getTrendingList() {
        return remoteRepository.getPaginationList();
    }

    void getResultsFromSearch(int offset, String userInput){
        remoteRepository.searchGIF(offset, userInput);
    }

    void loadTrendingGifs(int offset) {
        remoteRepository.loadTrendingGifs(offset);
    }

    LiveData<Boolean> getLoadingGifsError(){
        return remoteRepository.getLoadingGifsError();
    }

    LiveData<GifModel> getRefreshTrendingList() {
        return remoteRepository.getRefreshList();
    }

    LiveData<String> getResponseInformation(){
        return remoteRepository.getResponseInformation();
    }
}