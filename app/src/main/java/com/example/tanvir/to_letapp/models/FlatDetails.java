package com.example.tanvir.to_letapp.models;

public class FlatDetails {
    private String ownerId;
    private String flatLocation;
    private String bedroom;
    private String kitchen;
    private String bathroom;
    private String rentDate;
    private String totalRent;
    private String condition;
    public  String image;
    private String postId;

    public FlatDetails(String ownerId, String flatLocation, String bedroom, String kitchen, String bathroom, String rentDate, String condition, String totalRent, String s1, String postId) {
        this.ownerId = ownerId;
        this.flatLocation = flatLocation;
        this.bedroom = bedroom;
        this.kitchen = kitchen;
        this.bathroom = bathroom;
        this.rentDate = rentDate;
        this.condition = condition;
        this.totalRent=totalRent;
        this.postId = postId;
        this.image = s1;
    }

    public FlatDetails(String ownerId,String flatLocation, String bedroom, String kitchen, String bathroom, String rentDate, String condition, String totalRent,String image) {

        this.ownerId = ownerId;
        this.flatLocation = flatLocation;
        this.bedroom = bedroom;
        this.kitchen = kitchen;
        this.bathroom = bathroom;
        this.rentDate = rentDate;
        this.condition = condition;
        this.totalRent=totalRent;
        this.image = image;
    }

    /*public FlatDetails(String ownerId,String postId,String address,String bedroom, String kitchen, String bathroom, String rentDate, String condition, String totalRent,String s) {
        //ownerIdList.get(finalI),dataSnapshot.getKey() address, bedroom, kitchen,bathroom,rentDate,condition,totalRent
        this.ownerId = ownerId;
        this.flatLocation = address;
        this.bedroom = bedroom;
        this.kitchen = kitchen;
        this.bathroom = bathroom;
        this.rentDate = rentDate;
        this.condition = condition;
        this.totalRent=totalRent;
        this.postId = postId;
    }*/

    public String getImage() {
        return image;
    }
    public String getPostId() {
        return postId;
    }

    public String getTotalRent() {
        return totalRent;
    }

    public String getBedroom() {
        return bedroom;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getBathroom() {
        return bathroom;
    }

    public String getRentDate() {
        return rentDate;
    }

    public String getCondition() {
        return condition;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getFlatLocation() {
        return flatLocation;
    }

}
