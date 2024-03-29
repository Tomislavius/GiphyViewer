package com.example.giphyviewer.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.giphyviewer.repositories.RemoteRepositoryImpl;

import okhttp3.MultipartBody;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private RemoteRepositoryImpl repository = new RemoteRepositoryImpl();

    void uploadFile(MultipartBody.Part body) {
        repository.uploadFile(body);
    }

    LiveData<Boolean> getUploadFileSuccessResponse() {
        return repository.getUploadFileSuccessResponse();
    }

    LiveData<String> getUploadFileErrorResponse() {
        return repository.getUploadFileErrorResponse();
    }
}