package com.example.c_10.calendarapp.ui.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.controller.EventManager;
import com.example.c_10.calendarapp.data.EventItem;

import java.util.ArrayList;


public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolderEvents> {
    private Context mContext;
    private ArrayList<EventItem> eventItems = new ArrayList<>();
    private ArrayList<Drawable> startTimeOccurs = new ArrayList<>();
    private ArrayList<Drawable> endTimeOccurs = new ArrayList<>();
    private ArrayList<Drawable> eventOccurs = new ArrayList<>();

    public EventsRecyclerAdapter(Context context
            , ArrayList<Drawable> startTimeOccurs
            , ArrayList<Drawable> endTimeOccurs
            , ArrayList<Drawable> eventOccurs
            , ArrayList<EventItem> eventItems) {
        this.mContext = context;
        this.eventItems = eventItems;
        this.startTimeOccurs = startTimeOccurs;
        this.endTimeOccurs = endTimeOccurs;
        this.eventOccurs = eventOccurs;
    }

    @Override
    public EventsRecyclerAdapter.ViewHolderEvents onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        ViewHolderEvents viewHolder = new ViewHolderEvents(view);
        viewHolder.setContext(mContext);
        viewHolder.setEventItems(eventItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventsRecyclerAdapter.ViewHolderEvents holder, int position) {
        if (position < 10) {
            holder.txtTime.setText("0" + position + ":00");
        } else {
            holder.txtTime.setText(position + ":00");
        }
        /*for(EventItem eventItem : eventItems){
            if(position==eventItem.getStartTime()){
                holder.imgCircle.setVisibility(View.VISIBLE);
                holder.imgCircleStart.setVisibility(View.VISIBLE);
                holder.txtName.setText(eventItem.getEventName());
                //mContinue=true;
                //Log.e("success-"+position, eventItem.getEventName());
            }else if (position==eventItem.getEndTime()){
                holder.imgCircle.setVisibility(View.VISIBLE);
                holder.imgCircleEnd.setVisibility(View.VISIBLE);
                //mContinue=false;
            }
        }*/
        holder.imgCircleStart.setImageDrawable(startTimeOccurs.get(position));
        holder.imgCircleEnd.setImageDrawable(endTimeOccurs.get(position));
        holder.imgCircle.setImageDrawable(eventOccurs.get(position));
        /*if(startTimeOccurs.get(position)==null){
            holder.imgCircleStart.setVisibility(View.GONE);
        }
        if(endTimeOccurs.get(position)==null){
            holder.imgCircleEnd.setVisibility(View.GONE);
        }
        if(eventOccurs.get(position)==null){
            holder.imgCircle.setVisibility(View.GONE);
        }*/

        holder.txtName.setText(eventItems.get(position).getEventName());
    }

    @Override
    public int getItemCount() {
        return 24;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class ViewHolderEvents extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtTime, txtName;
        ImageView imgCircle, imgCircleStart, imgCircleEnd;
        Context context;
        EventManager eventManager;
        ArrayList<EventItem> eventItems = new ArrayList<>();

        public ViewHolderEvents(View itemView) {
            super(itemView);
            txtTime = (TextView) itemView.findViewById(R.id.text_view_time);
            txtName = (TextView) itemView.findViewById(R.id.text_view_event);
            imgCircle = (ImageView) itemView.findViewById(R.id.image_view_circle);
            imgCircleStart = (ImageView) itemView.findViewById(R.id.image_view_circle_start);
            imgCircleEnd = (ImageView) itemView.findViewById(R.id.image_view_circle_end);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Log.e("long click", "true " + getAdapterPosition());
            eventManager = new EventManager(context);
            eventManager.viewEvent(eventItems.get(getAdapterPosition()));
            //Toast.makeText(context, "true " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return false;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void setEventItems(ArrayList<EventItem> eventItems) {
            this.eventItems = eventItems;
        }
    }
}
