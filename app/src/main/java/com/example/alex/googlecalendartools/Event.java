package com.example.alex.googlecalendartools;

import java.util.Calendar;

/**
 * Created by alex on 6/2/16.
 */
public class Event {

    private static final int COMPELETE = 0;
    private static final int NOT_END = 1;
    private static final int NOT_START = 2;

    private String Title;
    private Calendar StartCal;
    private Calendar EndCal;
    private int duration;
    private int state;

    public void Event (String Title, Calendar StartCal, Calendar EndCal){
        this.Title = Title;
        this.StartCal = StartCal;
        this.EndCal = EndCal;
        this.state = COMPELETE;
    }

    public void Event (String Title, Calendar calendar, int state){
        this.Title = Title;
        this.state = state;
        if (this.state == NOT_END){
            this.StartCal = calendar;
        } else {
            this.EndCal = calendar;
        }
    }

    public void setTitle (String title){
        this.Title = title;
    }

    public void setStartCal (Calendar cal){
        this.StartCal = cal;
    }

    public void setEndCal (Calendar cal){
        this.EndCal = cal;
    }

    public String getTitle(){
        return Title;
    }

    public Calendar getStartCal(){
        return StartCal;
    }

    public Calendar getEndCal(){
        return EndCal;
    }

}
