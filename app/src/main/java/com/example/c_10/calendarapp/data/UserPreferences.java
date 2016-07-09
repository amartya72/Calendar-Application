package com.example.c_10.calendarapp.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mStrPref = "USER_SP";
    private Context mContext;

    public UserPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void saveColorOfDay(ColorItem colorItem) {
        mSharedPreferences = mContext.getSharedPreferences(mStrPref, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.putString(colorItem.getDay()
                + "/" + colorItem.getMonth()
                + "/" + colorItem.getYear(), colorItem.getColor());

        mEditor.commit();
    }

    public String getColorOfDay(int day, int month, int year) {
        mSharedPreferences = mContext.getSharedPreferences(mStrPref, Context.MODE_PRIVATE);
        String date = day + "/" + month + "/" + year;
        return mSharedPreferences.getString(date, null);
    }
}
