package com.example.giphyviewer.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.giphyviewer.Constants;
import com.example.giphyviewer.SingleLiveEvent;
import com.example.giphyviewer.models.GifData;
import com.example.giphyviewer.models.GifModel;
import com.example.giphyviewer.networking.GiphyAPI;
import com.example.giphyviewer.networking.ServiceGenerator;

import io.realm.RealmList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepositoryImpl implements RemoteRepository {

    private MutableLiveData<RealmList<GifData>> trendingList = new MutableLiveData<>();
    private MutableLiveData<GifModel> refreshTrendingList = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingGifsError = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> uploadFileSuccess = new SingleLiveEvent<>();
    private SingleLiveEvent<String> uploadFileError = new SingleLiveEvent<>();
    private SingleLiveEvent<String> responseInformation = new SingleLiveEvent<>();
    private LocalRepository localRepository = new LocalRepositoryImpl();

    @Override
    public LiveData<RealmList<GifData>> getTrendingList() {
        return trendingList;
    }

    @Override
    public LiveData<Boolean> getUploadFileSuccessResponse() {
        return uploadFileSuccess;
    }

    @Override
    public LiveData<String> getUploadFileErrorResponse() {
        return uploadFileError;
    }

    @Override
    public LiveData<GifModel> getRefreshTrendingList() {
        return refreshTrendingList;
    }

    @Override
    public LiveData<Boolean> getLoadingGifsError() {
        return loadingGifsError;
    }

    @Override
    public LiveData<String> getResponseInformation() {
        return responseInformation;
    }

    @Override
    public void loadTrendingGifs(final int offset) {
        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);

        service.getTrendingGifs(offset).enqueue(new Callback<GifModel>() {
            @Override
            public void onResponse(@NonNull Call<GifModel> call, @NonNull Response<GifModel> response) {
                // TODO: Add clear description to explain separation of 2 scenarios
                // 1. initial data loading and swipe to refresh
                // 2. paging loading data
                GifModel body = response.body();
                if (response.isSuccessful()) {
                    if (offset != 0) {
                        assert response.body() != null;
                        trendingList.postValue(response.body().getGifData());
                    } else {
                        //TODO Flag for adapter to decide is it list from trending or search API call
                        assert body != null;
                        body.setFromSearch(false);
                        refreshTrendingList.postValue(body);
                    }
                    assert response.body() != null;
                    localRepository.saveTrendingGifsToLocalDatabase(response.body().getGifData());
                    loadingGifsError.postValue(false);
                } else {
                    responseInformation.setValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<GifModel> call, @NonNull Throwable t) {
                trendingList.postValue(localRepository.getTrendingGifsFromLocalDatabase());
                loadingGifsError.postValue(true);
            }
        });
    }

    @Override
    public void searchGIF(final int offset, String userInput) {
        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);

        service.searchGif(userInput, offset).enqueue(new Callback<GifModel>() {
            @Override
            public void onResponse(@NonNull Call<GifModel> call, @NonNull Response<GifModel> response) {
                if (response.isSuccessful()) {
                    if (offset != 0) {
                        assert response.body() != null;
                        trendingList.postValue(response.body().getGifData());
                    } else {
                        GifModel body = response.body();
                        //TODO Flag for adapter to decide is it list from trending or search API call
                        assert body != null;
                        body.setFromSearch(true);
                        refreshTrendingList.postValue(body);
                    }
                } else {
                    responseInformation.setValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<GifModel> call, @NonNull Throwable t) {
                //TODO hendlati failure
                loadingGifsError.postValue(true);
            }
        });
    }

    @Override
    public void uploadFile(MultipartBody.Part body) {
        GiphyAPI service = ServiceGenerator.createService(GiphyAPI.class);
        service.uploadGIF(Constants.UPLOAD_FILE_URL, body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    uploadFileSuccess.postValue(true);
                } else {
                    uploadFileError.postValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                uploadFileError.postValue(t.getMessage());
            }
        });
    }
}