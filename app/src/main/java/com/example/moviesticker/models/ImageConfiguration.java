package com.example.moviesticker.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageConfiguration {
    @SerializedName("base_url")
    private String baseUrl;

    @SerializedName("backdrop_sizes")
    private ArrayList<String> backdropSizes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public ArrayList<String> getBackdropSizes() {
        return backdropSizes;
    }
}
