package com.example.moviesticker.network;

import com.example.moviesticker.models.ConfigurationObject;
import com.example.moviesticker.models.DataResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    // base url of the api
    public final String BASE_URL = "http://api.themoviedb.org/3/";
    public final String API_KEY = "d2bd197606453f994723b743f5b5c047";

    // endpoints to connect to
    @GET("movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1&region=CA")
    public Call<DataResponseObject> getItemData();

    @GET("configuration?api_key=" + API_KEY)
    public Call<ConfigurationObject> getConfiguration();
}
