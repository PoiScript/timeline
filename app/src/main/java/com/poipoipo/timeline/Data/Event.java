package com.poipoipo.timeline.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable {
    private SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy      HH:mm");
    private static final String NO_LOCATION = "Add Location";
    private static final String NO_COST = "Set Cost";
    private static final String NO_END = "Set Ending Time";
    private static final String NO_TEACHER = "Add Teacher";

    public static final String EVENT = "event";

    private String title;
    private String subtitle;
    private int start;
    private int end;
    private String location;
    private String note;
    private String teacher;
    private String cost;

    public boolean hasTitle = false;
    public boolean hasEndTime = false;
    public boolean hasLocation = false;
    public boolean hasNote = false;
    public boolean hasSubtitle = false;
    public boolean hasTeacher = false;
    public boolean hasCost = false;

    public Event(int start) {
        this.start = start;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getNote() {
        return note;
    }

    public List<Label> getAvailableLabelList() {
        List<Label> labels = new ArrayList<>();
        labels.add(new Label(Label.START, format.format(start * 1000L)));
        if (hasEndTime) {
            labels.add(new Label(Label.END, format.format(end)));
        }
        if (hasLocation) {
            labels.add(new Label(Label.LOCATION, location));
        }
        if (hasTeacher) {
            labels.add(new Label(Label.TEACHER, teacher));
        }
        if (hasCost) {
            labels.add(new Label(Label.COST, cost));
        }
        return labels;
    }

    public List<Label> getAllLabelList() {
        List<Label> labels = new ArrayList<>();
        labels.add(new Label(Label.START, format.format(start * 1000L)));
        if (hasEndTime) {
            labels.add(new Label(Label.END, format.format(end * 1000L)));
        } else {
            labels.add(new Label(Label.END, NO_END));
        }
        if (hasLocation) {
            labels.add(new Label(Label.LOCATION, location));
        } else {
            labels.add(new Label(Label.LOCATION, NO_LOCATION));
        }
        if (hasTeacher) {
            labels.add(new Label(Label.TEACHER, teacher));
        } else {
            labels.add(new Label(Label.TEACHER, NO_TEACHER));
        }
        if (hasCost) {
            labels.add(new Label(Label.COST, cost));
        } else {
            labels.add(new Label(Label.COST, NO_COST));
        }
        return labels;
    }
}