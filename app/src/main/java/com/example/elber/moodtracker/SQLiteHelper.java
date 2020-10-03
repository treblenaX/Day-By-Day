import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.elber.moodtracker.Day;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 6;
    public static final String DB_NAME = "MoodTracker";
    public static final String DAY_ID = "_id";
    public static final String DAY_TABLE_NAME = "day";
    public static final String DAY_DATE = "date";
    public static final String MOOD_LEVEL = "mood";
    public static final String ENERGY_LEVEL = "energy";
    public static final String SLEEP_LEVEL = "sleep";
    public static final String ANXIETY_LEVEL = "anxiety";
    public static final String TAGS = "tags";
    public static final String NOTES = "notes";

    public static final String[] KEYS = {
            DAY_ID,
            DAY_DATE,
            MOOD_LEVEL,
            ENERGY_LEVEL,
            SLEEP_LEVEL,
            ANXIETY_LEVEL,
            TAGS,
            NOTES
    };

    public static int rCounter = 30;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void wipeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE " + DAY_TABLE_NAME);

        String query = "CREATE TABLE " + DAY_TABLE_NAME + " (" +
                DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DAY_TABLE_NAME + " TEXT, " +
                DAY_DATE + " TEXT," +
                MOOD_LEVEL + " INT, " +
                ENERGY_LEVEL + " INT, " +
                SLEEP_LEVEL + " INT, " +
                ANXIETY_LEVEL + " INT, " +
                NOTES + " TEXT, " +
                TAGS + " TEXT" + ")";

        db.execSQL(query);

        db.close();
    }

    public void storeDayToDB(Day day) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DAY_DATE, day.getDate().toString());
        values.put(MOOD_LEVEL, day.getMoodLevel());
        values.put(ENERGY_LEVEL, day.getEnergyLevel());
        values.put(SLEEP_LEVEL, day.getSleepLevel());
        values.put(ANXIETY_LEVEL, day.getAnxietyLevel());
        values.put(TAGS, daySetToDBText(day.getTags()));
        values.put(NOTES, day.getNote());

        rCounter++;
        db.insert(DAY_TABLE_NAME, null, values);
    }

    public List<Day> read30DaysFromDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Day> list = new ArrayList<>();

        String query = "SELECT * FROM " + DAY_TABLE_NAME + " ORDER BY " + DAY_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        int counter = 30;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast() && counter != 0) {
                list.add(cursorBuildDay(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();

        return list;
    }

    public Day readMostRecentFromDB() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                DAY_TABLE_NAME,
                KEYS,
                null,
                null,
                null,
                null,
                null
        );

        return cursorBuildDay(cursor);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + DAY_TABLE_NAME + " (" +
                DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DAY_TABLE_NAME + " TEXT, " +
                DAY_DATE + " TEXT," +
                MOOD_LEVEL + " INT, " +
                ENERGY_LEVEL + " INT, " +
                SLEEP_LEVEL + " INT, " +
                ANXIETY_LEVEL + " INT, " +
                NOTES + " TEXT, " +
                TAGS + " TEXT" + ")";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DAY_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private Day cursorBuildDay(Cursor cursor) {
        Day d = new Day(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DAY_DATE))));

        d.setMoodLevel(cursor.getInt(cursor.getColumnIndex(MOOD_LEVEL)));
        d.setEnergyLevel(cursor.getInt(cursor.getColumnIndex(ENERGY_LEVEL)));
        d.setSleepLevel(cursor.getInt(cursor.getColumnIndex(SLEEP_LEVEL)));
        d.setAnxietyLevel(cursor.getInt(cursor.getColumnIndex(ANXIETY_LEVEL)));

        Set<Day.Tag> set = DBTextToDaySet(cursor.getString(cursor.getColumnIndex(TAGS)));

        for (Day.Tag t : set) {
            d.addTag(t);
        }

        d.setNote(cursor.getString(cursor.getColumnIndex(NOTES)));

        return d;
    }

    private String daySetToDBText(Set<Day.Tag> set) {
        StringBuilder sBuild = new StringBuilder();

        if (!set.isEmpty()) {
            Object[] setArr = set.toArray();

            sBuild.append(setArr[0]);
            for (int i = 1; i < setArr.length; i++) {
                sBuild.append(", " + setArr[i]);
            }
        }

        return sBuild.toString();
    }

    private Set<Day.Tag> DBTextToDaySet(String str) {
        Set<Day.Tag> set = new HashSet<>();

        if (!str.isEmpty()) {
            String[] arr = str.split("[\\s*,\\s]+");
            for (int i = 0; i < arr.length; i++) {
                set.add(Day.Tag.valToTag(arr[i]));
            }
        }

        return set;
    }
}
