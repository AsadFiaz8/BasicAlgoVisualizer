package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.provider.BaseColumns;

/**
 * Created by Asad Fiaz on 2/22/2018.
 */

public final class QuizContract {

    public static class MinimalSpanningTree implements BaseColumns {

        public static final String TABLE_NAME = "minimal_spanning_tree";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

    public static class QuestionsTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

}
