package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 8/11/2018.
 */

public class RegisteredLearnersModel {


    String id;
    String email;
    String userName;
    String pwd;
    String mobileNumber;

    public RegisteredLearnersModel(String id, String email, String userName, String pwd, String mobileNumber) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.pwd = pwd;
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
