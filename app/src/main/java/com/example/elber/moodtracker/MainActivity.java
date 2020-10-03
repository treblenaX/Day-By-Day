package com.example.elber.moodtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
                // todo: save data set on sliders

                // todo: see if follow up questions should be asked

                // open Calendar activity
                Intent i = new Intent(c, Calendar.class);
                startActivity(i);
            }
        });
    }
}
