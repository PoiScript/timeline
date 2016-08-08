package com.poipoipo.timeline.data;

import java.io.Serializable;

public class Event implements Serializable {

    public static final int BOOKMARK = 0;
    public static final int EVENT = 1;

    public static final String CATEGORY = "category";
    public static final String TITLE = "title";
    public static final String START = "start";
    public static final String END = "end";
    public static final String LOCATION = "location";
    public static final String NOTE = "note";
    public static final String STATE = "state";

    private String title = "(Title No Set)";
    private int start;
    private int end;
    private int state;
    private String category = "";
    private String location = "";
    private String note = "";

    public Event() {
    }

    public Event(int start) {
        this.start = start;
        state = BOOKMARK;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getState() {
        return state;
    }

    public String getCategory() {
        return category;
    }

    public String  getLocation() {
        return location;
    }

    public String  getNote() {
        return note;
    }
}