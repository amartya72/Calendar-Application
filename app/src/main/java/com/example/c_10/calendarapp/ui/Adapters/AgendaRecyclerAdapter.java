package com.example.c_10.calendarapp.ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c_10.calendarapp.R;
import com.example.c_10.calendarapp.controller.EventManager;
import com.example.c_10.calendarapp.data.EventItem;

import java.util.ArrayList;

public class AgendaRecyclerAdapter extends RecyclerView.Adapter<AgendaRecyclerAdapter.ViewHolderAgenda> {

    private Context mContext;
    private ArrayList<EventItem> mEventItems;

    public AgendaRecyclerAdapter(Context mContext, ArrayList<EventItem> mEventItems) {
        this.mContext = mContext;
        this.mEventItems = mEventItems;
    }

    @Override
    public AgendaRecyclerAdapter.ViewHolderAgenda onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_item, parent, false);
        ViewHolderAgenda viewHolderAgenda=new ViewHolderAgenda(view);
        viewHolderAgenda.setContext(mContext);
        viewHolderAgenda.setEventItems(mEventItems);
        return viewHolderAgenda;
    }

    @Override
    public void onBindViewHolder(AgendaRecyclerAdapter.ViewHolderAgenda holder, int position) {
        String date=mEventItems.get(position).getDay()+"/"+mEventItems.get(position).getMonth()+"/"+mEventItems.get(position).getYear();
        String time=mEventItems.get(position).getStartTime()+" - "+mEventItems.get(position).getEndTime();
        String eventName=mEventItems.get(position).getEventName();
        String eventDesc=mEventItems.get(position).getEventDescription();
        String eventCategory=mEventItems.get(position).getEventCategory();

        holder.txtDate.setText(date);
        holder.txtTime.setText(time);
        holder.txtEventName.setText(eventName);
        holder.txtEventDesc.setText(eventDesc);
        holder.txtEventCategory.setText(eventCategory);
    }

    @Override
    public int getItemCount() {
        return mEventItems.size();
    }

    public class ViewHolderAgenda extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView txtDate, txtTime, txtEventName, txtEventDesc, txtEventCategory;
        private Context mContext;
        private EventManager mEventManager;
        private ArrayList<EventItem> mEventItems = new ArrayList<>();
        public ViewHolderAgenda(View itemView) {
            super(itemView);
            txtDate= (TextView) itemView.findViewById(R.id.text_view_date);
            txtTime= (TextView) itemView.findViewById(R.id.text_view_time);
            txtEventName= (TextView) itemView.findViewById(R.id.text_view_event_name);
            txtEventDesc= (TextView) itemView.findViewById(R.id.text_view_event_desc);
            txtEventCategory= (TextView) itemView.findViewById(R.id.text_view_event_category);
            itemView.setOnLongClickListener(this);
        }

        public void setContext(Context mContext) {
            this.mContext = mContext;
        }

        public void setEventItems(ArrayList<EventItem> mEventItems) {
            this.mEventItems = mEventItems;
        }

        @Override
        public boolean onLongClick(View v) {
            Log.e("long click", "true " + getAdapterPosition());
            mEventManager = new EventManager(mContext);
            mEventManager.viewEvent(mEventItems.get(getAdapterPosition()));
            return false;
        }
    }
}
