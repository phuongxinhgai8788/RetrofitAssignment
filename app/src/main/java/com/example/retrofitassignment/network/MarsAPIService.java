package com.example.retrofitassignment.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MarsAPIService{

    @GET("realestate")
    Call<List<MarsProperty>> getMars();

}
