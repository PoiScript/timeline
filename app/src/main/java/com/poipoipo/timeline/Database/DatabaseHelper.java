package com.poipoipo.timeline.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_EVENT = "create table Event ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "title TEXT, "
            + "state INTEGER"
            + "start INTEGER, "
            + "end INTEGER)";

    Context mContext;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

