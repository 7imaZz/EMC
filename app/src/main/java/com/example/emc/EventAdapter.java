package com.example.emc;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    //Declaring Firebase Vars
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public EventAdapter(Context context, ArrayList<Event> events) {

        super(context, 0, events);

        //Initializing Firebase vars
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Events");
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }

        final Event currentEvent = getItem(position);

        final TextView dayTextView = view.findViewById(R.id.tv_day);
        dayTextView.setText(currentEvent.getDayNumber());

        TextView monthTextView = view.findViewById(R.id.tv_month);
        monthTextView.setText(currentEvent.getMonth());

        final TextView eventName = view.findViewById(R.id.tv_event_name);
        eventName.setText(currentEvent.getName());

        TextView timeTextView = view.findViewById(R.id.tv_event_time);
        timeTextView.setText(currentEvent.getDayName()+" "+currentEvent.getTime());

        final TextView numOfPeople = view.findViewById(R.id.tv_num_of_people);
        numOfPeople.setText(currentEvent.getPeopleNumber()+" Are going");

        final ImageView going = view.findViewById(R.id.img_going);
        if (currentEvent.getGoing()==-1)
            going.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        else
            going.setBackgroundResource(R.drawable.ic_star_black_24dp);

        going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentEvent.getGoing()==-1){
                    currentEvent.setGoing(1);
                    going.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    currentEvent.setPeopleNumber(currentEvent.getPeopleNumber()+1);
                    databaseReference.child(currentEvent.getId()).child("peopleNumber").setValue(currentEvent.getPeopleNumber());
                } else{
                    currentEvent.setGoing(-1);
                    going.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    currentEvent.setPeopleNumber(currentEvent.getPeopleNumber()-1);
                    databaseReference.child(currentEvent.getId()).child("peopleNumber").setValue(currentEvent.getPeopleNumber());
                }
            }
        });

        return view;
    }
}
