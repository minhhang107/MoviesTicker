package com.example.moviesticker.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviesticker.models.Purchase;

import java.util.List;

@Dao
public interface PurchaseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertPurchase(Purchase purchase);

    @Query("SELECT * FROM purchase_table")
    public List<Purchase> getAllPurchases();

    @Query("SELECT * FROM purchase_table WHERE id = :movieId")
    public Purchase getPurchaseByMovieId(int movieId);

    @Update()
    public void update(Purchase purchase);

    @Delete
    public void delete(Purchase purchase);
}
