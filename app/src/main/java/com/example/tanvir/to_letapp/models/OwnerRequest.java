package com.example.tanvir.to_letapp.models;

public class OwnerRequest {
    private String name;
    private String age;
    private String profession;
    private String renterId;
    private  String currentUser;

    public OwnerRequest(String name, String age, String profession,String renterId,String currentUser) {
        this.name = name;
        this.age = age;
        this.profession = profession;
        this.renterId = renterId;
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getRenterId() {
        return renterId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }
}
