package com.example.tanvir.to_letapp.models;

public class OwnerPost {
    private String userId;
    private String postId;
    private String address;
    private String batchroom;
    private String bedroom;
    private String kichen;

    public OwnerPost(String userId,String postId,String address, String batchroom, String bedroom, String kichen) {
        this.userId=userId;
        this.postId = postId;
        this.address = address;
        this.batchroom = batchroom;
        this.bedroom = bedroom;
        this.kichen = kichen;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }

    public String getAddress() {
        return address;
    }

    public String getBatchroom() {
        return batchroom;
    }

    public String getBedroom() {
        return bedroom;
    }

    public String getKichen() {
        return kichen;
    }
}
