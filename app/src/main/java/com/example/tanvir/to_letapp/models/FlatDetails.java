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


    public String getImage() {
        return image;
    }

    public FlatDetails(String ownerId, String flatLocation, String bedroom, String kitchen, String bathroom,
                       String rentDate, String condition, String totalRent,String image) {

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

    public FlatDetails(String image) {
        this.image = image;
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
