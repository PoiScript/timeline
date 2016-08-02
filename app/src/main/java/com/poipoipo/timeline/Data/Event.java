package com.poipoipo.timeline.Data;

import java.io.Serializable;

public class Event implements Serializable {

    public static final int BOOKMARK = 0;
    public static final int EVENT = 1;

    public static final String TITLE = "title";
    public static final String START = "start";
    public static final String END = "end";
    public static final String STATE = "state";

    private int title;
    private int start;
    private int end;
    private int state;
    private int category;
    private int location;
    private int note;

    public Event(){}

    public Event (int start) {
        this.start = start;
        state = BOOKMARK;
    }

    public void setTitle(int title) {
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

    public void setCategory(int category) {
        this.category = category;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getTitle() {
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

    public int getCategory() {
        return category;
    }

    public int getLocation() {
        return location;
    }

    public int getNote() {
        return note;
    }
}