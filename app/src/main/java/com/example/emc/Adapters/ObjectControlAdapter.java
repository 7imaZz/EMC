package com.example.emc.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emc.GObject;
import com.example.emc.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ObjectControlAdapter extends ArrayAdapter<GObject> {
    public ObjectControlAdapter(Context context, ArrayList<GObject> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.object_item, parent, false);
        }

        GObject currentObject = getItem(position);

        ImageView imageView = view.findViewById(R.id.img_gal_object);
        String s = currentObject.getImgId();
        Uri uri = Uri.parse(s);
        Picasso.get().load(uri).into(imageView);

        TextView nameTextView = view.findViewById(R.id.tv_gal_obj_name);
        nameTextView.setText(currentObject.getName());

        TextView bornDieTextView = view.findViewById(R.id.tv_gal_obj_born);
        bornDieTextView.setText(currentObject.getBornDie());

        return view;
    }
}
