package com.poipoipo.timeline.Database;

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

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
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

    Context mContext;

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_TITLE);
        db.execSQL(CREATE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

