package com.example.c_10.calendarapp.controller;

import android.content.Context;

import com.example.c_10.calendarapp.data.EventItem;

import java.util.ArrayList;

/**
 *
 * @author  Roy
 *
 */

public class Agenda {
    private Context mContext;
    private ArrayList<EventItem> eventItems = new ArrayList<>();
    
    /**
     * Constructor for Agenga Object
     *
     * @param mContext Content of the text
     * @param eventItems    Name of the Event
     */

    public Agenda(Context mContext, ArrayList<EventItem> eventItems) {
        this.mContext = mContext;
        this.eventItems = eventItems;
    }

    public ArrayList<EventItem> getEventItems() {
        return null;
    }
}


