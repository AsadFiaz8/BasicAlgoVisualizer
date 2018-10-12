package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 5/29/2018.
 */

public class UserProfileModel {

    String email;
    String fullName;
    String phoneNumber;
    String type;

    public UserProfileModel(String email, String fullName, String phoneNumber, String type) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }
}
