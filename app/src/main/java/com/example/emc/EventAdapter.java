package com.example.emc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }

        Event currentEvent = getItem(position);

        TextView dayTextView = view.findViewById(R.id.tv_day);
        dayTextView.setText(currentEvent.getDayNumber());

        TextView monthTextView = view.findViewById(R.id.tv_month);
        monthTextView.setText(currentEvent.getMonth());

        TextView eventName = view.findViewById(R.id.tv_event_name);
        eventName.setText(currentEvent.getName());

        TextView timeTextView = view.findViewById(R.id.tv_event_time);
        timeTextView.setText(currentEvent.getDayName()+" "+currentEvent.getTime());

        TextView numOfPeople = view.findViewById(R.id.tv_num_of_people);
        numOfPeople.setText(currentEvent.getPeopleNumber()+" Are going");

        return view;
    }
}
