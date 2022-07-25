package com.example.retrofitassignment;

import android.app.Application;

import com.example.retrofitassignment.network.MarsAPIService;
import com.example.retrofitassignment.network.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
