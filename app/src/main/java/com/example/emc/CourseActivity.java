package com.example.emc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emc.Activities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.acl.Permission;

public class CourseActivity extends AppCompatActivity {

    private TextView courseNameTextView, courseCostTextView, courseRequirements, applyTextView;
    private Uri pdfUri;
    private ProgressDialog progressDialog;
    private TextView notification;
    private Visitor visitor;

    //Declaring Firebase Vars
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        courseNameTextView = findViewById(R.id.tv_course_name_c);
        courseCostTextView = findViewById(R.id.tv_course_cost_c);
        courseRequirements = findViewById(R.id.tv_course_req);
        applyTextView = findViewById(R.id.tv_apply);

        visitor = new Visitor();

        //Initializing Firebase vars
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Visitors");
        storage = FirebaseStorage.getInstance();

        courseNameTextView.setText(getIntent().getStringExtra("name"));
        courseCostTextView.setText(getIntent().getStringExtra("cost")+"$");
        courseRequirements.setText(getIntent().getStringExtra("requirements"));


        applyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);

                LinearLayout rootLayout = new LinearLayout(getApplicationContext());
                rootLayout.setOrientation(LinearLayout.VERTICAL);

                final EditText fName = new EditText(getApplicationContext());
                fName.setHint("First Name");
                rootLayout.addView(fName);

                final EditText lName = new EditText(getApplicationContext());
                lName.setHint("Last Name");
                rootLayout.addView(lName);

                final EditText emailAddress = new EditText(getApplicationContext());
                emailAddress.setHint("E-mail Address");
                rootLayout.addView(emailAddress);

                LinearLayout childLayout = new LinearLayout(getApplicationContext());
                childLayout.setOrientation(LinearLayout.HORIZONTAL);

                final Button upload = new Button(getApplicationContext());
                upload.setText("Select");
                childLayout.addView(upload);

                notification = new TextView(getApplicationContext());
                notification.setText("Select CV File");
                childLayout.addView(notification);

                rootLayout.addView(childLayout);




                builder.setTitle("Applying");
                builder.setView(rootLayout);

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(CourseActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            browseDocuments();
                        }else {
                            ActivityCompat.requestPermissions(CourseActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                        }
                    }
                });

                builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pdfUri != null){

                            visitor.setFirstName(fName.getText().toString());
                            visitor.setLastName(lName.getText().toString());
                            visitor.setEmailAddress(emailAddress.getText().toString());
                            databaseReference.push().setValue(visitor);

                            uploadPDF(pdfUri);
                        }else {
                            Toast.makeText(getApplicationContext(), "Please select file", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            browseDocuments();
        }else{
            Toast.makeText(getApplicationContext(), "Please provide permission...", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadPDF(Uri pdfUri){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference();
        storageReference.child("Uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Successfully Applied For This Course", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "File not uploaded!", Toast.LENGTH_SHORT).show();
            }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
                });

    }

    private void browseDocuments(){

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0 && data!=null){
            pdfUri = data.getData();
            notification.setText(pdfUri.toString());
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Select File!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
