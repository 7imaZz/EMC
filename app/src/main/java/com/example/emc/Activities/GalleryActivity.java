package com.example.emc.Activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.emc.Adapters.ObjectsAdapter;
import com.example.emc.GObject;
import com.example.emc.R;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.rv_objects);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ArrayList<GObject> objects = new ArrayList<>();

        Uri uri =  Uri.parse("https://upload.wikimedia.org/wikipedia/commons/0/04/Nefertiti_bust2.jpg");

        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));
        objects.add(new GObject("Nefertiti", "1370 BC - 1330 BC", "Queen Queen Queen Queen Queen Queen Queen Queen ", uri));

        ObjectsAdapter adapter = new ObjectsAdapter(this, objects);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
