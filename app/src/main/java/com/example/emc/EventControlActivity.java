package com.example.emc;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.util.Clock;
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

    //Declaring Firebase Vars
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_control);

        listView = findViewById(R.id.lv_events);

        //Initializing Firebase vars
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Events");

        ArrayList<Event> events = new ArrayList<>();
        final EventAdapter adapter = new EventAdapter(this, events);
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

        if (id == R.id.add_event){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Make an event");

            ScrollView scrollView = new ScrollView(this);

            LinearLayout rootLinearLayout = new LinearLayout(this);
            rootLinearLayout.setOrientation(LinearLayout.VERTICAL);

            final EditText eventName = new EditText(this);
            eventName.setHint("Enter the name of event");
            eventName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            rootLinearLayout.addView(eventName);

            CalendarView calendarView = new CalendarView(this);
            rootLinearLayout.addView(calendarView);

            TimePicker timePicker = new TimePicker(this);
            rootLinearLayout.addView(timePicker);

            final EditText eventDescription = new EditText(this);
            eventDescription.setHint("Description");
            eventDescription.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            eventDescription.setMaxLines(10);
            eventDescription.setLines(5);
            rootLinearLayout.addView(eventDescription);


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

                    databaseReference.push().setValue(event);
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
        return true;
    }
}
