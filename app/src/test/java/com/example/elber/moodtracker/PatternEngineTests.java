package com.example.elber.moodtracker;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PatternEngineTests {

    @Test
    public void patternEngineEmptyDaysReturnsNegative() {
        PatternEngine engine = PatternEngine.getInstance();
        PatternEngine.Result r = engine.analyze(Collections.EMPTY_LIST);
        assertEquals(0, r.getPositives().size());
    }

    @Test
    public void patternEngineMajorDepressiveTest() {
        PatternEngine engine = PatternEngine.getInstance();
        List<Day> days = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            Day d = new Day(LocalDate.of(0, 1, i));
            d.setMoodLevel(0);
            d.setSleepLevel(0);
            d.setEnergyLevel(0);
            d.setAnxietyLevel(100);
            days.add(d);
        }
        PatternEngine.Result r = engine.analyze(days);
        System.out.println(r.results);
    }

    @Test
    public void patternEngineGeneralAnxietyTest() {
        PatternEngine engine = PatternEngine.getInstance();
        List<Day> days = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            Day d = new Day(LocalDate.of(0, 1, i));
            d.setMoodLevel(100);
            d.setSleepLevel(0);
            d.setEnergyLevel(100);
            d.setAnxietyLevel(0);
            days.add(d);
        }
        PatternEngine.Result r = engine.analyze(days);
        System.out.println(r.results);
    }

    @Test
    public void patternEngineBipolarITest() {
        PatternEngine engine = PatternEngine.getInstance();
        List<Day> days = new ArrayList<>();
        for (int i = 1; i < 15; i++) {
            Day d = new Day(LocalDate.of(0, 1, i));
            d.setMoodLevel(100);
            d.setSleepLevel(50);
            d.setEnergyLevel(100);
            d.setAnxietyLevel(50);
            days.add(d);
        }
        for (int i = 15; i < 31; i++) {
            Day d = new Day(LocalDate.of(0, 1, i));
            d.setMoodLevel(0);
            d.setSleepLevel(50);
            d.setEnergyLevel(0);
            d.setAnxietyLevel(50);
            days.add(d);
        }
        PatternEngine.Result r = engine.analyze(days);
        System.out.println(r.results);
    }

}