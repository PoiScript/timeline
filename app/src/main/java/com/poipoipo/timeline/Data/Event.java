package com.poipoipo.timeline.data;

import java.io.Serializable;

public class Event implements Serializable {

    public static final String EVENT = "event";
    public static final String START = "start";

    private String title;
    private String subtitle;
    private int start;
    private int end;
    private String location;
    private String note;
    private String teacher;
    private String cost;

    public boolean hasTitle = false;
    public boolean hasEndTime = false;
    public boolean hasLocation = false;
    public boolean hasNote = false;
    public boolean hasSubtitle = false;
    public boolean hasTeacher = false;
    public boolean hasCost = false;

    public Event(int start) {
        this.start = start;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getNote() {
        return note;
    }
}