package com.poipoipo.timeline.data;

import android.util.Log;

import java.util.Calendar;

public class TimestampUtil {
    Long timeMillis;

    public int getCurrentTimestamp(){
        timeMillis = System.currentTimeMillis() / 1000;
        return timeMillis.intValue();
    }
}