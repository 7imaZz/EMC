package com.example.emc.Adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.emc.Course;
import com.example.emc.R;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course>{
    public CourseAdapter(Context context, ArrayList<Course> courses) {
        super(context, 0, courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.course_item, parent, false);
        }

        Course currentCourse = getItem(position);

        TextView courseName = view.findViewById(R.id.tv_course_name);
        courseName.setText(currentCourse.getName());

        TextView courseDeadline= view.findViewById(R.id.tv_deadline);
        courseDeadline.setText("Applying will end in "+currentCourse.getDate());

        TextView courseCost = view.findViewById(R.id.tv_course_cost);
        courseCost.setText("Cost: "+currentCourse.getCost()+"$");

        return view;
    }
}
