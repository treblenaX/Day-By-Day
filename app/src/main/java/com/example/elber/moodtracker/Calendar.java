package com.example.elber.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // todo: retrieve history from database
        Day[] history = null;

        // determine the starting position
        // int startIndex = Math.min(history.length - 1, 34);
        TableLayout calendar = findViewById(R.id.calendar);
        for (int j = 0; j < 5; j++) {
            TableRow row = (TableRow) calendar.getChildAt(j);
            LinearLayout layout = (LinearLayout) row.getChildAt(0);
            for (int k = 0; k < 7; k++) {
                View dayBox = layout.getChildAt(k);
                dayBox.setBackgroundColor(Color.YELLOW);
            }
        }
    }

}
