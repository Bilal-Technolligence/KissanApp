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

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<CartAttr> cartAttrs;
    private Context context;
    Activity activity;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public CartListAdapter(ArrayList<CartAttr> cartAttrs, Context context , Activity activity) {
        this.context = context;
        this.cartAttrs = cartAttrs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(cartAttrs.get(position).getImage()).into(holder.img);
        holder.title.setText(cartAttrs.get(position).getTitle());
        holder.price.setText("RS " +cartAttrs.get(position).getPrice());
        holder.description.setText(cartAttrs.get(position).getDescription());
        holder.date.setText(cartAttrs.get(position).getDate());
        holder.city.setText(cartAttrs.get(position).getCity());
        holder.status.setText(cartAttrs.get(position).getStatus());

        String id = cartAttrs.get(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity , OrderDetail.class);
                i.putExtra("id" , id);
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img ;
        TextView title , price ,description ,city , date ,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            city = (TextView) itemView.findViewById(R.id.city);
            date = (TextView) itemView.findViewById(R.id.date);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
