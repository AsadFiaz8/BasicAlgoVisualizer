package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asadf on 7/3/2018.
 */

public class ShowQuestions implements Parcelable {

    String id;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    private int answerNr;
    String level;

    public ShowQuestions() {

    }

    public ShowQuestions(String id, String question, String option1, String option2, String option3, String option4, int answerNr, String level) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
        this.level = level;
    }

    protected ShowQuestions(Parcel in) {
        id = in.readString();
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        answerNr = in.readInt();
        level = in.readString();
    }

    public static final Creator<ShowQuestions> CREATOR = new Creator<ShowQuestions>() {
        @Override
        public ShowQuestions createFromParcel(Parcel in) {
            return new ShowQuestions(in);
        }

        @Override
        public ShowQuestions[] newArray(int size) {
            return new ShowQuestions[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public String getLevel() {
        return level;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeInt(answerNr);
        dest.writeString(level);
    }
}
