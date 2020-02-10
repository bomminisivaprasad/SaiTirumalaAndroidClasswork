package com.example.zixinglibtest;

public class MyPojo {
    private String itemName;
    private String itemPrice;
    private String filepath;

    public MyPojo(String itemName, String itemPrice, String filepath) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.filepath = filepath;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getFilepath() {
        return filepath;
    }
}
