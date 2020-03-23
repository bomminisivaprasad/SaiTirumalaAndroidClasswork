package com.example.farmerskart;

class MyItem {
    String productType,productName,productPricewithunits,ownerName,ownerMobile, ownerDistrict,ownerAddress,ownerImagepath,ownerLocation,itemimagePath,itemQuantity;

    public MyItem(String productType, String productName, String productPricewithunits, String ownerName, String ownerMobile, String ownerDistrict, String ownerAddress, String ownerImagepath, String ownerLocation, String itemimagePath,String itemQuantity) {
        this.productType = productType;
        this.productName = productName;
        this.productPricewithunits = productPricewithunits;
        this.ownerName = ownerName;
        this.ownerMobile = ownerMobile;
        this.ownerDistrict = ownerDistrict;
        this.ownerAddress = ownerAddress;
        this.ownerImagepath = ownerImagepath;
        this.ownerLocation = ownerLocation;
        this.itemimagePath = itemimagePath;
        this.itemQuantity = itemQuantity;
    }

    public MyItem() {
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPricewithunits() {
        return productPricewithunits;
    }

    public void setProductPricewithunits(String productPricewithunits) {
        this.productPricewithunits = productPricewithunits;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public String getOwnerDistrict() {
        return ownerDistrict;
    }

    public void setOwnerDistrict(String ownerDistrict) {
        this.ownerDistrict = ownerDistrict;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getOwnerImagepath() {
        return ownerImagepath;
    }

    public void setOwnerImagepath(String ownerImagepath) {
        this.ownerImagepath = ownerImagepath;
    }

    public String getOwnerLocation() {
        return ownerLocation;
    }

    public void setOwnerLocation(String ownerLocation) {
        this.ownerLocation = ownerLocation;
    }

    public String getItemimagePath() {
        return itemimagePath;
    }

    public void setItemimagePath(String itemimagePath) {
        this.itemimagePath = itemimagePath;
    }
}
