package com.example.giphyviewer.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.giphyviewer.models.GIFData;
import com.example.giphyviewer.models.GIFModel;
import com.example.giphyviewer.networking.GiphyAPI;
import com.example.giphyviewer.networking.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepositoryImpl implements RemoteRepository {

    private MutableLiveData<ArrayList<GIFData>> listMutableLiveData = new MutableLiveData<>();
    private LocalRepository localRepository = new LocalRepositoryImpl();

    public LiveData<ArrayList<GIFData>> getGifData() {
        return listMutableLiveData;
    }

    @Override
    public void loadTrendingGifs() {

        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);

        service.getTrendingGIFs().enqueue(new Callback<GIFModel>() {
            @Override
            public void onResponse(Call<GIFModel> call, Response<GIFModel> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.postValue(response.body().getGifData());
                    localRepository.saveGIFs(response.body().getGifData());
                }
            }

            @Override
            public void onFailure(Call<GIFModel> call, Throwable t) {
                int i = 0;
            }
        });
    }

    @Override
    public void searchGIF(String userInput) {
        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);

        service.searchGif(userInput, 10).enqueue(new Callback<GIFModel>() {
            @Override
            public void onResponse(Call<GIFModel> call, Response<GIFModel> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.postValue(response.body().getGifData());
                    localRepository.saveGIFs(response.body().getGifData());
                }
            }

            @Override
            public void onFailure(Call<GIFModel> call, Throwable t) {
                //TODO hendlati failure
            }
        });
    }


}