package com.example.c_10.calendarapp.controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.data.ColorItem;
import com.example.c_10.calendarapp.data.Database.DbAdapter;
import com.example.c_10.calendarapp.data.EventItem;
import com.example.c_10.calendarapp.data.UserPreferences;

import java.util.ArrayList;


public class EventManager {
    private Context mContext;
    private ArrayList<EventItem> eventItems = new ArrayList<>();
    private EditText mETxtStartTime, mETxtEndTime, mETxtName, mETxtDesc, mETxtCategory;

    public EventManager(Context mContext) {
        this.mContext = mContext;
    }

    public void colorManager(final String date) {
        CharSequence[] colors = new CharSequence[]{"Red", "Green", "Blue", "Pink", "Grey", "Black", "Yellow", "Purple"};
        MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.pick_color)
                .items(colors)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        ColorItem colorItem = new ColorItem();
                        String[] dateArr = date.split("/");
                        int day = Integer.parseInt(dateArr[0]);
                        int month = Integer.parseInt(dateArr[1]);
                        int year = Integer.parseInt(dateArr[2]);
                        colorItem.setDay(day);
                        colorItem.setMonth(month);
                        colorItem.setYear(year);
                        colorItem.setColor(String.valueOf(text));
                        UserPreferences userPreferences = new UserPreferences(mContext);
                        userPreferences.saveColorOfDay(colorItem);
                    }
                }).build();
        materialDialog.show();
    }

    public void addEvent(final String date) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.add_new_event)
                .customView(R.layout.dialog_add_event, true)
                .positiveText(R.string.action_add)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        String startTime = mETxtStartTime.getText().toString();
                        String endTime = mETxtEndTime.getText().toString();
                        String name = mETxtName.getText().toString();
                        String desc = mETxtDesc.getText().toString();
                        String category = mETxtCategory.getText().toString();
                        if (startTime.isEmpty() || endTime.isEmpty() || name.isEmpty() || desc.isEmpty() || category.isEmpty()) {
                            Toast.makeText(mContext, "Please don't leave values blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] dateItems = date.split("/");
                            int day = Integer.parseInt(dateItems[0]);
                            int month = Integer.parseInt(dateItems[1]);
                            int year = Integer.parseInt(dateItems[2]);
                            EventItem eventItem = new EventItem();
                            eventItem.setDay(day);
                            eventItem.setMonth(month);
                            eventItem.setYear(year);
                            eventItem.setStartTime(startTime);
                            eventItem.setEndTime(endTime);
                            eventItem.setEventName(name);
                            eventItem.setEventDescription(desc);
                            eventItem.setEventCategory(category);
                            addNewEventInDb(eventItem);
                        }
                    }
                }).build();

        mETxtStartTime = (EditText) materialDialog.getView().findViewById(R.id.edit_text_start_time);
        mETxtEndTime = (EditText) materialDialog.getView().findViewById(R.id.edit_text_end_time);
        mETxtCategory = (EditText) materialDialog.getView().findViewById(R.id.edit_text_category);
        mETxtName = (EditText) materialDialog.getView().findViewById(R.id.edit_text_name);
        mETxtDesc = (EditText) materialDialog.getView().findViewById(R.id.edit_text_desc);

        mETxtEndTime.setEnabled(true);
        mETxtStartTime.setEnabled(true);
        mETxtCategory.setEnabled(true);
        mETxtName.setEnabled(true);
        mETxtDesc.setEnabled(true);

        materialDialog.show();
    }

    public void viewEvent(final EventItem eventItem) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.modify_event)
                .customView(R.layout.dialog_add_event, true)
                .positiveText(R.string.update)
                .negativeText(R.string.cancel)
                .neutralText(R.string.share)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        modifyEvent(eventItem);
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        //share the event here
                        String title = eventItem.getDay()
                                + "/" + eventItem.getMonth()
                                + "/" + eventItem.getYear()
                                + " - Event shared: " + eventItem.getEventName();
                        String desc = "Event name: " + eventItem.getEventName()
                                + "\nEvent description: " + eventItem.getEventDescription()
                                + "\nEvent category: " + eventItem.getEventCategory()
                                + "\nStart time: " + eventItem.getStartTime()
                                + "\nEnd time: " + eventItem.getEndTime();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, desc);
                        sendIntent.setType("text/plain");
                        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(Intent.createChooser(sendIntent, "Share with..."));
                    }
                }).build();

        mETxtStartTime = (EditText) materialDialog.getView().findViewById(R.id.edit_text_start_time);
        mETxtEndTime = (EditText) materialDialog.getView().findViewById(R.id.edit_text_end_time);
        mETxtCategory = (EditText) materialDialog.getView().findViewById(R.id.edit_text_category);
        mETxtName = (EditText) materialDialog.getView().findViewById(R.id.edit_text_name);
        mETxtDesc = (EditText) materialDialog.getView().findViewById(R.id.edit_text_desc);

        mETxtStartTime.setText("" + eventItem.getStartTime());
        mETxtEndTime.setText("" + eventItem.getEndTime());
        mETxtCategory.setText("" + eventItem.getEventCategory());
        mETxtName.setText(eventItem.getEventName());
        mETxtDesc.setText(eventItem.getEventDescription());

        materialDialog.show();
    }

    public void modifyEvent(final EventItem eventItem) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.modify_event)
                .customView(R.layout.dialog_add_event, true)
                .positiveText(R.string.modify)
                .negativeText(R.string.cancel)
                .neutralText(R.string.delete)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        String startTime = mETxtStartTime.getText().toString();
                        String endTime = mETxtEndTime.getText().toString();
                        String name = mETxtName.getText().toString();
                        String desc = mETxtDesc.getText().toString();
                        String category = mETxtCategory.getText().toString();
                        if (startTime.isEmpty() || endTime.isEmpty()
                                || name.isEmpty() || desc.isEmpty()
                                || category.isEmpty()) {
                            Toast.makeText(mContext, "Please don't leave values blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            EventItem newEventItem = new EventItem(eventItem);
                            newEventItem.setStartTime(startTime);
                            newEventItem.setEndTime(endTime);
                            newEventItem.setEventName(name);
                            newEventItem.setEventDescription(desc);
                            newEventItem.setEventCategory(category);
                            Log.e("s_time", "" + eventItem.getStartTime());
                            Log.e("e_time", "" + eventItem.getEndTime());
                            updateEventInDb(eventItem, newEventItem);
                            //Toast.makeText(mContext, "Not yet implemented.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        //share the event here
                        deleteEvent(eventItem);
                        //Toast.makeText(mContext, "Not yet implemented.", Toast.LENGTH_SHORT).show();
                    }
                }).build();

        mETxtStartTime = (EditText) materialDialog.getView().findViewById(R.id.edit_text_start_time);
        mETxtEndTime = (EditText) materialDialog.getView().findViewById(R.id.edit_text_end_time);
        mETxtCategory = (EditText) materialDialog.getView().findViewById(R.id.edit_text_category);
        mETxtName = (EditText) materialDialog.getView().findViewById(R.id.edit_text_name);
        mETxtDesc = (EditText) materialDialog.getView().findViewById(R.id.edit_text_desc);

        mETxtStartTime.setText("" + eventItem.getStartTime());
        mETxtEndTime.setText("" + eventItem.getEndTime());
        mETxtCategory.setText("" + eventItem.getEventCategory());
        mETxtName.setText(eventItem.getEventName());
        mETxtDesc.setText(eventItem.getEventDescription());

        mETxtEndTime.setEnabled(true);
        mETxtStartTime.setEnabled(true);
        mETxtCategory.setEnabled(true);
        mETxtName.setEnabled(true);
        mETxtDesc.setEnabled(true);

        materialDialog.show();
    }

    public void deleteEvent(final EventItem eventItem) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.delete_event)
                .content(R.string.are_you_sure)
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteEventInDb(eventItem);
                    }
                }).build();

        materialDialog.show();
    }

    public void deleteAllEvents() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.delete_all_event)
                .content(R.string.are_you_sure)
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteAllEventsInDb();
                    }
                }).build();

        materialDialog.show();
    }

    public ArrayList<EventItem> getAllEvents() {
        ArrayList<EventItem> eventItemsTemp = new ArrayList<>();
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();
        String query = "SELECT * FROM events";
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

    public ArrayList<EventItem> getAllEventsOnDate(String date) {

        String[] dateItems = date.split("/");
        int day = Integer.parseInt(dateItems[0]);
        int month = Integer.parseInt(dateItems[1]);
        int year = Integer.parseInt(dateItems[2]);
        ArrayList<EventItem> eventItemsTemp = new ArrayList<>();

        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();

        String query = "SELECT * FROM events " +
                "WHERE day='" + day + "' " +
                "AND month='" + month + "' " +
                "AND year='" + year + "' " +
                "ORDER BY start_time";

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

    private void addNewEventInDb(EventItem eventItem) {
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();

        String query = "INSERT INTO events(day, month, year, start_time, end_time, event_name, event_desc, event_category) " +
                "VALUES ('" + eventItem.getDay() + "','"
                + eventItem.getMonth() + "','"
                + eventItem.getYear() + "','"
                + eventItem.getStartTime() + "','"
                + eventItem.getEndTime() + "','"
                + eventItem.getEventName() + "', '"
                + eventItem.getEventDescription() + "', '"
                + eventItem.getEventCategory() + "')";

        dbAdapter.executeQuery(query);
        dbAdapter.close();
        Toast.makeText(mContext, "Event Added", Toast.LENGTH_SHORT).show();
    }

    private void updateEventInDb(EventItem eventItemOld, EventItem eventItemNew) {
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();

        String query = "UPDATE events " +
                "SET start_time='" + eventItemNew.getStartTime() + "', " +
                "end_time='" + eventItemNew.getEndTime() + "', " +
                "event_name='" + eventItemNew.getEventName() + "', " +
                "event_desc='" + eventItemNew.getEventDescription() + "', " +
                "event_category='" + eventItemNew.getEventCategory() + "' " +
                "WHERE day='" + eventItemOld.getDay() + "' " +
                "AND month='" + eventItemOld.getMonth() + "' " +
                "AND year='" + eventItemOld.getYear() + "' " +
                "AND start_time='" + eventItemOld.getStartTime() + "' " +
                "AND end_time='" + eventItemOld.getEndTime() + "'";

        Log.e("Update Query", query);

        dbAdapter.executeQuery(query);
        dbAdapter.close();
        Toast.makeText(mContext, "Event Updated", Toast.LENGTH_SHORT).show();
    }

    private void deleteEventInDb(EventItem eventItem) {
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();

        String query = "DELETE FROM events " +
                "WHERE day='" + eventItem.getDay() + "' " +
                "AND month='" + eventItem.getMonth() + "' " +
                "AND year='" + eventItem.getYear() + "' " +
                "AND start_time='" + eventItem.getStartTime() + "' " +
                "AND end_time='" + eventItem.getEndTime() + "'";

        Log.e("Delete Query", query);

        dbAdapter.executeQuery(query);
        dbAdapter.close();
        Toast.makeText(mContext, "Event deleted", Toast.LENGTH_SHORT).show();
    }

    private void deleteAllEventsInDb() {
        DbAdapter dbAdapter = new DbAdapter(mContext);
        dbAdapter.createDatabase();
        dbAdapter.open();

        String query = "DELETE FROM events";

        Log.e("Delete All Query", query);

        dbAdapter.executeQuery(query);
        dbAdapter.close();
        Toast.makeText(mContext, "Event deleted", Toast.LENGTH_SHORT).show();
    }

    /*public interface OnDataSetChangedListener{
        public void onDataSetChanged(Event)
    }*/

}
