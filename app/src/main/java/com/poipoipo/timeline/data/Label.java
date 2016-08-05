package com.poipoipo.timeline.data;

public class Label {
    public static final int CATEGORY = 0;
    public static final int TITLE = 1;
    public static final int LOCATION = 2;

    private int id;
    private String value;

    public Label() {
    }

    public void setId(int id) {
        this.id = id;
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
