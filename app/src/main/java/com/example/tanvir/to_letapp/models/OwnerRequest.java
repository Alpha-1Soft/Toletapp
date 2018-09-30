package com.example.tanvir.to_letapp.models;

public class OwnerRequest {
    private  String renterImage;
    private  String renterName;
    private  String renterAge;
    private  String renterProfession;
    private String renterPhoneNum;
    private String renterAddress;
    private String renterMonthlyIn;
    private String renterMaritStatus;
    private String renterGender;
    private String renterReligion;
    private String renterNationality;
    private String renterId;
    private String currentuser;
    private String renterEmail;

    private String ownerPostId;
    private String requestKey;

    /*public OwnerRequest(String renterImage, String renterName, String renterAge, String renterProfession, String renterPhoneNum,
                        String renterAddress, String renterMonthlyIn, String renterMaritStatus, String renterGender,
                        String renterReligion, String renterNationality, String renterId, String currentuser) {
    }*/

    public OwnerRequest(String renterImage, String renterName, String renterAge, String renterProfession,
                        String renterPhoneNum, String renterAddress, String renterMonthlyIn, String renterMaritStatus,
                        String renterGender, String renterReligion, String renterNationality, String renterId,
                        String currentuser,String renterEmail,String ownerPostId,String requestKey) {

        this.renterImage = renterImage;
        this.renterName = renterName;
        this.renterAge = renterAge;
        this.renterProfession = renterProfession;
        this.renterPhoneNum = renterPhoneNum;
        this.renterAddress = renterAddress;
        this.renterMonthlyIn = renterMonthlyIn;
        this.renterMaritStatus = renterMaritStatus;
        this.renterGender = renterGender;
        this.renterReligion = renterReligion;
        this.renterNationality = renterNationality;
        this.renterId = renterId;
        this.currentuser = currentuser;
        this.renterEmail = renterEmail;
        this.ownerPostId = ownerPostId;
        this.requestKey = requestKey;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public String getOwnerPostId() {
        return ownerPostId;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public String getRenterImage() {
        return renterImage;
    }

    public String getRenterName() {
        return renterName;
    }

    public String getRenterAge() {
        return renterAge;
    }

    public String getRenterProfession() {
        return renterProfession;
    }

    public String getRenterPhoneNum() {
        return renterPhoneNum;
    }

    public String getRenterAddress() {
        return renterAddress;
    }

    public String getRenterMonthlyIn() {
        return renterMonthlyIn;
    }

    public String getRenterMaritStatus() {
        return renterMaritStatus;
    }

    public String getRenterGender() {
        return renterGender;
    }

    public String getRenterReligion() {
        return renterReligion;
    }

    public String getRenterNationality() {
        return renterNationality;
    }

    public String getRenterId() {
        return renterId;
    }

    public String getCurrentuser() {
        return currentuser;
    }
}
