package com.example.c_10.calendarapp.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.controller.AgendaManager;
import com.example.c_10.calendarapp.controller.EventManager;
import com.example.c_10.calendarapp.controller.SimpleDividerItemDecoration;
import com.example.c_10.calendarapp.ui.Adapters.AgendaRecyclerAdapter;

/**
 * 
 * @author  Roy
 *
 */

public class AgendaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewAgenda;
    private AgendaRecyclerAdapter mAgendaRecyclerAdapter;
    private AgendaManager mAgendaManager;
    private RecyclerView.LayoutManager mLayoutManager;
    
    /**
     *
     * @param Year Tells which year
     * @param Day    Tells which day
     * @param Month Tells which month
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerViewAgenda = (RecyclerView) findViewById(R.id.recycler_view_agenda);

        mAgendaManager=new AgendaManager(AgendaActivity.this);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewAgenda.setLayoutManager(mLayoutManager);

        mRecyclerViewAgenda.setHasFixedSize(true);
        mRecyclerViewAgenda.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewAgenda.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()
        ));

        if(getYear()!=0) {
            mAgendaRecyclerAdapter = new AgendaRecyclerAdapter(AgendaActivity.this
                    , mAgendaManager.getEventItemsForYear(getYear()));
        }else if(!getDate().isEmpty()){
            String[] strArr=getDate().split("/");

            int day= Integer.parseInt(strArr[0]);
            int month= Integer.parseInt(strArr[1]);
            int year= Integer.parseInt(strArr[2]);

            mAgendaRecyclerAdapter = new AgendaRecyclerAdapter(AgendaActivity.this
                    , mAgendaManager.getEventItemsForDay(day, month, year));
        }else{
            mAgendaRecyclerAdapter = new AgendaRecyclerAdapter(AgendaActivity.this
                    , mAgendaManager.getEventItems());
        }

        mRecyclerViewAgenda.setAdapter(mAgendaRecyclerAdapter);
    }

    private int getYear(){
        Bundle bundle=getIntent().getExtras();
        return bundle.getInt("year",0);
    }
    private String getDate(){
        Bundle bundle=getIntent().getExtras();
        return bundle.getString("date", "");
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
            new EventManager(AgendaActivity.this).deleteAllEvents();
            return true;
        }

        if(id == R.id.action_agenda_view){
            //Intent intent=new Intent(AgendaActivity.this, AgendaActivity.class);
            //intent.putExtra("year", 0);
            //startActivity(intent);
            //finish();
        }

        if(id == R.id.action_year_view){
            Intent intent=new Intent(AgendaActivity.this, YearlyActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_monthly_view){
            Intent intent=new Intent(AgendaActivity.this, CalendarActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.action_daily_view){
            Intent intent=new Intent(AgendaActivity.this, DailyActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
