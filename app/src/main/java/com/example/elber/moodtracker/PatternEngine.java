package com.example.elber.moodtracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// handles the recognition of mental illness patterns
// is a singleton
public class PatternEngine {

    // an enum of mental illnesses this Engine can analyze for
    public enum Illness {

        // Major Depressive Disorder
        MAJOR_DEPRESSIVE {
            @Override
            public boolean diagnose(List<Day> days) {
                return false;
            }
        },

        // Generalized Anxiety Disorder
        GENERAL_ANXIETY {
            @Override
            public boolean diagnose(List<Day> days) {
                return false;
            }
        },

        // Bipolar I Disorder
        BIPOLAR_I {
            @Override
            public boolean diagnose(List<Day> days) {
                return false;
            }
        };

        // returns whether this history of days suggests a mental illness
        public abstract boolean diagnose(List<Day> days);
    }

    // represents a Result returned by a call to analyze()
    public class Result {
        // illnesses mapped to whether they're detected
        Map<Illness, Boolean> results;

        // constructor
        public Result() {
            this.results = new HashMap<>();
        }

        // easy accessors
        // returns all illnesses that tests positive
        public Set<Illness> getPositives() {
            Set<Illness> positives = new HashSet<>();
            for (Map.Entry<Illness, Boolean> illnessBooleanEntry : results.entrySet()) {
                if (illnessBooleanEntry.getValue()) {
                    positives.add(illnessBooleanEntry.getKey());
                }
            }
            return positives;
        }

    }

    // instance
    private static PatternEngine instance = null;

    // instance retrieval
    public static PatternEngine getInstance() {
        if (instance == null) {
            instance = new PatternEngine();
        }
        return instance;
    }

    // private constructor
    private PatternEngine() {
        if (instance != null) {
            throw new IllegalStateException("New instances of PatternEngine should not be created");
        }
    }

    // analyzes a user's history of Days to detect any signs of mental illness
    // days: a list containing the days to analyze
    // returns: a Result object representing the findings of the analysis
    public Result analyze(List<Day> days) {
        // create new Result object
        Result r = new Result();

        // run diagnosis for all Illnesses in enum and put into Result
        for (Illness illness : Illness.values()) {
            boolean concern = illness.diagnose(days);
            r.results.put(illness, concern);
        }

        // return result
        return r;
    }

}
