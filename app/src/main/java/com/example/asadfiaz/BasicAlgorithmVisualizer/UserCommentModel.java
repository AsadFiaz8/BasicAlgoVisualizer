package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 8/2/2018.
 */

public class UserCommentModel {

    String userName;
    String userComment;

    public UserCommentModel(String userName, String userComment) {
        this.userName = userName;
        this.userComment = userComment;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
