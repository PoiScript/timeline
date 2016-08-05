package com.poipoipo.timeline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    public static final String DATABASE_NAME = "Event.db";
    public static final String TABLE_EVENT = "Event";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_TITLE = "Title";
    public static final String TABLE_LOCATION = "Location";
    public static final int VERSION = 1;

    SQLiteDatabase database;
    ContentValues values = new ContentValues();
    Cursor cursor;
    List<Event> list = new ArrayList<>();
    List<Label> labels = new ArrayList<>();

    public DatabaseHelper(Context context) {
        database = new DatabaseOpenHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
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

    public void update(int start, String[] tags, String[] tagsValues) {
        if (tags.length == tagsValues.length) {
            values.clear();
            for (int i = 0; i <= tags.length; i++) {
                values.put(tags[i], tagsValues[i]);
            }
            database.update(TABLE_EVENT, values, "start = ?", new String[]{Integer.toString(start)});
        }
    }

    public void update(String tag, String before, String after) {
        values.clear();
        values.put(tag, after);
        database.update(tag, values, "value = ?", new String[]{before});
    }

    public void delete(String table) {
        database.delete(table, null, null);
    }

    public void delete(int start) {
        database.delete(TABLE_EVENT, "start = ?", new String[]{Integer.toString(start)});
    }

    public void delete(String tag, String which) {
        database.delete(tag, "value  = ?", new String[]{which});
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
                event.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
                event.setTitle(cursor.getInt(cursor.getColumnIndex("title")));
                event.setState(cursor.getInt(cursor.getColumnIndex("state")));
                switch (event.getState()) {
                    case Event.BOOKMARK:
                        event.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                    case Event.EVENT:
                        event.setStart(cursor.getInt(cursor.getColumnIndex("start")));
                }
                list.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public List<Label> query(String tag) {
        cursor = database.query(tag, null, null, null, null, null, null);
        labels.clear();
        if (cursor.moveToFirst()) {
            do {
                Label label1 = new Label();
                label1.setId(cursor.getInt(cursor.getColumnIndex("id")));
                label1.setValue(cursor.getString(cursor.getColumnIndex("value")));
                labels.add(label1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }
}

