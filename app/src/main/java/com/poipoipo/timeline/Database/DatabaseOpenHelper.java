package com.poipoipo.timeline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_EVENT = "create table Event ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "category INTEGER, "
            + "title INTEGER, "
            + "state INTEGER, "
            + "start INTEGER, "
            + "end INTEGER, "
            + "location INTEGER, "
            + "note TEXT)";

    public static final String CREATE_SUBTITLE = "create table Subtitle ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "title INTEGER, "
            + "value TEXT, "
            + "usage INTEGER)";

    public static final String CREATE_TITLE = "create table Title ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "value TEXT, "
            + "usage INTEGER)";

    public static final String CREATE_LOCATION = "create table Location ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "value TEXT, "
            + "usage INTEGER)";

    private static final String[] defaultTitle = {"Course", "Breakfast", "Lunch", "Dinner", "Brunch", "Cook"};
    private static final String[] defaultCourseSubtitle = {"Further Mathematics", "Linear Algebra", "Discrete Mathematics", "Digital Signal Process", "Probability And Statistics"};

    Context mContext;

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_SUBTITLE);
        db.execSQL(CREATE_TITLE);
        db.execSQL(CREATE_LOCATION);
        insertDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertDefaultData(SQLiteDatabase database){
        ContentValues values = new ContentValues();
        for (String s : defaultTitle){
            values.put("value", s);
            database.insert(DatabaseHelper.TABLE_TITLE, null, values);
            values.clear();
        }
        for (String s : defaultCourseSubtitle){
            values.put("value", s);
            values.put("title", 1);
            database.insert(DatabaseHelper.TABLE_SUBTITLE, null, values);
            values.clear();
        }
    }
}

