package com.poipoipo.timeline;

public class Event {

    public static final int COMPLETE = 0;
    public static final int NOT_END = 1;
    public static final int NOT_START = 2;

    public static final String TITLE = "title";
    public static final String START = "start";
    public static final String END = "end";
    public static final String STATE = "state";

    private int id;
    private String title;
    private int start;
    private int end;
    private int state;

    public Event(){}

    public Event (int start) {
        this.start = start;
        state = NOT_END;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
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

    public int getDuration() {
        return end - start;
    }

    public int getState() {
        return state;
    }
}