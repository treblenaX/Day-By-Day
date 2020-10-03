package com.example.elber.moodtracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class MoodTrackerDebug {

    public static List<Day> generateDepressionHistory() {
        ArrayList<Day> days = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            Day d = new Day(LocalDate.of(i, 1, 1));
            d.setSleepLevel(0);
            d.setAnxietyLevel(100);
            d.setEnergyLevel(100);
            d.setMoodLevel(0);
            days.add(d);
        }
        return days;
    }

    public static List<Day> generateControlHistory() {
        ArrayList<Day> days = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            Day d = new Day(LocalDate.of(i, 1, 1));
            d.setSleepLevel(50);
            d.setAnxietyLevel(50);
            d.setEnergyLevel(50);
            d.setMoodLevel(50);
            days.add(d);
        }
        return days;
    }

}
