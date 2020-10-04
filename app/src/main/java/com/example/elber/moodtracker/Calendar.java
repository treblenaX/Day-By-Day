package com.example.elber.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // List<Day> history = MoodTrackerDebug.generateControlHistory();
        SQLiteHelper helper = new SQLiteHelper(this);
        List<Day> history = helper.read30DaysFromDB();

        // calculate a color for up to 35 past days
        int[] colors = generateColors(history);
        int n = 0;

        // determine the starting position
        // int startIndex = Math.min(history.length - 1, 34);
        TableLayout calendar = findViewById(R.id.calendar);
        for (int j = 0; j < 5; j++) {
            TableRow row = (TableRow) calendar.getChildAt(j);
            LinearLayout layout = (LinearLayout) row.getChildAt(0);
            for (int k = 0; k < 7; k++) {
                View dayBox = layout.getChildAt(k);
                dayBox.setBackgroundColor(colors[n]);
            }
        }
    }

    // returns an array of colors to color the calendar with
    private int[] generateColors(List<Day> history) {
        int[] colors = new int[35];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = 0;
        }
        int n = 0;
        int start = Math.min(history.size() - 1, 34);
        for (int i = start; i >= 0; i--) {
            Day d = history.get(i);
            // calculate the average score across all attributes
            double dayScore = (double) (d.getSleepLevel() + d.getMoodLevel() + d.getEnergyLevel() + d.getAnxietyLevel()) / 4;
            int color;
            if (dayScore < 33.0) {
                color = Color.RED;
            }
            else if (dayScore < 66.0) {
                color = Color.YELLOW;
            }
            else {
                color = Color.GREEN;
            }
            colors[n] = color;
            n++;
        }
        return colors;
    }

}