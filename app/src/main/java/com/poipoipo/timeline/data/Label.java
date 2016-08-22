package com.poipoipo.timeline.data;

import android.util.ArrayMap;

public class Label {
    public static final int TITLE = 0;
    public static final int SUBTITLE = 1;
    public static final int LOCATION = 2;
    private static final String TAG = "Label";
    public final ArrayMap<Integer, Integer> index = new ArrayMap<>();
    public final ArrayMap<Integer, String> name = new ArrayMap<>();
    public final ArrayMap<Integer, Integer> usage = new ArrayMap<>();
    public final ArrayMap<Integer, Integer> id = new ArrayMap<>();
    public final String value;
    public final int position;
    public int icon = 0;
    int i = 0;

    public Label(int icon, String value, int position) {
        this.value = value;
        this.icon = icon;
        this.position = position;
    }

    public void add(int id, String name, int usage) {
        this.name.put(id, name);
        this.usage.put(id, usage);
        this.index.put(id, i);
        this.id.put(i++, id);
    }
}
