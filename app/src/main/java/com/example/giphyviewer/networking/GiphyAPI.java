package com.example.giphyviewer.networking;

import com.example.giphyviewer.models.GIFModel;

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
    Call<GIFModel> getTrendingGIFs(@Query("offset") int offset, @Query("limit") int limit);

    @GET("search")
    Call<GIFModel> searchGif(@Query("q") String q, @Query("limit") int limit);

    @Multipart
    @POST()
    Call<Void> uploadGIF(@Url String url, @Part MultipartBody.Part file);
}