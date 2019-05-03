package com.example.emc.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emc.Adapters.ObjectControlAdapter;
import com.example.emc.Adapters.ObjectsAdapter;
import com.example.emc.GObject;
import com.example.emc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GalleryControlActivity extends AppCompatActivity {

    //Constant
    private final static String URL_QUERY = "https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=original&titles=";

    //Components Vars
    private ListView listView;
    private ProgressBar progressBar;

    //Declaring Firebase Vars
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //Vars
    private GObject object = new GObject();
    private ObjectControlAdapter adapter;
    ArrayList<GObject> objects;

    private String objectName = "", birthDie = "", details = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.rv_control_objects);
        progressBar = findViewById(R.id.pb_loading);

        progressBar.setVisibility(View.INVISIBLE);

        //Initializing Firebase vars
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Gallery");


        objects = new ArrayList<>();
        adapter = new ObjectControlAdapter(this, objects);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GObject gObject = dataSnapshot.getValue(GObject.class);
                adapter.add(gObject);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listView.setAdapter(adapter);
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

        else if (id == R.id.add_object){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add New Object");

            LinearLayout rootLayout = new LinearLayout(this);
            rootLayout.setOrientation(LinearLayout.VERTICAL);
            rootLayout.setFocusableInTouchMode(true);
            rootLayout.setClickable(true);

            final EditText objectNameE = new EditText(this);
            objectNameE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            objectNameE.setHint("Object Name");
            rootLayout.addView(objectNameE);
            objectNameE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        hideKeyboard(v);
                    }
                }
            });

            final EditText birthDieE = new EditText(this);
            birthDieE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            birthDieE.setHint("Birth - Die");
            rootLayout.addView(birthDieE);
            birthDieE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        hideKeyboard(v);
                    }
                }
            });

            final EditText detailsE = new EditText(this);
            detailsE.setHint("Details");
            detailsE.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            detailsE.setMaxLines(10);
            detailsE.setLines(5);
            rootLayout.addView(detailsE);
            detailsE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        hideKeyboard(v);
                    }
                }
            });

            builder.setView(rootLayout);

            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    objectName = objectNameE.getText().toString();
                    birthDie = birthDieE.getText().toString();
                    details = detailsE.getText().toString();

                    new MyTask().execute(URL_QUERY+objectNameE.getText().toString());


                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_object, menu);

        MenuItem searchItem = menu.findItem(R.id.object_search);
        EditText searchView = (EditText) searchItem.getActionView();
        searchView.setHint("Search For An Object");

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textlength = s.length();
                ArrayList<GObject> tempArrayList = new ArrayList<>();
                for(GObject c: objects){
                    if (textlength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }
                }
                ObjectControlAdapter mAdapter = new ObjectControlAdapter(getApplicationContext(), tempArrayList);
                listView.setAdapter(mAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return true;
    }

    public class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String text;
            String res = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(12000);
                urlConnection.setReadTimeout(12000);
                urlConnection.connect();

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                text = stream2String(inputStream);

                res = extractFromJson(text);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            object.setImgId(s);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            object.setName(objectName);
            object.setBornDie(birthDie);
            object.setDetails(details);
            object.setId(databaseReference.push().getKey());
            databaseReference.child(object.getId()).setValue(object);
        }
    }

    public String stream2String(InputStream inputStream){

        String line;
        String text = "";

        BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream));

        try{
            while((line = reader.readLine()) != null){
                text += line;
            }
        }catch (IOException e){}

        return text;
    }

    public String extractFromJson(String json){
        String url = "";
        try {

            JSONObject root = new JSONObject(json);
            JSONObject query = root.getJSONObject("query");
            JSONObject pages = query.getJSONObject("pages");
            Iterator<String> key = pages.keys();
            String firstPageId= key.next();
            JSONObject firstPage = pages.getJSONObject(firstPageId);
            JSONObject original = firstPage.getJSONObject("original");
            url = original.getString("source");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
