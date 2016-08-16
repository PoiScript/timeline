package com.poipoipo.timeline.data;

import java.util.Map;

/**
 * Created by alex on 8/14/2016.
 */
public class NeoEvent {
    int start;
    int end;
    Map<Integer, Integer> labels;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Map<Integer, Integer> getLabels() {
        return labels;
    }
}
