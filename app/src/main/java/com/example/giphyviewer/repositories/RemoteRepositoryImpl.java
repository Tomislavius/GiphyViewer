package com.example.giphyviewer.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.giphyviewer.Constants;
import com.example.giphyviewer.SingleLiveEvent;
import com.example.giphyviewer.models.GIFData;
import com.example.giphyviewer.models.GIFModel;
import com.example.giphyviewer.networking.GiphyAPI;
import com.example.giphyviewer.networking.ServiceGenerator;

import java.util.ArrayList;

import io.realm.RealmList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepositoryImpl implements RemoteRepository {

    private MutableLiveData<RealmList<GIFData>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingGifsError = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> uploadFileSuccess = new SingleLiveEvent<>();
    private SingleLiveEvent<String> uploadFileError = new SingleLiveEvent<>();
    private LocalRepository localRepository = new LocalRepositoryImpl();

    public LiveData<RealmList<GIFData>> getGifData() {
        return listMutableLiveData;
    }

    public LiveData<Boolean> getUploadFileSuccessResponse() {
        return uploadFileSuccess;
    }

    public LiveData<String> getUploadFileErrorResponse() {
        return uploadFileError;
    }

    public LiveData<Boolean> getLoadingGifsError(){
        return loadingGifsError;
    }

    @Override
    public void loadTrendingGifs() {

        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);

        service.getTrendingGIFs(0,20).enqueue(new Callback<GIFModel>() {
            @Override
            public void onResponse(Call<GIFModel> call, Response<GIFModel> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.postValue(response.body().getGifData());
                    localRepository.saveGIFs(response.body().getGifData());
                    loadingGifsError.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<GIFModel> call, Throwable t) {
                listMutableLiveData.postValue(localRepository.getGIFs());
                loadingGifsError.postValue(true);
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

    @Override
    public void uploadFile(MultipartBody.Part body) {
        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);
        service.uploadGIF(Constants.UPLOAD_FILE_URL, body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    uploadFileSuccess.postValue(true);
                } else {
                    uploadFileError.postValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                uploadFileError.postValue(t.getMessage());
            }
        });
    }
}