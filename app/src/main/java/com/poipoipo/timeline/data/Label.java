package com.poipoipo.timeline.data;

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

    private int id;
    private String value;
    private int type;

    public Label(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
