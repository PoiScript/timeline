package com.poipoipo.timeline.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class Label {
    public static final int TITLE = 0;
    public static final int SUBTITLE = 1;
    public static final int LOCATION = 2;
    private static final String TAG = "Label";
    public Map<Integer, Integer> index = new LinkedHashMap<>();
    public Map<Integer, String> name = new LinkedHashMap<>();
    public Map<Integer, Integer> usage = new LinkedHashMap<>();
    public Map<Integer, Integer> id = new LinkedHashMap<>();
    public String value;
    public int icon = 0;
    public int position;
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
