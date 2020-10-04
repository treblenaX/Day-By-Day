package com.example.elber.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private static final int POSITIVE_ANSWER_THRESHOLD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // get the question to ask and display it
        Intent i = getIntent();
        final String[] questionsToAsk = (String[]) i.getExtras().get("questions");
        final TextView questionText = findViewById(R.id.questionText);
        questionText.setText(questionsToAsk[0]);
        final int[] questionsAsked = {0};
        final int[] positiveAnswers = {0};
        final int[] negativeAnswers = {0};

        // set button listeners
        // if yes
        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increment counters
                questionsAsked[0]++;
                positiveAnswers[0]++;
                // if that wasn't the last question, ask the next
                if (questionsAsked[0] < questionsToAsk.length) {
                    questionText.setText(questionsToAsk[questionsAsked[0]]);
                }
                // otherwise, if enough were positive, send them to the help screen
                else {
                    checkAnswers(positiveAnswers[0]);
                }
            }
        });
        // if no
        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increment counters
                questionsAsked[0]++;
                negativeAnswers[0]++;
                // if that wasn't the last question, ask the next
                if (questionsAsked[0] < questionsToAsk.length) {
                    questionText.setText(questionsToAsk[questionsAsked[0]]);
                }
                // otherwise, if enough were positive, send them to the help screen
                else {
                    checkAnswers(positiveAnswers[0]);
                }
            }
        });
    }

    private void checkAnswers(int positiveAnswers) {
        // if enough positive, send to help
        if (positiveAnswers >= POSITIVE_ANSWER_THRESHOLD) {
            // todo: send to help screen
            openCalendar();
        }
        else {
            // otherwise close
            openCalendar();
        }
    }

    private void openCalendar() {
        Intent i = new Intent(this, Calendar.class);
        startActivity(i);
    }

}