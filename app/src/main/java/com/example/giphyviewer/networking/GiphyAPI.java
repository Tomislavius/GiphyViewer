package com.example.giphyviewer.networking;

import com.example.giphyviewer.models.GifModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GiphyAPI {

    @GET("trending")
    Call<GifModel> getTrendingGifs(@Query("offset") int offset);

    @GET("search")
    Call<GifModel> searchGif(@Query("q") String q, @Query("offset") int offset);

    @Multipart
    @POST()
    Call<Void> uploadGIF(@Url String url, @Part MultipartBody.Part file);
}