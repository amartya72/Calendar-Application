package com.example.c_10.calendarapp.data;

public class EventItem {
    private int day, month, year;
    private String eventName, eventDescription, eventCategory, startTime, endTime;

    public EventItem() {
        //empty constructor
    }

    public EventItem(EventItem eventItem) {
        this.day = eventItem.getDay();
        this.month = eventItem.getMonth();
        this.year = eventItem.getYear();
        this.startTime = eventItem.getStartTime();
        this.endTime = eventItem.getEndTime();
        this.eventName = eventItem.getEventName();
        this.eventDescription = eventItem.getEventDescription();
        this.eventCategory = eventItem.getEventCategory();
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }
}
