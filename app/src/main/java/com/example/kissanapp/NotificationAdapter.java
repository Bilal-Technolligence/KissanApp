package com.example.kissanapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<com.example.kissanapp.NotificationAdapter.MyViewHolder> {
    ArrayList<notificationAttr> notificationAttrs;
    Activity context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

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
        String orderId = notificationAttrs.get(position).getOrderId();
        String id = notificationAttrs.get(position).getId();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to delete notification?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("Notification").child(id).setValue(null);
                                context.startActivity(new Intent(context , NotificationsActivity.class));
                                context.finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , OrderDetail.class);
                i.putExtra("id" , orderId);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationAttrs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desc,title;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            desc = (TextView) itemView.findViewById(R.id.notification_description);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }

}

