package com.example.propertymanagementapplocal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Documents extends Fragment {
    private long refFlatId;
    private long refTenantId;


    public Documents() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_Documents: ==> ", String.valueOf(refFlatId));

        refTenantId = getArguments().getLong("2");
        Log.e("TenantId in Documents ===> ", String.valueOf(refTenantId));
        return view;
    }
}