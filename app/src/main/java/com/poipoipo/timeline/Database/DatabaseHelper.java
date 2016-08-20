package com.poipoipo.timeline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {
    public static final String DATABASE_NAME = "Event.db";
    public static final String TABLE_EVENT = "Event";
    public static final int VERSION = 2;
    private static final String TAG = "DatabaseHelper";
    public Map<Integer, Label> labelMap = new HashMap<>();
    public Map<Integer, String> labelNameMap = new HashMap<>();
    SQLiteDatabase database;
    ContentValues values = new ContentValues();
    Cursor cursor;
    List<Event> events = new ArrayList<>();

    public DatabaseHelper(Context context) {
        database = new DatabaseOpenHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        initMap();
        Log.d(TAG, "DatabaseHelper: database ready");
    }

    private void initMap() {
        cursor = database.query("AllLabel", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                labelMap.put(cursor.getInt(cursor.getColumnIndex("id")),
                        getLabelByName(cursor.getString(cursor.getColumnIndex("label"))));
                labelNameMap.put(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("label")));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private Label getLabelByName(String tableName) {
        Label label = new Label();
        Cursor cursor = database.query(tableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                label.add(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("value")), cursor.getInt(cursor.getColumnIndex("usage")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return label;
    }

    public void insertEvent(int start) {
        values.clear();
        values.put("start", start);
        database.insert(TABLE_EVENT, null, values);

    }

    public void insertLabel(String tag, String value) {
        values.clear();
        values.put(tag, value);
        database.insert(tag, null, values);
    }

    public void update(int start, Map<Integer, Integer> map) {
        values.clear();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getKey() == Event.START) {
                values.put("start", entry.getValue());
            } else if (entry.getKey() == Event.END) {
                values.put("end", entry.getValue());
            } else {
                values.put(labelNameMap.get(entry.getKey()), entry.getValue());
            }
        }
        database.update(TABLE_EVENT, values, "start = ?", new String[]{Integer.toString(start)});
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

    public List<Event> query(int dataMin, int dataMax) {
        String where = "start < ? and start > ?";
        String[] whereValues = {Integer.toString(dataMax), Integer.toString(dataMin)};
        cursor = database.query(TABLE_EVENT, null, where, whereValues, null, null, null);
        events.clear();
        if (cursor.moveToFirst()) {
            do {
                Map<Integer, Integer> map = new LinkedHashMap<>();
                Event event = new Event(cursor.getInt(cursor.getColumnIndex("start")));
                for (Map.Entry<Integer, String> entry : labelNameMap.entrySet()) {
                    if (cursor.getInt(cursor.getColumnIndex(entry.getValue())) != 0) {
                        map.put(entry.getKey(), cursor.getInt(cursor.getColumnIndex(entry.getValue())));
                    }
                }
                event.setLabelsMap(map);
                events.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    public int getLabelIcon(int id) {
        int icon = 0;
        switch (id) {
            case 1:
                icon = R.drawable.ic_title;
                break;
            case 2:
                icon = R.drawable.ic_search;
                break;
            case 3:
                icon = R.drawable.ic_location;
                break;
            case 999:
                icon = R.drawable.ic_touch_app_black_24dp;
        }
        return icon;
    }
}