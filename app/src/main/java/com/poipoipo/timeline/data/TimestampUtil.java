package com.poipoipo.timeline.data;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class TimestampUtil {
    private Calendar calendar;
    private Long timestamp;
    private int todayTimestamp;

    public TimestampUtil() {
        calendar = Calendar.getInstance();
        moveToBeginning();
        timestamp = calendar.getTimeInMillis() / 1000;
        todayTimestamp = timestamp.intValue();
    }

    public int getCurrentTimestamp() {
        timestamp = Calendar.getInstance().getTimeInMillis() / 1000;
        return timestamp.intValue();
    }

    public int getTodayTimestamp() {
        return todayTimestamp;
    }

    public int getDayTimestamp(Calendar calendar) {
        this.calendar = calendar;
        moveToBeginning();
        timestamp = calendar.getTimeInMillis() / 1000;
        return timestamp.intValue();
    }

    private void moveToBeginning(){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}