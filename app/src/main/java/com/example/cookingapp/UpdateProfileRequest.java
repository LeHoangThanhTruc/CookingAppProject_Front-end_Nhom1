package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {
    @SerializedName("full_name")
    private final String fullName;

    @SerializedName("phone_number")
    private final String phoneNumber;

    private final String bio;

    public UpdateProfileRequest(String fullName, String phoneNumber, String bio) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
    }
}
