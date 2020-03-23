package com.example.farmerskart;

class User {
    String name, mobile, district, address, username, password, typeofUser,imagePath,location;

    public User(String name, String mobile, String district, String address, String username, String password, String typeofUser, String imagePath, String location) {
        this.name = name;
        this.mobile = mobile;
        this.district = district;
        this.address = address;
        this.username = username;
        this.password = password;
        this.typeofUser = typeofUser;
        this.imagePath = imagePath;
        this.location = location;
    }

    public User() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeofUser() {
        return typeofUser;
    }

    public void setTypeofUser(String typeofUser) {
        this.typeofUser = typeofUser;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
