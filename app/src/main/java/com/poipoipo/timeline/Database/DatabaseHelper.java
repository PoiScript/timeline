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

public class DatabaseHelper {
    private static final String DATABASE_NAME = "Event.db";
    private static final String TABLE_EVENT = "Event";
    private static final int VERSION = 2;
    private static final String TAG = "DatabaseHelper";
    public final ArrayMap<Integer, Label> map = new ArrayMap<>();
    private final SQLiteDatabase database;
    private final ContentValues values = new ContentValues();
    private final List<Event> events = new ArrayList<>();
    private Cursor mCursor;

    public DatabaseHelper(Context context) {
        database = new DatabaseOpenHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        initMap();
        Log.d(TAG, "DatabaseHelper: database ready");
    }

    private void initMap() {
        mCursor = database.query("AllLabel", null, null, null, null, null, null);
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {
                Label label = new Label(getLabelIcon(mCursor.getInt(mCursor.getColumnIndex("id"))), mCursor.getString(mCursor.getColumnIndex("label")), i++);
                Cursor cursor = database.query(mCursor.getString(mCursor.getColumnIndex("label")), null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        label.add(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("value")), cursor.getInt(cursor.getColumnIndex("usage")));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                map.put(mCursor.getInt(mCursor.getColumnIndex("id")), label);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
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

    public void update(int id, ArrayMap<Integer, Integer> map) {
        values.clear();
        for (int i = 0; i <= map.size() - 1; i++) {
            if (map.keyAt(i) == Event.START) {
                values.put("start", map.valueAt(i));
            } else if (map.keyAt(i) == Event.END) {
                values.put("end", map.valueAt(i));
            } else {
                values.put(this.map.get(map.keyAt(i)).value, map.valueAt(i));
            }
        }
        database.update(TABLE_EVENT, values, "id = ?", new String[]{Integer.toString(id)});
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

    public Event queryEvent(int id) {
        String where = "id = ? ";
        String[] whereValues = {Integer.toString(id)};
        mCursor = database.query(TABLE_EVENT, null, where, whereValues, null, null, null);
        Event event = new Event(id);
        if (mCursor.moveToFirst()) {
            do {
                ArrayMap<Integer, Integer> array = new ArrayMap<>();
                event.setStart(mCursor.getInt(mCursor.getColumnIndex("start")));
                event.setEnd(mCursor.getInt(mCursor.getColumnIndex("end")));
                for (int i = 0; i < map.size(); i++) {
                    if (mCursor.getInt(mCursor.getColumnIndex(map.valueAt(i).value)) != 0) {
                        array.put(map.keyAt(i), mCursor.getInt(mCursor.getColumnIndex(map.valueAt(i).value)));
                    }
                }
                event.setLabelArray(array);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return event;
    }

    public List<Event> queryEvents(int dataMin, int dataMax) {
        String where = "start < ? and start > ?";
        String[] whereValues = {Integer.toString(dataMax), Integer.toString(dataMin)};
        mCursor = database.query(TABLE_EVENT, null, where, whereValues, null, null, null);
        events.clear();
        if (mCursor.moveToFirst()) {
            do {
                ArrayMap<Integer, Integer> arrayMap = new ArrayMap<>();
                Event event = new Event(mCursor.getInt(mCursor.getColumnIndex("id")));
                event.setStart(mCursor.getInt(mCursor.getColumnIndex("start")));
                event.setEnd(mCursor.getInt(mCursor.getColumnIndex("end")));
                for (int i = 0; i < map.size(); i++) {
                    if (mCursor.getInt(mCursor.getColumnIndex(map.valueAt(i).value)) != 0) {
                        arrayMap.put(map.keyAt(i), mCursor.getInt(mCursor.getColumnIndex(map.valueAt(i).value)));
                    }
                }

                event.setLabelArray(arrayMap);
                events.add(event);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return events;
    }

    public Event queryLastEvent() {
        mCursor = database.query(TABLE_EVENT, null, null, null, null, null, null);
        mCursor.moveToLast();
        Event event = new Event(mCursor.getInt(mCursor.getColumnIndex("id")));
        event.setStart(mCursor.getInt(mCursor.getColumnIndex("start")));
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