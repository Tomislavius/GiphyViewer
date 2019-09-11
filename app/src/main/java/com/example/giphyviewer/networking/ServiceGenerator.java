package com.example.giphyviewer.networking;

import com.example.giphyviewer.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(new QueryParamsInterceptor());

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient client = httpClient.build();

    private static Retrofit retrofit = builder.client(client).build();

    public static <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}