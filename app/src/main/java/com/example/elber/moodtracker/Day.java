package com.example.elber.moodtracker;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// represents a Day, storing user information for that specified date
public class Day {

    // Tag enum
    public enum Tag {
        // TODO: Add some actual enums
        GOOD("good"),
        BAD("bad");

        private String val;

        Tag(String value) {
            this.val = value;
        }

        public String getVal() {
            return this.val;
        }

        public static Tag valToTag(String val) {
            for (Tag t : Tag.values()) {
                if (t.getVal().equals(val)) {
                    return t;
                }
            }

            return null;
        }

        public String toString() {
            return getVal();
        }
    }

    // the date which this Day occurred
    final private LocalDate date;

    // the four metrics we're tracking: mood, energy, sleep, and anxiety
    // values range from 1-100 inclusive
    private int moodLevel;
    private int energyLevel;
    private int sleepLevel;
    private int anxietyLevel;

    // a set of Tags that the user has associated with this Day
    Set<Tag> tags;

    // note that the user has attributed to this day
    private String note;

    // constructor
    // date: the date this Day is representing
    public Day(LocalDate date) {
        this.date = date;
        this.tags = new HashSet<>();
    }

    /////////////////////////////
    // accessors and modifiers //
    /////////////////////////////
    public LocalDate getDate() {
        return date;
    }

    public int getMoodLevel() {
        return moodLevel;
    }

    public void setMoodLevel(int moodLevel) {
        this.moodLevel = moodLevel;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public int getSleepLevel() {
        return sleepLevel;
    }

    public void setSleepLevel(int sleepLevel) {
        this.sleepLevel = sleepLevel;
    }

    public int getAnxietyLevel() {
        return anxietyLevel;
    }

    public void setAnxietyLevel(int anxietyLevel) {
        this.anxietyLevel = anxietyLevel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    public String toString() {
        return date + " " +
                moodLevel  + " " +
                energyLevel + " " +
                anxietyLevel + " " +
                sleepLevel + " " +
                tags.toString() + " " +
                note;
    }
}
