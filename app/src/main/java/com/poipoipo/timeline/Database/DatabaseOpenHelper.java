package com.poipoipo.timeline.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_EVENT = "create table Event ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "start INTEGER, "
            + "end INTEGER, "
            + "category INTEGER, "
            + "content INTEGER)";

    private static final String CREATE_FIELD = "create table Field ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "field_id INTEGER, "
            + "label_id INTEGER, "
            + "name TEXT)";

    private static final String CREATE_CATEGORY = "create table Category ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "category_id INTEGER, "
            + "content_id INTEGER, "
            + "name TEXT)";

    private static final String[] defaultCategory = {"Course", "Breakfast", "Lunch", "Dinner", "Brunch", "Cook"};
    private static final String[] defaultCourse = {"Further Mathematics", "Linear Algebra", "Discrete Mathematics", "Digital Signal Process", "Probability And Statistics"};

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_FIELD);
        insertCategories(defaultCategory, db);
        insertContents(defaultCourse, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void insertCategories(String[] categories, SQLiteDatabase db) {
        for (int i = 0; i < categories.length; )
            db.execSQL("insert into Category (name, category_id, content_id) values(?, ?, ?)", new String[]{categories[i], Integer.toString(++i), Integer.toString(0)});
    }

    private void insertContents(String[] contents, SQLiteDatabase db) {
        for (int i = 0; i < contents.length; )
            db.execSQL("insert into Category (name, category_id, content_id) values(?, ?, ?)", new String[]{contents[i], Integer.toString(1), Integer.toString(++i)});
    }
}

