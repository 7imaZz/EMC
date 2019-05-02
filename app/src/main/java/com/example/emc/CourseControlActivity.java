package com.example.emc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourseControlActivity extends AppCompatActivity {
    private EditText courseNameEditText, requirementsEditText;
    private CalendarView calendarView;
    private TextView doneButton;

    //Declaring Firebase Vars
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseNameEditText = findViewById(R.id.ed_course_name);
        requirementsEditText = findViewById(R.id.ed_requirements);
        calendarView = findViewById(R.id.cal_deadline_date);
        doneButton = findViewById(R.id.tv_done);

        final Course course = new Course();

        //Initializing Firebase vars
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Courses");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                course.setDate(dayOfMonth+"/"+month+"/"+year);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.setName(courseNameEditText.getText().toString());
                course.setRequirements(requirementsEditText.getText().toString());
                course.setId(databaseReference.push().getKey());
                databaseReference.child(course.getId()).setValue(course);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
