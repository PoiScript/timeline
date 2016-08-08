package com.poipoipo.timeline.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    public static final String DATABASE_NAME = "Event.db";
    public static final String TABLE_EVENT = "Event";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_TITLE = "Title";
    public static final String TABLE_LOCATION = "Location";
    public static final int VERSION = 2;

    SQLiteDatabase database;
    ContentValues values = new ContentValues();
    Cursor cursor;
    List<Event> list = new ArrayList<>();
    Map<Integer, String> categories;
    Map<Integer, String> titles;
    Map<Integer, String> locations;

    public DatabaseHelper(Context context) {
        database = new DatabaseOpenHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        categories = query(TABLE_CATEGORY);
        titles = query(TABLE_TITLE);
        locations = query(TABLE_LOCATION);
    }

    public void insert(Event event) {
        values.clear();
        values.put("category", event.getCategory());
        values.put("title", event.getTitle());
        values.put("state", event.getState());
        switch (event.getState()) {
            case Event.EVENT:
                values.put("end", event.getStart());
            case Event.BOOKMARK:
                values.put("start", event.getStart());
        }
        values.put("location", event.getLocation());
        values.put("note", event.getNote());
        database.insert(TABLE_EVENT, null, values);
    }

    public void insert(List<Event> list) {
        for (Event event : list) {
            insert(event);
        }
    }

    public void insert(String tag, String value) {
        values.clear();
        values.put(tag, value);
        database.insert(tag, null, values);
    }

    public void update(int start, String[] labels, String[] labelsValues) {
        if (labels.length == labelsValues.length) {
            values.clear();
            for (int i = 0; i <= labels.length; i++) {
                values.put(labels[i], labelsValues[i]);
            }
            database.update(TABLE_EVENT, values, "start = ?", new String[]{Integer.toString(start)});
        }
    }

    public void update(String label, String before, String after) {
        values.clear();
        values.put(label, after);
        database.update(label, values, "value = ?", new String[]{before});
    }

    public void delete(String table) {
        database.delete(table, null, null);
    }

    public void delete(int start) {
        database.delete(TABLE_EVENT, "start = ?", new String[]{Integer.toString(start)});
    }

    public void delete(String label, String which) {
        database.delete(label, "value  = ?", new String[]{which});
    }

    public List<Event> query() {
        cursor = database.query(TABLE_EVENT, null, null, null, null, null, null);
        queryTraverse();
        return list;
    }

    public List<Event> query(int dataMin, int dataMax) {
        String where = "start < ? and start > ?";
        String[] whereValues = {Integer.toString(dataMax), Integer.toString(dataMin)};
        cursor = database.query(TABLE_EVENT, null, where, whereValues, null, null, null);
        queryTraverse();
        return list;
    }

    private void queryTraverse() {
        list.clear();
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setCategory(categories.get(cursor.getInt(cursor.getColumnIndex("category"))));
                event.setTitle(titles.get(cursor.getInt(cursor.getColumnIndex("title"))));
                event.setState(cursor.getInt(cursor.getColumnIndex("state")));
                switch (event.getState()) {
                    case Event.BOOKMARK:
                        event.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                    case Event.EVENT:
                        event.setStart(cursor.getInt(cursor.getColumnIndex("start")));
                }
                event.setLocation(locations.get(cursor.getInt(cursor.getColumnIndex("location"))));
                list.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public Map<Integer, String> query(String label) {
        Map<Integer, String> map = new HashMap<>();
        cursor = database.query(label, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                map.put(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("value")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }
}