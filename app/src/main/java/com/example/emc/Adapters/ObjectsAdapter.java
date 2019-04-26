package com.example.emc.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emc.GObject;
import com.example.emc.ItemClickListener;
import com.example.emc.Activities.ObjectActivity;
import com.example.emc.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ObjectsAdapter extends RecyclerView.Adapter<ObjectsAdapter.ObjectHolder>{

    class ObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemClickListener itemClickListener;

        private ImageView imageView;
        private TextView nameTextView;
        private TextView bornDieTextView;

        public ObjectHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_gal_object);
            nameTextView = itemView.findViewById(R.id.tv_gal_obj_name);
            bornDieTextView = itemView.findViewById(R.id.tv_gal_obj_born);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }


    private Context context;
    private ArrayList<GObject> objects;


    public ObjectsAdapter(Context context, ArrayList<GObject> objects) {
        this.context = context;
        this.objects = objects;
    }

    public ObjectsAdapter.ObjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.object_item, viewGroup, false);
        return new ObjectsAdapter.ObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectHolder objectHolder, int i) {

        final GObject currentObject = objects.get(i);

        objectHolder.nameTextView.setText(currentObject.getName());
        objectHolder.bornDieTextView.setText(currentObject.getBornDie());

        final Uri uri = currentObject.getImgId();
        Picasso.get().load(uri).into(objectHolder.imageView);

        objectHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ObjectActivity.class);

                intent.putExtra("name", currentObject.getName());
                intent.putExtra("date", currentObject.getBornDie());
                intent.putExtra("description", currentObject.getDetails());
                intent.putExtra("pic", currentObject.getImgId().toString());

                context.startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return objects.size();
    }

}
