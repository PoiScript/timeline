package com.poipoipo.timeline.data;

public class DateMessageEvent {
    public final int type;
    public final int year;
    public final int month;
    public final int day;

    public DateMessageEvent(int type, int year, int month, int day) {
        this.type = type;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
