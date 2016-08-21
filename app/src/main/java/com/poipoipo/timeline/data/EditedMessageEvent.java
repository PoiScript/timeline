package com.poipoipo.timeline.data;

import java.util.Map;

public class EditedMessageEvent {
    public final int position;
    public final Map<Integer, Integer> changeLog;

    public EditedMessageEvent(int position, Map<Integer, Integer> changeLog) {
        this.position = position;
        this.changeLog = changeLog;
    }
}
