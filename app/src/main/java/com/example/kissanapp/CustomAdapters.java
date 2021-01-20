package com.example.kissanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapters extends BaseAdapter {
    Context context;
    int logos[];
    LayoutInflater inflter;
    String detail[];
    public CustomAdapters(Context applicationContext, int[] logos, String[] detail) {
        this.context = applicationContext;
        this.logos = logos;
        this.detail = detail;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.marketthings, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.skillImages); // get the reference of ImageView
        TextView name = (TextView) view.findViewById(R.id.txtPName);
        name.setText(detail[position]);
        icon.setImageResource(logos[position]); // set logo images
        return view;

    }

//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        view = inflter.inflate(R.layout.skillsselection, null); // inflate the layout
//        ImageView icon = (ImageView) view.findViewById(R.id.skillImages); // get the reference of ImageView
//        icon.setImageResource(logos[position]); // set logo images
//        return view;
//    }
}
