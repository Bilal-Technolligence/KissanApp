package com.example.kissanapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class MyAdFragment extends Fragment {

    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_myad, container, false);

//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading..... ");
//        progressDialog.show();

        return v;
    }
}