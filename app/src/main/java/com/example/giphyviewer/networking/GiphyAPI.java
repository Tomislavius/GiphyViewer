package com.example.giphyviewer.networking;

import com.example.giphyviewer.models.GIFModel;
import com.example.giphyviewer.models.Meta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GiphyAPI {

    @GET("trending")
    Call<GIFModel> getTrendingGIFs();

    @POST("gifs")
    Call<Meta> uploadGIF(@Url String url, @Query("file") String file);

    @GET("search")
    Call<GIFModel> searchGif(@Query("q") String q, @Query("limit") int limit);
}