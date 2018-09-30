package com.example.tanvir.to_letapp.models;

public class RenterNotification {
    private String ownerName;
    private String ownerIdTemp, postId,phoneNum;
    int mark;
    int clickList;


    private String address,bedroom,bathroom,kitchen,rentFor,rentType,image,rentAmount;
    private String notificationValue;
    public RenterNotification(String ownerName, String ownerIdTemp, String postId,String phoneNum) {
        this.ownerName = ownerName;
        this.ownerIdTemp = ownerIdTemp;
        this.postId = postId;
        this.phoneNum = phoneNum;
    }

    public RenterNotification(String address, String bedroom, String bathroom, String kitchen, String rentFor, String rentType, String image, String rentAmount) {
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.kitchen = kitchen;
        this.rentFor = rentFor;
        this.rentType = rentType;
        this.image = image;
        this.rentAmount = rentAmount;
    }

    public String getOwnerIdTemp() {
        return ownerIdTemp;
    }

    public String getPostId() {
        return postId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAddress() {
        return address;
    }

    public String getBedroom() {
        return bedroom;
    }

    public String getBathroom() {
        return bathroom;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getRentFor() {
        return rentFor;
    }

    public String getRentType() {
        return rentType;
    }

    public String getImage() {
        return image;
    }

    public String getRentAmount() {
        return rentAmount;
    }
}
