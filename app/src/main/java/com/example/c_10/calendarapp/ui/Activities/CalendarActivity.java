package com.example.c_10.calendarapp.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.controller.EventManager;
import com.example.c_10.calendarapp.data.EventItem;
import com.example.c_10.calendarapp.data.UserPreferences;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.entity.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
    * @author   Roy
 */

public class CalendarActivity extends AppCompatActivity {

    private TextView mTxtDate, mTxtMonth;
    private Button mBtnManageEvent;
    private String currDate = null;
    private FlexibleCalendarView mFlexibleCalendarView;
    private EventManager mEventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**
         *
         * @param onDateClick   Displays when date is clicked
         * @param onMonthChange Displays when month is changed
         * @param onClick   Views when clicked
         * @param getColor  Displays when selected color
         */

        mTxtMonth = (TextView) findViewById(R.id.text_view_month);
        mTxtDate = (TextView) findViewById(R.id.text_view_selected_date);
        mBtnManageEvent = (Button) findViewById(R.id.button_manage_events);

        mFlexibleCalendarView = (FlexibleCalendarView) findViewById(R.id.calendar_view);
        mFlexibleCalendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {
                currDate = day + "/" + (month + 1) + "/" + year;
                mTxtDate.setText(currDate);
                mTxtMonth.setText(getMonth(month));
            }
        });

        mFlexibleCalendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, int direction) {
                currDate = "1/" + (month + 1) + "/" + year;
                mTxtDate.setText(currDate);
                mTxtMonth.setText(getMonth(month));
            }
        });

        mEventManager = new EventManager(CalendarActivity.this);
        final ArrayList<EventItem> eventItems = mEventManager.getAllEvents();

        mFlexibleCalendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<? extends Event> getEventsForTheDay(int year, int month, int day) {

                for (EventItem eventItem : eventItems) {
                    if (year == eventItem.getYear()
                            && month == (eventItem.getMonth() - 1)
                            && day == eventItem.getDay()) {
                        List<CalendarEvent> eventColors = new ArrayList<>(1);
                        eventColors.add(new CalendarEvent(getColor(day, month + 1, year)));
                        return eventColors;
                    }
                }
                return null;
            }
        });

        mBtnManageEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, ManageEventsActivity.class);
                intent.putExtra("date", currDate);
                startActivity(intent);
            }
        });

        currDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        mTxtDate.setText(currDate);
        int currMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        mTxtMonth.setText(getMonth(currMonth - 1));

        getSupportActionBar().setTitle(R.string.action_monthly_view);
    }

    private String getMonth(int month) {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }
        return null;
    }

    private int getColor(int day, int month, int year) {
        UserPreferences userPreferences = new UserPreferences(CalendarActivity.this);
        String colorVal = userPreferences.getColorOfDay(day, month, year);
        if (colorVal != null) {
            if (colorVal.equals("Red")) {
                return R.color.md_red_500;
            } else if (colorVal.equals("Green")) {
                return R.color.md_green_500;
            } else if (colorVal.equals("Blue")) {
                return R.color.md_blue_500;
            } else if (colorVal.equals("Pink")) {
                return R.color.md_pink_500;
            } else if (colorVal.equals("Grey")) {
                return R.color.md_grey_500;
            } else if (colorVal.equals("Black")) {
                return R.color.md_black_1000;
            } else if (colorVal.equals("Yellow")) {
                return R.color.md_yellow_500;
            } else if (colorVal.equals("Purple")) {
                return R.color.md_deep_purple_500;
            }
        }
        return R.color.primary;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_events) {
            new EventManager(CalendarActivity.this).deleteAllEvents();
            return true;
        }

        if(id == R.id.action_agenda_view){
            Intent intent=new Intent(CalendarActivity.this, AgendaActivity.class);
            intent.putExtra("year", 0);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_year_view){
            Intent intent=new Intent(CalendarActivity.this, YearlyActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_monthly_view){
            //Intent intent=new Intent(CalendarActivity.this, CalendarActivity.class);
            //startActivity(intent);
            //finish();
        }

        if (id == R.id.action_daily_view){
            Intent intent=new Intent(CalendarActivity.this, DailyActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
