package com.example.elber.moodtracker;

import org.junit.Test;

import java.util.Collections;

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
}