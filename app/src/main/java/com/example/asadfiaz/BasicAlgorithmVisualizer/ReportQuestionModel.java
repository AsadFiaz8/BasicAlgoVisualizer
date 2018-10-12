package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 8/8/2018.
 */

public class ReportQuestionModel {

    String reportedID;
    String questionID;
    String name;
    String category;
    String question;

    public ReportQuestionModel(String reportedID, String questionID, String name, String category, String question) {
        this.reportedID = reportedID;
        this.questionID = questionID;
        this.name = name;
        this.category = category;
        this.question = question;
    }

    public String getReportedID() {
        return reportedID;
    }

    public void setReportedID(String reportedID) {
        this.reportedID = reportedID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
