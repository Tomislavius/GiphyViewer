package com.example.giphyviewer.trendinggifs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giphyviewer.models.GIFData;
import com.example.giphyviewer.repositories.RemoteRepositoryImpl;

import java.util.ArrayList;

public class TrendingGifsViewModel extends ViewModel {

    private RemoteRepositoryImpl remoteRepository = new RemoteRepositoryImpl();
    private MediatorLiveData<ArrayList<GIFData>> gifData = new MediatorLiveData<>();

    public LiveData<ArrayList<GIFData>> getData() {
        return remoteRepository.getGifData();
    }

    public void getResultsFromSearch(String userInput){
        remoteRepository.searchGIF(userInput);
    }

    public void loadTrendingGifs() {
        remoteRepository.loadTrendingGifs();
    }
}