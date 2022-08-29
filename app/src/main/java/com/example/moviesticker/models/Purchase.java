package com.example.moviesticker.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "purchase_table")
public class Purchase {
    @PrimaryKey
    private int id;
    private String title;
    private int quantity;

    public Purchase(int id, String title, int quantity){
        this.id = id;
        this.title = title;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
