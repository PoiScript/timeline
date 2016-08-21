package com.poipoipo.timeline.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Event implements Serializable {

    public static final int START = 0;
    public static final int END = -1;
    public static final int ERROR_TIME = -2;
    public boolean hasTitle = false;
    public boolean hasEndTime = false;
    public boolean hasSubtitle = false;
    private String title;
    private String subtitle;
    private String location;
    private int start;
    private int end;
    private int id;
    private Map<Integer, Integer> labelsMap;
    private List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();

    public Event(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getLabelsMap() {
        return labelsMap;
    }

    public void setLabelsMap(Map<Integer, Integer> labelsMap) {
        this.labelsMap = labelsMap;
    }

    public Event editByChangeLog(Map<Integer, Integer> map) {
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getKey() == START) {
                start = entry.getValue();
            } else if (entry.getKey() == END) {
                end = entry.getValue();
            } else {
                labelsMap.put(entry.getKey(), entry.getValue());
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