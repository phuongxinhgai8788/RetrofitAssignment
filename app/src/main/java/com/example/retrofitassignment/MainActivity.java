package com.example.retrofitassignment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.retrofitassignment.network.MarsAPIService;
import com.example.retrofitassignment.network.MarsProperty;
import com.example.retrofitassignment.network.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements ListItemFragment.OpenScreen{

    boolean isRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRepository();
    }

    private void initRepository() {

        Retrofit retrofit01 = new Retrofit.Builder()
                .baseUrl("https://mars.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MarsAPIService marsAPIService = retrofit01.create(MarsAPIService.class);
        Repository.initialize(marsAPIService);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_mars:
                isRent = true;
                displayListFragment();
                return true;
            case R.id.open_flickers:
                isRent = false;
                displayListFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayListFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);
        if(currentFragment==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, ListItemFragment.newInstance(isRent))
                    .commit();
        } else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, ListItemFragment.newInstance(isRent))
                    .commit();
        }

}

    @Override
    public void openMarsDetailScreen(MarsProperty marsProperty) {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);
        if(currentFragment==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, ItemDetailFragment.newInstance(marsProperty))
                    .commit();
        } else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, ItemDetailFragment.newInstance(marsProperty))
                    .addToBackStack(null)
                    .commit();
        }
    }
}

