package com.poipoipo.timeline.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_EVENT = "create table Event ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "start INTEGER, "
            + "end INTEGER)";

    public static final String CREATE_ALL_LABEL = "create table AllLabel ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "label TEXT)";

    public static final String CREATE_NEW_LABEL_PART_1 = "create table ";
    public static final String CREATE_NEW_LABEL_PART_2 = " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "parent INTEGER, "
            + "value TEXT, "
            + "usage INTEGER)";
    public static final String EVENT_ADD_COLUMN_PART_1 = "alter table Event add column ";
    public static final String EVENT_ADD_COLUMN_PART_2 = " integer";

    private static final String[] defaultTitle = {"Course", "Breakfast", "Lunch", "Dinner", "Brunch", "Cook"};
    private static final String[] defaultCourseSubtitle = {"Further Mathematics", "Linear Algebra", "Discrete Mathematics", "Digital Signal Process", "Probability And Statistics"};
    private static final String[] defaultLabels = {"Title", "Subtitle", "Location"};

    Context mContext;

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_ALL_LABEL);
        for (String label : defaultLabels) {
            db.execSQL(CREATE_NEW_LABEL_PART_1 + label + CREATE_NEW_LABEL_PART_2);
            db.execSQL(EVENT_ADD_COLUMN_PART_1 + label + EVENT_ADD_COLUMN_PART_2);
            db.execSQL("insertEvent into AllLabel (label) values(?)", new String[]{label});
        }
        insertDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void insertDefaultData(SQLiteDatabase database) {
        for (String title : defaultTitle) {
            database.execSQL("insertEvent into Title (value) values(?)", new String[]{title});
        }
        for (String subtitle : defaultCourseSubtitle) {
            database.execSQL("insertEvent into Subtitle (label) values(?)", new String[]{subtitle});
        }
    }
}

