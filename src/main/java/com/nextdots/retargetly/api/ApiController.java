package com.nextdots.retargetly.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nextdots.retargetly.data.listeners.CustomEventListener;
import com.nextdots.retargetly.data.models.Event;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private ApiService service;

    public ApiController(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.retargetly.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

    public void callCustomEvent(Event event){
        callEvent(event,null);
    }

    public void callCustomEvent(Event event,final CustomEventListener customEventListener){
        callEvent(event,customEventListener);
    }

    private void callEvent(Event event,final CustomEventListener customEventListener){
        service.callEvent(event).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d(ApiConstanst.TAG,response.code()+"");
                if(customEventListener != null)
                    customEventListener.customEventSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(ApiConstanst.TAG,t.getMessage());
                if(customEventListener != null)
                    customEventListener.customEventFailure(t.getMessage());
            }
        });
    }

}
