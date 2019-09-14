package com.example.giphyviewer.repositories;

import okhttp3.MultipartBody;

public interface RemoteRepository {

    void loadTrendingGifs();

    void searchGIF(String userInput);

    void uploadFile(MultipartBody.Part body);
}