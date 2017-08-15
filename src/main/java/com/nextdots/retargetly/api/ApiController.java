package com.nextdots.retargetly.api;

import android.util.Log;

import com.nextdots.retargetly.models.Event;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    Retrofit retrofit;
    ApiService service;

    public ApiController(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.retargetly.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

    public void callEvent(Event event){
        service.callEvent(event).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("-->","Todo bon");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("-->","Todo mal");
            }
        });
    }

}
