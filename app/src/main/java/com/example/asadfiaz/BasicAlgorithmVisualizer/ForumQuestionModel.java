package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 8/1/2018.
 */

public class ForumQuestionModel {

    String qID;
    String uID;
    String name;
    String Category;
    String question;

    public ForumQuestionModel(String qID, String uID, String name, String category, String question) {
        this.qID = qID;
        this.uID = uID;
        this.name = name;
        Category = category;
        this.question = question;
    }

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
