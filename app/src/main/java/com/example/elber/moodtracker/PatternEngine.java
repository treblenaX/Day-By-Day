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

            // threshold to be considered "low"
            public final int LOW_THRESHOLD = 34;

            // follow up questions to ask if illness is suspected
            public final String[] secondaryQuestions = {
                    "Have you been able to engage in your hobbies as of late?",
                    "Have you had any significant changes in weight?",
                    "Have you had difficulty concentrating?",
                    "Have you had any recurrent thoughts of death or suicide?"
            };

            // diagnoses based on a history of days
            // conditions:
            // - at least 6 days of low mood in the past week
            // - at least 3 days of low sleep in the past week
            // - at least 3 days of low energy in the past week
            @Override
            public boolean diagnose(List<Day> days) {
                // if there aren't enough days to make a call, return false
                if (days.size() < 7) {
                    return false;
                }

                // count low attributes
                int lowMood = 0, lowSleep = 0, lowEnergy = 0;
                for (int i = 0; i < 7; i++) {
                    Day day = days.get(i);
                    if (day.getMoodLevel() <= LOW_THRESHOLD) lowMood++;
                    if (day.getSleepLevel() <= LOW_THRESHOLD) lowSleep++;
                    if (day.getEnergyLevel() <= LOW_THRESHOLD) lowEnergy++;
                }

                // if low mood didn't make up 5 of the 7 days, return false
                if (lowMood < 5) {
                    return false;
                }

                // if the sum of the counts reaches 9 out of a possible 15, return true
                return lowMood + lowSleep + lowEnergy >= 9;
            }
<<<<<<< HEAD

            @Override
            public String[] getQuestions() {
                return secondaryQuestions;
            }
=======
>>>>>>> master
        },

        // Generalized Anxiety Disorder
        GENERAL_ANXIETY {
            // anxiety threshold
            public final int LOW_THRESHOLD = 33;

            // follow up questions to ask if illness is suspected
            public final String[] secondaryQuestions = {
                    "Have you had difficulty concentrating?",
                    "Have you been feeling tense or worked up?",
                    "Have you been finding it difficult to relax?"
            };

            // diagnoses based on a history of days
            // conditions:
            // - low anxiety for the majority of the past 3 weeks
            // - low sleep for some days
            @Override
            public boolean diagnose(List<Day> days) {
                // if there aren't enough days, return false
                if (days.size() < 21) {
                    return false;
                }

                // count the anxiety and sleep days
                int lowAnxiety = 0, lowSleep = 0;
                for (int i = 0; i < 21; i++) {
                    Day day = days.get(i);
                    if (day.getAnxietyLevel() < LOW_THRESHOLD) lowAnxiety++;
                    if (day.getSleepLevel() < LOW_THRESHOLD) lowSleep++;
                }

                // if anxiety wasn't felt a majority of the days, return false
                if (lowAnxiety < 11) {
                    return false;
                }

                // if the number of days of anxiety (plus low sleep) is greater than half of the
                // total days, return positive
                return lowAnxiety + lowSleep > 10;
            }
<<<<<<< HEAD

            @Override
            public String[] getQuestions() {
                return secondaryQuestions;
            }
=======
>>>>>>> master
        },

        // Bipolar I Disorder
        BIPOLAR_I {
            // thresholds
            public final int LOW_THRESHOLD = 10;
            public final int HIGH_THRESHOLD = 90;

            // follow up questions to ask if illness is suspected
            public final String[] secondaryQuestions = {
                    "Have you had any significant changes in weight?",
                    "Have you experienced a sudden lack of need for sleep?",
                    "Have you experienced a sudden boost of energy in the past week?"
            };

            // diagnoses based on a history of days
            // conditions:
            // - large volume of extreme high and extreme low mood levels in the past month
            // - large volume of extreme high and extreme low energy levels in the past month
            @Override
            public boolean diagnose(List<Day> days) {
                // return false if not enough data is provided
                if (days.size() < 30) {
                    return false;
                }

                // count the number of high and low mood days, and sleep and energy
                int lowMood = 0, hiMood = 0, hiEnergy = 0, lowEnergy = 0;
                for (int i = 0; i < 30; i++) {
                    Day day = days.get(i);
                    if (day.getMoodLevel() > HIGH_THRESHOLD) hiMood++;
                    if (day.getMoodLevel() < LOW_THRESHOLD) lowMood++;
                    if (day.getEnergyLevel() > HIGH_THRESHOLD) hiEnergy++;
                    if (day.getEnergyLevel() < LOW_THRESHOLD) lowEnergy++;
                }

                // if the sum of all counts reaches 25 of 30 days, return true
                return (lowEnergy + lowMood + hiEnergy + hiMood) > 50;
            }
<<<<<<< HEAD

            @Override
            public String[] getQuestions() {
                return secondaryQuestions;
            }
=======
>>>>>>> master
        };

        // returns whether this history of days suggests a mental illness
        public abstract boolean diagnose(List<Day> days);
<<<<<<< HEAD

        // returns an array of secondary questiosn to ask if illness is suspected
        public abstract String[] getQuestions();
=======
>>>>>>> master
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
    // days: a list containing the days to analyze, sorted with most recent days at the beginning
    // returns: a Result object representing the findings of the analysis
    public Result analyze(List<Day> days) {
<<<<<<< HEAD
        // if days is null, complain
        if (days == null) {
            throw new IllegalArgumentException("days cannot be null");
        }

=======
>>>>>>> master
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
