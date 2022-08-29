package com.example.moviesticker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviesticker.R;
import com.example.moviesticker.adapter.PurchaseAdapter;
import com.example.moviesticker.databinding.FragmentTicketsBinding;
import com.example.moviesticker.db.MyDatabase;
import com.example.moviesticker.listeners.OnRowItemClickListener;
import com.example.moviesticker.models.Purchase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FragmentTickets extends Fragment implements OnRowItemClickListener {
    private FragmentTicketsBinding binding;
    private MyDatabase db;
    private PurchaseAdapter purchaseAdapter;
    ArrayList<Purchase> itemsArray = new ArrayList<Purchase>();


    public FragmentTickets() {
        super(R.layout.fragment_tickets);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.db = MyDatabase.getDatabase(this.getContext());
        List<Purchase> purchasesList = db.purchaseDAO().getAllPurchases();
        itemsArray.addAll(purchasesList);

        if (itemsArray.size() == 0){
            binding.tvNoTickets.setVisibility(View.VISIBLE);
        } else{
            binding.tvNoTickets.setVisibility(View.GONE);
            this.purchaseAdapter = new PurchaseAdapter(getContext(), itemsArray, this::onRowItemClicked);
            binding.rvPurchaseItems.setAdapter(purchaseAdapter);
            binding.rvPurchaseItems.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTicketsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();;
        binding = null;
    }

    @Override
    public void onRowItemClicked(Purchase currRowItemData) {
        db.purchaseDAO().delete(currRowItemData);
        itemsArray.remove(currRowItemData);
        purchaseAdapter.notifyDataSetChanged();

        if (itemsArray.size() == 0){
            binding.tvNoTickets.setVisibility(View.VISIBLE);
        }

        Snackbar.make(binding.getRoot(), "Purchase deleted successfully", Snackbar.LENGTH_SHORT).show();
    }
}
