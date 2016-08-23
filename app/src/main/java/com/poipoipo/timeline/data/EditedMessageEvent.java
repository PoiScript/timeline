package com.poipoipo.timeline.data;

import android.util.ArrayMap;

public class EditedMessageEvent {
    public final int position;
    public final ArrayMap<Integer, Integer> changeLog;

    public EditedMessageEvent(int position, ArrayMap<Integer, Integer> changeLog) {
        this.position = position;
        this.changeLog = changeLog;
    }
}
