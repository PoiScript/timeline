package com.poipoipo.timeline.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class Label {
    public static final String TYPE = "type";
    public static final int TITLE = 0;
    public static final int SUBTITLE = 1;
    public static final int LOCATION = 2;
    public static final int END = 3;
    public static final int START = 4;
    public static final int TEACHER = 5;
    public static final int COST = 6;
    public static final int NOTE = 7;

    public Map<Integer, Integer> index = new LinkedHashMap<>();
    public Map<Integer, String> name = new LinkedHashMap<>();
    public Map<Integer, Integer> usage = new LinkedHashMap<>();
    int i = 0;

    private int id;
    private String value;
    private int type;

    public Label(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public Label() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void add(int id, String name, int usage) {
        this.name.put(id, name);
        this.usage.put(id, usage);
        this.index.put(id, i++);
    }

    public int getIdByIndex(int i) {
        return index.get(i);
    }
}
