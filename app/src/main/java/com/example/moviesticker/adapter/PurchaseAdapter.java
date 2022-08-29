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
import com.example.moviesticker.databinding.PurchaseRowLayoutBinding;
import com.example.moviesticker.db.MyDatabase;
import com.example.moviesticker.listeners.OnRowItemClickListener;
import com.example.moviesticker.models.Movie;
import com.example.moviesticker.models.Purchase;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ItemViewHolder> {
    private final Context context;
    private final ArrayList<Purchase> purchasesList;
    private final OnRowItemClickListener clickListener;
    PurchaseRowLayoutBinding binding;

    public PurchaseAdapter(Context context, ArrayList<Purchase> purchases, OnRowItemClickListener clickListener){
        this.purchasesList = purchases;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PurchaseAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PurchaseAdapter.ItemViewHolder(PurchaseRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ItemViewHolder holder, int position) {
        Purchase currentItem = purchasesList.get(position);
        holder.bind(context, currentItem);
    }

    @Override
    public int getItemCount() {
        Log.d("CharacterAdapter", "getItemCount: Number of items " +this.purchasesList.size() );
        return this.purchasesList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        PurchaseRowLayoutBinding purchaseBinding;

        public ItemViewHolder(@NonNull PurchaseRowLayoutBinding binding){
            super(binding.getRoot());
            this.purchaseBinding = binding;
        }

        public void bind(Context context, Purchase currentItem){
            purchaseBinding.tvPurchaseTitle.setText(currentItem.getTitle());
            purchaseBinding.tvPurchaseQuantity.setText("Tickets purchased: " + currentItem.getQuantity());

            purchaseBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onRowItemClicked(currentItem);
                }
            });

        }
    }
}
