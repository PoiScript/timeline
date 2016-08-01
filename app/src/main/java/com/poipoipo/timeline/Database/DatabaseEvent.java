package com.poipoipo.timeline.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poipoipo.timeline.Event;

import java.util.ArrayList;
import java.util.List;

public class DatabaseEvent {

    public static final String DATABASE_NAME = "Event.db";
    public static final String TABLE_NAME = "Event";
    public static final int VERSION = 1;

    SQLiteDatabase database;
    ContentValues values = new ContentValues();
    Cursor cursor;
    List<Event> list = new ArrayList<>();

    public DatabaseEvent(Context context) {
        database = new DatabaseHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
    }

    public void insert(Event event) {
        values.clear();
        values.put("title", event.getTitle());
        values.put("state", event.getState());
        switch (event.getState()) {
            case Event.NOT_END:
                values.put("start", event.getStart());
                break;
            case Event.COMPLETE:
                values.put("data", event.getStart());
            case Event.NOT_START:
                values.put("data", event.getEnd());
                break;
        }
        database.insert(TABLE_NAME, null, values);
    }

    public void insert(List<Event> list) {
        for (Event event : list) {
            insert(event);
        }
    }

    public void update(int id, String[] which, String[] whichValues) {
        values.clear();
        for (int i = 0; i <= which.length; i++) {
            values.put(which[i], whichValues[i]);
        }
        database.update(DATABASE_NAME, values, "id = ?", new String[]{Integer.toString(id)});
    }

    public void delete() {
        database.delete(DATABASE_NAME, null, null);
    }

    public void delete(int id) {
        database.delete(DATABASE_NAME, "id = ?", new String[]{Integer.toString(id)});
    }

    public List<Event> query() {
        cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        queryTraverse();
        return list;
    }

    public List<Event> query(int dataMin, int dataMax) {
        String where = "start < ? and start > ?";
        String[] whereValue = {Integer.toString(dataMax), Integer.toString(dataMin)};
        cursor = database.query(TABLE_NAME, null, where, whereValue, null, null, null);
        queryTraverse();
        return list;
    }

    private void queryTraverse() {
        list.clear();
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                switch (event.getState()) {
                    case Event.NOT_START:
                        event.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                        break;
                    case Event.COMPLETE:
                        event.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                    case Event.NOT_END:
                        event.setStart(cursor.getInt(cursor.getColumnIndex("start")));
                }
                list.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}

