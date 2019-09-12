package com.example.giphyviewer.networking;

import com.example.giphyviewer.models.GIFModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GiphyAPI {

    @GET("trending")
    Call<GIFModel> getTrendingGIFs();
}