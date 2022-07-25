package com.example.retrofitassignment.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository INSTANCE;
    private final String TAG = "Repository";
    private MarsAPIService marsAPIService;
    private Call<List<MarsProperty>> marsRequest;

    private Repository(MarsAPIService marsAPIService) {
        this.marsAPIService = marsAPIService;
    }


    public static void initialize(MarsAPIService marsAPIService) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(marsAPIService);
        }
    }

    public static Repository get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Repository must be initialized!");
        }
        return INSTANCE;
    }

    public LiveData<List<MarsProperty>> fetchRentMars() {
        MutableLiveData<List<MarsProperty>> responseLiveData = new MutableLiveData<>();
        marsRequest = marsAPIService.getMars();
        marsRequest.enqueue(new Callback<List<MarsProperty>>() {

            @Override
            public void onResponse(Call<List<MarsProperty>> call, Response<List<MarsProperty>> response) {
                Log.i(TAG, "Response received");
                if (response != null) {
                    List<MarsProperty> marsResponse = response.body();
                    if (marsResponse != null) {
                        List<MarsProperty> rentMars = new ArrayList<>();
                        for(MarsProperty marsProperty:marsResponse){
                            if("rent".equals(marsProperty.getType())){
                                rentMars.add(marsProperty);
                            }
                        }
                        responseLiveData.postValue(rentMars);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MarsProperty>> call, Throwable t) {
                Log.e(TAG, "Failed to fetch mars", t);
            }
        });
        return responseLiveData;
    }

    public LiveData<List<MarsProperty>> fetchBoughtMars() {
        MutableLiveData<List<MarsProperty>> responseLiveData = new MutableLiveData<>();
        marsRequest = marsAPIService.getMars();
        marsRequest.enqueue(new Callback<List<MarsProperty>>() {

            @Override
            public void onResponse(Call<List<MarsProperty>> call, Response<List<MarsProperty>> response) {
                Log.i(TAG, "Response received");
                if (response != null) {
                    List<MarsProperty> marsResponse = response.body();
                    if (marsResponse != null) {
                        List<MarsProperty> boughtMars = new ArrayList<>();
                        for(MarsProperty marsProperty:marsResponse){
                            if("bought".equals(marsProperty.getType())){
                                boughtMars.add(marsProperty);
                            }
                        }
                        responseLiveData.postValue(boughtMars);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MarsProperty>> call, Throwable t) {
                Log.e(TAG, "Failed to fetch mars", t);
            }
        });
        return responseLiveData;
    }
    public void cancelRequestInFlight() {
        if (marsRequest != null) {
            if (marsRequest.isExecuted())
                marsRequest.cancel();
        }

    }
}