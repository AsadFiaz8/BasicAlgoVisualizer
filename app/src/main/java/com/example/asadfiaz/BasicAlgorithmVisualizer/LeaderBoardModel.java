package com.example.asadfiaz.BasicAlgorithmVisualizer;

/**
 * Created by asadf on 8/3/2018.
 */

public class LeaderBoardModel {


    String name;
    String level;
    String Score;

    public LeaderBoardModel(String name, String level, String score) {
        this.name = name;
        this.level = level;
        Score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
