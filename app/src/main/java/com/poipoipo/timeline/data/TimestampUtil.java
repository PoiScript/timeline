package com.poipoipo.timeline.data;

import java.util.Calendar;

public class TimestampUtil {
    public static int getTimestampByCalendar(Calendar calendar) {
        Long timestamp = calendar.getTimeInMillis() / 1000;
        return timestamp.intValue();
    }

    public static int getDayTimestampByCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long timestamp = calendar.getTimeInMillis() / 1000;
        return timestamp.intValue();
    }

    public static int getCurrentTimestamp() {
        Long timestamp = Calendar.getInstance().getTimeInMillis() / 1000;
        return timestamp.intValue();
    }

    public static int getTodayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long timestamp = calendar.getTimeInMillis() / 1000;
        return timestamp.intValue();
    }

}