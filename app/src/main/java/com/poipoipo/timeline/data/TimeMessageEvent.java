package com.poipoipo.timeline.data;

public class TimeMessageEvent {
    public final int type;
    public final int hour;
    public final int minute;

    public TimeMessageEvent(int type, int hour, int minute) {
        this.type = type;
        this.hour = hour;
        this.minute = minute;
    }
}
