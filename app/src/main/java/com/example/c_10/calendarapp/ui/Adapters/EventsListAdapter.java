package com.example.c_10.calendarapp.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.data.EventItem;

import java.util.ArrayList;


public class EventsListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<EventItem> eventItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private boolean mContinue = false;

    public EventsListAdapter(Context mContext, ArrayList<EventItem> eventItems) {
        this.mContext = mContext;
        this.eventItems = eventItems;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 24;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.event_item, null);
        TextView txtTime = (TextView) view.findViewById(R.id.text_view_time);
        TextView txtName = (TextView) view.findViewById(R.id.text_view_event);
        ImageView imgCircle = (ImageView) view.findViewById(R.id.image_view_circle);
        ImageView imgCircleStart = (ImageView) view.findViewById(R.id.image_view_circle_start);
        ImageView imgCircleEnd = (ImageView) view.findViewById(R.id.image_view_circle_end);
        if (position < 10) {
            txtTime.setText("0" + position + ":00");
        } else {
            txtTime.setText(position + ":00");
        }

        for (EventItem eventItem : eventItems) {
            int startTime = Integer.parseInt(eventItem.getStartTime().split(":")[0]);
            int endTime = Integer.parseInt(eventItem.getEndTime().split(":")[0]);
            if (position == startTime) {
                imgCircle.setVisibility(View.VISIBLE);
                imgCircleStart.setVisibility(View.VISIBLE);
                txtName.setText(eventItem.getEventName());
                //mContinue=true;
                //Log.e("success-"+position, eventItem.getEventName());
            } else if (position == endTime) {
                imgCircle.setVisibility(View.VISIBLE);
                imgCircleEnd.setVisibility(View.VISIBLE);
                //mContinue=false;
            }
        }

        /*if(mContinue){
            imgCircleNew.setVisibility(View.VISIBLE);
        }else{
            imgCircleNew.setVisibility(View.INVISIBLE);
        }*/
        //txtName.setText(eventItems.get(position).getEventName());

        return view;
    }
}
