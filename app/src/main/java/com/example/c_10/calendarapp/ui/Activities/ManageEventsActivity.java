package com.example.c_10.calendarapp.ui.Activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.controller.EventManager;
import com.example.c_10.calendarapp.controller.Events;
import com.example.c_10.calendarapp.data.UserPreferences;
import com.example.c_10.calendarapp.ui.Adapters.EventsListAdapter;
import com.example.c_10.calendarapp.ui.Adapters.EventsRecyclerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ManageEventsActivity extends AppCompatActivity {

    private TextView mTxtDay;
    private ListView mListEvents;
    private RecyclerView mRecyclerViewEvents;
    private EventsListAdapter mEventsListAdapter;
    private EventsRecyclerAdapter mEventsRecyclerAdapter;
    private EventManager mEventManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private Events mEvents;
    private FrameLayout mFrameLayout;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEventManager = new EventManager(ManageEventsActivity.this);

        mListEvents = (ListView) findViewById(R.id.list_view_events);
        mRecyclerViewEvents = (RecyclerView) findViewById(R.id.recycler_view_events);
        mTxtDay = (TextView) findViewById(R.id.text_view_day);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        String[] dateArr = getDate().split("/");

        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]);

       

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerViewEvents.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewEvents.setLayoutManager(mLayoutManager);

        mEvents = new Events(ManageEventsActivity.this, mEventManager.getAllEventsOnDate(getDate()));

        mEventsRecyclerAdapter = new EventsRecyclerAdapter(ManageEventsActivity.this
                , mEvents.getStartTimeOccurs()
                , mEvents.getEndTimeOccurs()
                , mEvents.getEventOccurs()
                , mEvents.getEventItemsNew());
        mRecyclerViewEvents.setAdapter(mEventsRecyclerAdapter);

        //mRecyclerViewEvents.
        mTxtDay.setText(getDay());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getDate());
        getSupportActionBar().setSubtitle("");
    }

    private String getDate() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString("date", "");
    }

    private String getDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
        }
        return null;
    }

    private int getColor(int day, int month, int year) {
        UserPreferences userPreferences = new UserPreferences(ManageEventsActivity.this);
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
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }


    //don't modify this
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            EventManager eventManager = new EventManager(ManageEventsActivity.this);
            eventManager.addEvent(getDate());
            return true;
        }

        if (id == R.id.action_color) {
            EventManager eventManager = new EventManager(ManageEventsActivity.this);
            eventManager.colorManager(getDate());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
