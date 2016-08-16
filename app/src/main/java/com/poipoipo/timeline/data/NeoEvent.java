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

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setLabels(Map<Integer, Integer> labels) {
        this.labels = labels;
    }
}
