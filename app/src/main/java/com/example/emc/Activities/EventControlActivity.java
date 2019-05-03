package com.example.emc.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.emc.Adapters.EventAdapter;
import com.example.emc.Adapters.ObjectControlAdapter;
import com.example.emc.Event;
import com.example.emc.GObject;
import com.example.emc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class EventControlActivity extends AppCompatActivity {

    private ListView listView;
    private Event event = new Event();
    private EventAdapter adapter;
    private ArrayList<Event> events;

    //Declaring Firebase Vars
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.lv_events);

        //Initializing Firebase vars
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Events");

        events = new ArrayList<>();
        adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event event = dataSnapshot.getValue(Event.class);
                adapter.add(event);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        else if (id == R.id.add_event){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Make an event");

            ScrollView scrollView = new ScrollView(this);
            scrollView.setClickable(true);
            scrollView.setFocusableInTouchMode(true);

            LinearLayout rootLinearLayout = new LinearLayout(this);
            rootLinearLayout.setOrientation(LinearLayout.VERTICAL);
            rootLinearLayout.setClickable(true);
            rootLinearLayout.setFocusableInTouchMode(true);

            final EditText eventName = new EditText(this);
            eventName.setHint("Enter the name of event");
            eventName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            rootLinearLayout.addView(eventName);
            eventName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        hideKeyboard(v);
                    }
                }
            });

            CalendarView calendarView = new CalendarView(this);
            rootLinearLayout.addView(calendarView);
            calendarView.setClickable(true);
            calendarView.setFocusableInTouchMode(true);

            TimePicker timePicker = new TimePicker(this);
            rootLinearLayout.addView(timePicker);
            timePicker.setFocusableInTouchMode(true);
            timePicker.setClickable(true);

            final EditText eventDescription = new EditText(this);
            eventDescription.setHint("Description");
            eventDescription.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            eventDescription.setMaxLines(10);
            eventDescription.setLines(5);
            rootLinearLayout.addView(eventDescription);
            eventDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        hideKeyboard(v);
                    }
                }
            });


            scrollView.addView(rootLinearLayout);
            builder.setView(scrollView);

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    event.setDayNumber(dayOfMonth+"");
                    event.setMonth(months[month]);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    event.setDayName(days[dayOfWeek-1]);
                }
            });

            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    event.setTime(hourOfDay+":"+minute);
                }
            });


            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    event.setName(eventName.getText().toString());
                    event.setPeopleNumber(0);
                    event.setDescription(eventDescription.getText().toString());
                    event.setId(databaseReference.push().getKey());
                    databaseReference.child(event.getId()).setValue(event);
                    Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_event, menu);

        MenuItem searchItem = menu.findItem(R.id.event_search);
        EditText searchView = (EditText) searchItem.getActionView();
        searchView.setHint("Search For An Event");

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textlength = s.length();
                ArrayList<Event> tempArrayList = new ArrayList<>();
                for(Event c: events){
                    if (textlength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }
                }
                EventAdapter mAdapter = new EventAdapter(getApplicationContext(), tempArrayList);
                listView.setAdapter(mAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return true;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
