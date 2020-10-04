package com.example.elber.moodtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class QuizActivity extends AppCompatActivity {

    private static final int POSITIVE_ANSWER_THRESHOLD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // get the question to ask and display it
        Intent i = getIntent();
        final String[] questionsToAsk = (String[]) i.getExtras().get("questions");
        final String illnessName = (String) i.getExtras().get("illnessName");
        final TextView questionText = findViewById(R.id.questionText);
        questionText.setText(questionsToAsk[0]);
        final AtomicInteger questionsAsked = new AtomicInteger();
        final AtomicInteger positiveAnswers = new AtomicInteger();
        final AtomicInteger negativeAnswers = new AtomicInteger();

        // set button listeners
        // if yes
        Button yesButton = findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increment counters
                questionsAsked.getAndIncrement();
                positiveAnswers.getAndIncrement();
                // if that wasn't the last question, ask the next
                if (questionsAsked.get() < questionsToAsk.length) {
                    questionText.setText(questionsToAsk[questionsAsked.get()]);
                }
                // otherwise, if enough were positive, send them to the help screen
                else {
                    checkAnswers(positiveAnswers.get(), illnessName);
                }
            }
        });
        // if no
        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increment counters
                questionsAsked.getAndIncrement();
                negativeAnswers.getAndIncrement();
                // if that wasn't the last question, ask the next
                if (questionsAsked.get() < questionsToAsk.length) {
                    questionText.setText(questionsToAsk[questionsAsked.get()]);
                }
                // otherwise, if enough were positive, send them to the help screen
                else {
                    checkAnswers(positiveAnswers.get(), illnessName);
                }
            }
        });
    }

    private void checkAnswers(int positiveAnswers, final String illnessName) {
        // if enough positive, send to help
        if (true) { // todo: unhack this for production purposes
            Log.d("QUIZ", "Positive answer threshold reached");
            AlertDialog log = new AlertDialog.Builder(this)
                    .setTitle("Results")
                    .setMessage("We have detected signs of " + illnessName.toLowerCase().replace("_", " ") + ", we recommend seeking professional help. Would you like to do a search in your area?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "https://www.google.com/maps/search/" + illnessName + "therapy+near+me";
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        }
                    }).setNegativeButton(android.R.string.no, null)
                    .show();
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