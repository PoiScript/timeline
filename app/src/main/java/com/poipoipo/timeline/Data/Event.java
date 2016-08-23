package com.poipoipo.timeline.data;


import android.util.ArrayMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Event implements Serializable {

    public static final int START = 0;
    public static final int END = -1;
    public static final int ERROR_TIME = -2;
    public final boolean hasTitle = false;
    public final boolean hasEndTime = false;
    public final boolean hasSubtitle = false;
    private final List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();
    private String title;
    private String subtitle;
    private String location;
    private int start;
    private int end;
    private int id;
    private Map<Integer, Integer> labelsMap;
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

    public Event editByChangeLog(ArrayMap<Integer, Integer> map) {
        for (int i = 0; i <= map.size() - 1; i++) {
            if (map.keyAt(i) == START) {
                start = map.valueAt(i);
            } else if (map.keyAt(i) == END) {
                end = map.valueAt(i);
            } else {
                if (labelsMap == null) {
                    labelsMap = new ArrayMap<>();
                }
                labelsMap.put(map.keyAt(i), map.valueAt(i));
            }
        }
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Map.Entry<Integer, Integer>> getLabelList() {
        labelList.clear();
        for (Map.Entry<Integer, Integer> entry : labelsMap.entrySet()) {
            labelList.add(entry);
        }
        return labelList;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

}