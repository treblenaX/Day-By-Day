package com.example.elber.moodtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on Confirm Button click
        final Button button = findViewById(R.id.confirmButton);
        final Context c = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // record this day's data
                Day day = getDayFromSliders();
                SQLiteHelper helper = new SQLiteHelper(c);
                helper.storeDayToDB(day);

                List<Day> history = helper.read30DaysFromDB();
                // List<Day> history = MoodTrackerDebug.generateControlHistory();

                PatternEngine.Result r = PatternEngine.getInstance().analyze(history);
                if (r.getPositives().size() > 0) {
                    Log.d("DIAGNOSIS", "Found evidence of following illnesses: " + r.getPositives());
                    askFollowUps(r.getPositives());
                }
                else {
                    openCalendar();
                }
            }
        });
    }

    private void openCalendar() {
        // open Calendar activity
        Intent i = new Intent(this, Calendar.class);
        startActivity(i);
    }

    // asks follow up questions for when a mental illness is detected
    private void askFollowUps(Set<PatternEngine.Illness> illnesses) {
        // for each illness detected
        for (PatternEngine.Illness illness : illnesses) {
            Intent i = new Intent(this, QuizActivity.class);
            i.putExtra("questions", illness.getQuestions());
            startActivity(i);
        }
    }

    // creates a Day object based on the sliders
    // todo: add support for tags
    private Day getDayFromSliders() {
        // get the sliders
        SeekBar moodSlider = findViewById(R.id.moodSlider);
        SeekBar energySlider = findViewById(R.id.energySlider);
        SeekBar anxietySlider = findViewById(R.id.anxietySlider);
        SeekBar sleepSlider = findViewById(R.id.sleepSlider);

        // match their values with ints
        int mood = moodSlider.getProgress();
        int energy = energySlider.getProgress();
        int anxiety = anxietySlider.getProgress();
        int sleep = sleepSlider.getProgress();

        // get today's date
        LocalDate date = LocalDate.now();

        // form a new Day object
        Day d = new Day(date);
        d.setMoodLevel(mood);
        d.setEnergyLevel(energy);
        d.setAnxietyLevel(anxiety);
        d.setSleepLevel(sleep);

        // return it
        return d;
    }
}