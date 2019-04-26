package com.example.emc.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.emc.EventControlActivity;
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
    }
}
