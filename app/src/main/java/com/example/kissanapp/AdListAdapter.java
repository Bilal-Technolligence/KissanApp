package com.example.kissanapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdListAdapter extends RecyclerView.Adapter<AdListAdapter.ViewHolder> {
    ArrayList<AdAttr> adAttrs;
    private Context context;
    Activity activity;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public AdListAdapter(ArrayList<AdAttr> adAttrs, Context context ,Activity activity) {
        this.context = context;
        this.adAttrs = adAttrs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(adAttrs.get(position).getImage()).into(holder.img);
        holder.title.setText(adAttrs.get(position).getTitle());
        holder.price.setText("RS " +adAttrs.get(position).getPrice());
        holder.description.setText(adAttrs.get(position).getDescription());
        holder.date.setText(adAttrs.get(position).getDate());
        holder.city.setText(adAttrs.get(position).getCity());
        String userId = adAttrs.get(position).getUserId();

        String id = adAttrs.get(position).getId();

        if(!userId.equals(uid)){
            databaseReference.child("Like").child(uid).child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        holder.like.setImageResource(R.drawable.fillheart);
                    } else {
                        holder.like.setImageResource(R.drawable.emptyheart);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userId.equals(uid)){
                    Intent i = new Intent(activity , AdDetail.class);
                    i.putExtra("id" , id);
                    activity.startActivity(i);
                }
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId.equals(uid)) {
                    new AlertDialog.Builder(activity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child("Ads").child(id).setValue(null);
                                    activity.startActivity(new Intent(activity , SellingActivity.class));
                                    activity.finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                else{
                    holder.like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseReference.child("Like").child(uid).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        databaseReference.child("Like").child(uid).child(id).setValue(null);
                                        holder.like.setImageResource(R.drawable.emptyheart);
                                    } else {
                                        databaseReference.child("Like").child(uid).child(id).child("id").setValue(id);
                                        holder.like.setImageResource(R.drawable.fillheart);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return adAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img , like;
        TextView title , price ,description ,city , date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            city = (TextView) itemView.findViewById(R.id.city);
            date = (TextView) itemView.findViewById(R.id.date);
            like = (ImageView) itemView.findViewById(R.id.love);
        }
    }
}
