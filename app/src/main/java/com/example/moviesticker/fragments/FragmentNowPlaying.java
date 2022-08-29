package com.example.moviesticker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesticker.R;
import com.example.moviesticker.adapter.MovieAdapter;
import com.example.moviesticker.databinding.FragmentNowPlayingBinding;
import com.example.moviesticker.db.MyDatabase;
import com.example.moviesticker.models.ConfigurationObject;
import com.example.moviesticker.models.DataResponseObject;
import com.example.moviesticker.models.ImageConfiguration;
import com.example.moviesticker.models.Movie;
import com.example.moviesticker.network.API;
import com.example.moviesticker.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNowPlaying extends Fragment {
    private FragmentNowPlayingBinding binding;
    private MovieAdapter movieAdapter;
    private API api;
    private MyDatabase db;

    String imageBaseUrl;
    ArrayList<Movie> itemsArray = new ArrayList<>();

    public FragmentNowPlaying() {
        super(R.layout.fragment_now_playing);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.api = RetrofitClient.getInstance().getApi();


        // if data not fetched yet, call API
        if (itemsArray.size() == 0){

            // get img base url
            Call<ConfigurationObject> configRequest = api.getConfiguration();
            configRequest.enqueue(new Callback<ConfigurationObject>() {
                @Override
                public void onResponse(Call<ConfigurationObject> call, Response<ConfigurationObject> response) {
                    Log.d("test", "runs here");
                    if (response.isSuccessful() == false){
                        Log.d("debug", "Error from API with response code: " + response.code());
                        return;
                    }


                    ConfigurationObject obj = response.body();
                    ImageConfiguration imgConfig = obj.getImages();
                    imageBaseUrl = imgConfig.getBaseUrl() + imgConfig.getBackdropSizes().get(1) + "/";
                }

                @Override
                public void onFailure(Call<ConfigurationObject> call, Throwable t) {
                    Log.d("debug", t.getMessage());
                }
            });

            // get movies data
            Call<DataResponseObject> dataRequest = api.getItemData();
            dataRequest.enqueue(new Callback<DataResponseObject>() {
                @Override
                public void onResponse(Call<DataResponseObject> call, Response<DataResponseObject> response) {
                    if (response.isSuccessful() == false){
                        Log.d("debug", "Error from API with response code: " + response.code());
                        return;
                    }

                    DataResponseObject obj = response.body();
                    List<Movie> moviesFromAPI = obj.getResults();
                    itemsArray.addAll(moviesFromAPI);
                    movieAdapter = new MovieAdapter(getContext(), itemsArray, imageBaseUrl);
                    binding.rvMovieItems.setAdapter(movieAdapter);
                    binding.rvMovieItems.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }

                @Override
                public void onFailure(Call<DataResponseObject> call, Throwable t) {
                    Log.d("debug", t.getMessage());
                }
            });
        }
        else{
            // set up adapter
            Log.d("test", "itemsArray 104: " + itemsArray.size() );
            movieAdapter = new MovieAdapter(getContext(), itemsArray, imageBaseUrl);
            binding.rvMovieItems.setAdapter(movieAdapter);
            binding.rvMovieItems.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();;
        binding = null;
    }
}