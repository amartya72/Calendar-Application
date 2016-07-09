package com.example.c_10.calendarapp.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.c_10.calendarapp.data.Database.DbAdapter;
import com.example.c_10.calendarapp.data.EventItem;

import java.util.ArrayList;

public class AgendaManager {
    private Context mContext;
    private ArrayList<EventItem> eventItems;

    public AgendaManager(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<EventItem> getEventItems() {
        return getEventItemsOrderByDate();
    }

    public ArrayList<EventItem> getEventItemsForYear(int year){
        return getEventItemsYearOrderByDate(year);
    }

    public ArrayList<EventItem> getEventItemsForDay(int day, int month, int year){
        return getEventItemsDayOrderByDate(day, month, year);
    }

    private ArrayList<EventItem> getEventItemsOrderByDate() {
        ArrayList<EventItem> eventItemsTemp = new ArrayList<>();
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();
        String query = "SELECT * FROM events ORDER BY year, month, day, start_time";
        Cursor cursor = dbAdapter.selectQuery(query);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    EventItem eventItem = new EventItem();
                    eventItem.setDay(cursor.getInt(cursor.getColumnIndex("day")));
                    eventItem.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
                    eventItem.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    eventItem.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
                    eventItem.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
                    eventItem.setEventName(cursor.getString(cursor.getColumnIndex("event_name")));
                    eventItem.setEventDescription(cursor.getString(cursor.getColumnIndex("event_desc")));
                    eventItem.setEventCategory(cursor.getString(cursor.getColumnIndex("event_category")));
                    eventItemsTemp.add(eventItem);
                } while (cursor.moveToNext());
            }
        }
        dbAdapter.close();
        return eventItemsTemp;
    }

    private ArrayList<EventItem> getEventItemsYearOrderByDate(int year){
        ArrayList<EventItem> eventItemsTemp = new ArrayList<>();
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();
        String query = "SELECT * FROM events " +
                "WHERE year='"+year+"' "+
                "ORDER BY year, month, day, start_time";
        Cursor cursor = dbAdapter.selectQuery(query);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    EventItem eventItem = new EventItem();
                    eventItem.setDay(cursor.getInt(cursor.getColumnIndex("day")));
                    eventItem.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
                    eventItem.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    eventItem.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
                    eventItem.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
                    eventItem.setEventName(cursor.getString(cursor.getColumnIndex("event_name")));
                    eventItem.setEventDescription(cursor.getString(cursor.getColumnIndex("event_desc")));
                    eventItem.setEventCategory(cursor.getString(cursor.getColumnIndex("event_category")));
                    eventItemsTemp.add(eventItem);
                } while (cursor.moveToNext());
            }
        }
        dbAdapter.close();
        return eventItemsTemp;
    }

    private ArrayList<EventItem> getEventItemsDayOrderByDate(int day, int month, int year) {
        ArrayList<EventItem> eventItemsTemp = new ArrayList<>();
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();
        String query = "SELECT * FROM events " +
                "WHERE day='"+day+"' AND "+
                "month='"+month+"' AND "+
                "year='"+year+"' "+
                "ORDER BY year, month, day, start_time";
        Cursor cursor = dbAdapter.selectQuery(query);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    EventItem eventItem = new EventItem();
                    eventItem.setDay(cursor.getInt(cursor.getColumnIndex("day")));
                    eventItem.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
                    eventItem.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    eventItem.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
                    eventItem.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
                    eventItem.setEventName(cursor.getString(cursor.getColumnIndex("event_name")));
                    eventItem.setEventDescription(cursor.getString(cursor.getColumnIndex("event_desc")));
                    eventItem.setEventCategory(cursor.getString(cursor.getColumnIndex("event_category")));
                    eventItemsTemp.add(eventItem);
                } while (cursor.moveToNext());
            }
        }
        dbAdapter.close();
        return eventItemsTemp;
    }
}
