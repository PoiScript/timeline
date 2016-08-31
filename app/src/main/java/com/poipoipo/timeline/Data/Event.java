package com.poipoipo.timeline.data;

import android.util.ArrayMap;

import java.io.Serializable;

public class Event implements Serializable {

    public static final int START = 0;
    public static final int END = -1;
    public static final int ERROR_TIME = -2;
    private int start;
    private int end;
    private int id;
    private ArrayMap<Integer, Integer> labelArray;

    public Event(int id) {
        this.id = id;
    }

    public ArrayMap<Integer, Integer> getLabelArray() {
        if (labelArray == null) {
            return new ArrayMap<>();
        } else {
            return labelArray;
        }
    }

    public void setLabelArray(ArrayMap<Integer, Integer> labelArray) {
        this.labelArray = labelArray;
    }

    public Event update(ArrayMap<Integer, Integer> changelog) {
        for (int i = 0; i <= changelog.size() - 1; i++) {
            if (changelog.keyAt(i) == START) {
                start = changelog.valueAt(i);
            } else if (changelog.keyAt(i) == END) {
                end = changelog.valueAt(i);
            } else {
                if (labelArray == null) {
                    labelArray = new ArrayMap<>();
                }
                labelArray.put(changelog.keyAt(i), changelog.valueAt(i));
            }
        }
        return this;
    }

    public int getId() {
        return id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}