package com.example.digitalrestaurant;

import android.net.Uri;

class HotelItem {
    private String itemName;
    private String itemPrice;
    private String itemImagepath;

    public HotelItem() {
    }

    public HotelItem(String itemName, String itemPrice, String itemImagepath) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImagepath = itemImagepath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImagepath() {
        return itemImagepath;
    }

    public void setItemImagepath(String itemImagepath) {
        this.itemImagepath = itemImagepath;
    }
}
