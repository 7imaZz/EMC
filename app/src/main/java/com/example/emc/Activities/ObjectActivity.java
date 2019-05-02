package com.example.emc.Activities;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.emc.R;
import com.squareup.picasso.Picasso;

public class ObjectActivity extends AppCompatActivity {

    private ImageView objectImageView;
    private TextView titleTextView, dateTextView, descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        objectImageView = findViewById(R.id.img_obj);
        titleTextView = findViewById(R.id.tv_obj_title);
        dateTextView = findViewById(R.id.tv_obj_date);
        descriptionTextView = findViewById(R.id.tv_obj_description);

        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("date");
        String desc = getIntent().getStringExtra("description");
        String pic = getIntent().getStringExtra("pic");

        titleTextView.setText(name);
        dateTextView.setText(date);
        descriptionTextView.setText(desc);

        Uri uri = Uri.parse(pic);
        Picasso.get().load(uri).into(objectImageView);
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
