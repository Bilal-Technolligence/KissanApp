package com.example.kissanapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<com.example.kissanapp.NotificationAdapter.MyViewHolder> {
    ArrayList<notificationAttr> notificationAttrs;
    Activity context;

    public NotificationAdapter(ArrayList<notificationAttr> notificationAttrs, Activity context) {
        this.context = context;
        this.notificationAttrs = notificationAttrs;
    }

    @NonNull
    @Override
    public com.example.kissanapp.NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifitem, parent, false);
        return new com.example.kissanapp.NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.kissanapp.NotificationAdapter.MyViewHolder holder, final int position) {
        holder.desc.setText(notificationAttrs.get(position).getDescription());
        holder.title.setText(notificationAttrs.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return notificationAttrs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desc,title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            desc = (TextView) itemView.findViewById(R.id.notification_description);
        }
    }

}

