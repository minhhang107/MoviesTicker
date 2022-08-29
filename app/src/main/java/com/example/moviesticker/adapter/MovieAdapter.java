package com.example.moviesticker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesticker.databinding.MovieRowLayoutBinding;
import com.example.moviesticker.db.MyDatabase;
import com.example.moviesticker.models.Movie;
import com.example.moviesticker.models.Purchase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {
    private MyDatabase db;
    private final Context context;
    private final ArrayList<Movie> moviesList;
    private String imageBaseUrl;
    MovieRowLayoutBinding binding;

    public MovieAdapter(Context context, ArrayList<Movie> movies, String url){
        this.moviesList = movies;
        this.context = context;
        this.imageBaseUrl = url;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.db = MyDatabase.getDatabase((context.getApplicationContext()));
        return new ItemViewHolder(MovieRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Movie currentItem = moviesList.get(position);
        holder.bind(context, currentItem);
    }

    @Override
    public int getItemCount() {
        return this.moviesList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        MovieRowLayoutBinding movieBinding;

        public ItemViewHolder(@NonNull MovieRowLayoutBinding binding){
            super(binding.getRoot());
            this.movieBinding = binding;
        }

        public void bind(Context context, Movie currentItem){
            movieBinding.tvTitle.setText(currentItem.getOriginalTitle());
            movieBinding.tvOverview.setText(currentItem.getOverview());
            movieBinding.tvReleaseDate.setText("Released: " + currentItem.getReleaseDate());
            movieBinding.tvRating.setText(currentItem.getVoteAverage()*10 + "%");
            Glide.with(context).load(imageBaseUrl + currentItem.getBackdropPath()).into(movieBinding.ivBackdrop);

            movieBinding.btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Purchase purchase = db.purchaseDAO().getPurchaseByMovieId(currentItem.getId());
                    if (purchase == null){
                        Purchase newPurchase = new Purchase(currentItem.getId(), currentItem.getOriginalTitle(), 1);
                        db.purchaseDAO().insertPurchase(newPurchase);
                    }
                    else {
                        Purchase updatedPurchase = new Purchase(purchase.getId(), purchase.getTitle(), purchase.getQuantity() + 1);
                        db.purchaseDAO().update(updatedPurchase);
                    }

                    Snackbar.make(movieBinding.getRoot(), "Ticket purchased successfully", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
