package com.example.c_10.calendarapp.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.controller.AgendaManager;
import com.example.c_10.calendarapp.controller.EventManager;
import com.example.c_10.calendarapp.data.EventItem;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class YearlyActivity extends AppCompatActivity {

    private TextView mTxtEventAvailable;
    private DatePicker mDatePickerYear;
    private Button mBtnAgendaView;
    private AgendaManager mAgendaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTxtEventAvailable = (TextView) findViewById(R.id.text_view_event_available);
        mDatePickerYear = (DatePicker) findViewById(R.id.date_picker_year);
        mBtnAgendaView = (Button) findViewById(R.id.button_agenda_view);

        mAgendaManager = new AgendaManager(YearlyActivity.this);

        hideDayMonthFromDatPicker();
        mBtnAgendaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YearlyActivity.this, AgendaActivity.class);
                intent.putExtra("year", mDatePickerYear.getYear());
                startActivity(intent);
                finish();
            }
        });

        ArrayList<EventItem> eventItems = mAgendaManager.getEventItemsForYear(mDatePickerYear.getYear());
        mTxtEventAvailable.setText(eventItems.size() + " events available in the year");

        mDatePickerYear.init(2015, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ArrayList<EventItem> eventItems = mAgendaManager.getEventItemsForYear(year);
                mTxtEventAvailable.setText(eventItems.size() + " events available in the year");
            }
        });

    }

    private void hideDayMonthFromDatPicker() {
        try {
            Field f[] = mDatePickerYear.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mDaySpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = new Object();
                    yearPicker = field.get(mDatePickerYear);
                    ((View) yearPicker).setVisibility(View.GONE);
                }
                if (field.getName().equals("mMonthSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = new Object();
                    yearPicker = field.get(mDatePickerYear);
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        } catch (SecurityException e) {
            Log.d("ERROR", e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        } catch (IllegalAccessException e) {
            Log.d("ERROR", e.getMessage());
        }
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
            new EventManager(YearlyActivity.this).deleteAllEvents();
            return true;
        }

        if(id == R.id.action_agenda_view){
            Intent intent=new Intent(YearlyActivity.this, AgendaActivity.class);
            intent.putExtra("year", 0);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_year_view){
            //Intent intent=new Intent(YearlyActivity.this, YearlyActivity.class);
            //startActivity(intent);
            //finish();
        }


        if(id == R.id.action_monthly_view){
            Intent intent=new Intent(YearlyActivity.this, CalendarActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.action_daily_view){
            Intent intent=new Intent(YearlyActivity.this, DailyActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
