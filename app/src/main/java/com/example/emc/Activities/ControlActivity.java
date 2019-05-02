package com.example.emc.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.emc.EduControlActivity;
import com.example.emc.EventControlActivity;
import com.example.emc.GalleryControlActivity;
import com.example.emc.R;

public class ControlActivity extends AppCompatActivity {

    private CardView controlGalleryCardView;
    private CardView controlEventsCardView;
    private CardView controlEduCardView;
    private CardView settingCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controlGalleryCardView = findViewById(R.id.cv_add_object);
        controlEventsCardView = findViewById(R.id.cv_make_event);
        controlEduCardView = findViewById(R.id.cv_control_edu);
        settingCardView = findViewById(R.id.cv_settings);

        controlEventsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlActivity.this, EventControlActivity.class);
                startActivity(intent);
            }
        });

        controlGalleryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlActivity.this, GalleryControlActivity.class);
                startActivity(intent);
            }
        });

        controlEduCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlActivity.this, EduControlActivity.class);
                startActivity(intent);
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
