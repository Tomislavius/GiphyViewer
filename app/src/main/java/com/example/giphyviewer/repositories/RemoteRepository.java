package com.example.giphyviewer.repositories;

import androidx.lifecycle.LiveData;

import com.example.giphyviewer.models.GifData;
import com.example.giphyviewer.models.GifModel;

import io.realm.RealmList;
import okhttp3.MultipartBody;

public interface RemoteRepository {

    LiveData<RealmList<GifData>> getPaginationList();

    LiveData<Boolean> getUploadFileSuccessResponse();

    LiveData<String> getUploadFileErrorResponse();

    LiveData<GifModel> getRefreshList();

    LiveData<Boolean> getLoadingGifsError();

    LiveData<String> getResponseInformation();

    void loadTrendingGifs(int offset);

    void searchGIF(int offset, String userInput);

    void uploadFile(MultipartBody.Part body);
}