package com.example.c_10.calendarapp.controller;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.data.EventItem;

import java.util.ArrayList;


public class Events {
    private Context mContext;
    private ArrayList<EventItem> eventItems = new ArrayList<>();
    private ArrayList<EventItem> eventItemsNew = new ArrayList<>();
    private ArrayList<Drawable> startTimeOccurs = new ArrayList<>();
    private ArrayList<Drawable> endTimeOccurs = new ArrayList<>();
    private ArrayList<Drawable> eventOccurs = new ArrayList<>();
    private Drawable default_circle, blue_circle, red_circle;

    public Events(Context mContext, ArrayList<EventItem> eventItems) {
        this.mContext = mContext;
        this.eventItems = eventItems;
        default_circle = mContext.getResources().getDrawable(R.drawable.circle);
        blue_circle = mContext.getResources().getDrawable(R.drawable.circle_blue);
        red_circle = mContext.getResources().getDrawable(R.drawable.circle_red);
    }

    public ArrayList<Drawable> getStartTimeOccurs() {
        for (int i = 0; i < 24; i++) {
            boolean status = false;
            for (EventItem eventItem : eventItems) {
                int time = Integer.parseInt(eventItem.getStartTime().split(":")[0]);
                if (time == i) {
                    startTimeOccurs.add(blue_circle);
                    status = true;
                }
            }
            if (!status) {
                startTimeOccurs.add(null);
            }
        }
        return startTimeOccurs;
    }

    public ArrayList<Drawable> getEndTimeOccurs() {
        for (int i = 0; i < 24; i++) {
            boolean status = false;
            for (EventItem eventItem : eventItems) {
                /*if(eventItem.getStartTime()==i&&eventItem.getEndTime()==i){
                    endTimeOccurs.add()
                }else if(eventItem.getStartTime()==i){
                    endTimeOccurs.add(blue_circle);
                    status=true;
                }else */
                int time = Integer.parseInt(eventItem.getEndTime().split(":")[0]);
                if (time == i) {
                    endTimeOccurs.add(red_circle);
                    status = true;
                }
            }
            if (!status) {
                endTimeOccurs.add(null);
            }
        }
        return endTimeOccurs;
    }

    public ArrayList<Drawable> getEventOccurs() {
        for (int i = 0; i < 24; i++) {
            boolean status = false;
            for (EventItem eventItem : eventItems) {
                int time = Integer.parseInt(eventItem.getStartTime().split(":")[0]);
                if (time == i) {
                    eventOccurs.add(default_circle);
                    status = true;
                }
            }
            if (!status) {
                eventOccurs.add(null);
            }
        }
        return eventOccurs;
    }

    public ArrayList<EventItem> getEventItemsNew() {
        for (int i = 0; i < 24; i++) {
            boolean status = false;
            for (EventItem eventItem : eventItems) {
                int time = Integer.parseInt(eventItem.getStartTime().split(":")[0]);
                if (time == i) {
                    eventItemsNew.add(eventItem);
                    status = true;
                }
            }
            if (!status) {
                EventItem eventItem = new EventItem();
                eventItem.setDay(0);
                eventItem.setMonth(0);
                eventItem.setYear(0);
                eventItem.setEndTime("00:00");
                eventItem.setStartTime("00:00");
                eventItem.setEventName("");
                eventItem.setEventDescription("");
                eventItemsNew.add(eventItem);
            }
        }
        return eventItemsNew;
    }
}
