package com.example.tanvir.to_letapp.models;

public class FlatDetails {
    private String ownerId;
    private String flatLocation;
    private String flatCondition;
    private String availableFor;


    public FlatDetails(String ownerId, String flatLocation, String flatCondition, String availableFor) {
        this.ownerId = ownerId;
        this.flatLocation = flatLocation;
        this.flatCondition = flatCondition;
        this.availableFor = availableFor;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getFlatLocation() {
        return flatLocation;
    }

    public String getFlatCondition() {
        return flatCondition;
    }

    public String getAvailableFor() {
        return availableFor;
    }
}
