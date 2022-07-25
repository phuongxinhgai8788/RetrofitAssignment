package com.example.retrofitassignment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofitassignment.network.MarsProperty;
import com.example.retrofitassignment.network.Repository;

import java.util.List;

public class ListItemViewModel extends ViewModel {
    private Repository repository = Repository.get();

    public LiveData<List<MarsProperty>> rentMarsList = repository.fetchRentMars();
    public LiveData<List<MarsProperty>> boughtMarsList = repository.fetchBoughtMars();


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cancelRequestInFlight();
    }
}
