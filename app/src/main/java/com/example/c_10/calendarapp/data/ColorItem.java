package com.example.c_10.calendarapp.data;

/**
 *
 * @author Roy
 *
 */


public class ColorItem {
    private int day, month, year;
    private String color;
    
    /**
     * Constructor for ColorItem Object
     *
     * @param getDay    Returns day
     * @param getMonth    Returns Month
     * @param getYear    Returns year
     * @param setDay    Set day
     * @param setMonth    set Month
     * @param setYear    set year
     */

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
