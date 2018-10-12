package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 5/26/2018.
 */

public class TeacherModel {

    String id;
    String teacherEmail;
    String teacherPassword;
    String teacherFullName;
    String teacherType;
    String phoneNumber;

    public TeacherModel() {

    }

    public TeacherModel(String id, String teacherEmail, String teacherPassword, String teacherFullName, String teacherType, String phoneNumber) {
        this.id = id;
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherFullName = teacherFullName;
        this.teacherType = teacherType;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public String getTeacherType() {
        return teacherType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public void setTeacherType(String teacherType) {
        this.teacherType = teacherType;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
