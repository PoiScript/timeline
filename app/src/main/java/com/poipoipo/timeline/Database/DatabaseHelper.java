package com.poipoipo.timeline.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;
import android.util.Log;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {
    private static final String DATABASE_NAME = "Event.db";
    private static final String TABLE_EVENT = "Event";
    private static final int VERSION = 2;
    private static final String TAG = "DatabaseHelper";
    public final ArrayMap<Integer, Label> map = new ArrayMap<>();
    private final SQLiteDatabase database;
    private final ContentValues values = new ContentValues();
    private final List<Event> events = new ArrayList<>();
    private Cursor cursor;

    public DatabaseHelper(Context context) {
        database = new DatabaseOpenHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        initMap();
        Log.d(TAG, "DatabaseHelper: database ready");
    }

    private void initMap() {
        cursor = database.query("AllLabel", null, null, null, null, null, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                map.put(cursor.getInt(cursor.getColumnIndex("id")),
                        getLabel(cursor.getString(cursor.getColumnIndex("label")), cursor.getInt(cursor.getColumnIndex("id")), i++));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private Label getLabel(String tableName, int id, int position) {
        Label label = new Label(getLabelIcon(id), tableName, position);
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

    public void update(int id, Map<Integer, Integer> map) {
        values.clear();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getKey() == Event.START) {
                values.put("start", entry.getValue());
            } else if (entry.getKey() == Event.END) {
                values.put("end", entry.getValue());
            } else {
                values.put(this.map.get(entry.getKey()).value, entry.getValue());
            }
        }
        database.update(TABLE_EVENT, values, "start = ?", new String[]{Integer.toString(id)});
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

    public Event query(int id) {
        String where = "id = ? ";
        String[] whereValues = {Integer.toString(id)};
        cursor = database.query(TABLE_EVENT, null, where, whereValues, null, null, null);
        Event event = new Event(id);
        if (cursor.moveToFirst()) {
            do {
                ArrayMap<Integer, Integer> array = new ArrayMap<>();
                event.setStart(cursor.getInt(cursor.getColumnIndex("start")));
                event.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                for (int i = 0; i < map.size(); i++) {
                    if (cursor.getInt(cursor.getColumnIndex(map.valueAt(i).value)) != 0) {
                        array.put(map.keyAt(i), cursor.getInt(cursor.getColumnIndex(map.valueAt(i).value)));
                    }
                }
                event.setLabelArray(array);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return event;
    }

    public List<Event> query(int dataMin, int dataMax) {
        String where = "start < ? and start > ?";
        String[] whereValues = {Integer.toString(dataMax), Integer.toString(dataMin)};
        cursor = database.query(TABLE_EVENT, null, where, whereValues, null, null, null);
        events.clear();
        if (cursor.moveToFirst()) {
            do {
                ArrayMap<Integer, Integer> arrayMap = new ArrayMap<>();
                Event event = new Event(cursor.getInt(cursor.getColumnIndex("id")));
                event.setStart(cursor.getInt(cursor.getColumnIndex("start")));
                event.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
                for (int i = 0; i < map.size() ; i++){
                    if (cursor.getInt(cursor.getColumnIndex(map.valueAt(i).value)) != 0){
                       arrayMap.put(map.keyAt(i), cursor.getInt(cursor.getColumnIndex(map.valueAt(i).value)));
                    }
                }

                event.setLabelArray(arrayMap);
                events.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    public Event queryLastEvent() {
        cursor = database.query(TABLE_EVENT, null, null, null, null, null, null);
        cursor.moveToLast();
        Event event = new Event(cursor.getInt(cursor.getColumnIndex("id")));
        event.setStart(cursor.getInt(cursor.getColumnIndex("start")));
        return event;
    }

    private int getLabelIcon(int id) {
        int icon = 0;
        switch (id) {
            case 1:
                icon = R.drawable.ic_category;
                break;
            case 2:
                icon = R.drawable.ic_content;
                break;
            case 3:
                icon = R.drawable.ic_location;
                break;
        }
        return icon;
    }

}