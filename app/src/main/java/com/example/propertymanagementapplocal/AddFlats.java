package com.example.propertymanagementapplocal;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class AddFlats extends DialogFragment {

    private static long propertyId;
    private static FlatsCreateListener flatsCreateListener;


    EditText edtFlatNo;
    Button flatsave;

    public String strflatNo;

    public AddFlats() {
    }

    public static AddFlats newInstance(long id, FlatsCreateListener listener) {

        propertyId = id;
        flatsCreateListener = listener;
        AddFlats addFlats = new AddFlats();
        addFlats.setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialogTheme);
        return addFlats;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_flats, container, false);
        getDialog().setTitle(getResources().getString(R.string.add_new_Flat));

        /*
        edtFlatNo = view.findViewById(R.id.flatNoEditText);
        flatsave = view.findViewById(R.id.flatSavebtn);

        flatsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

         */

        return view;



    }

    private void getData() {

        String flatNo = edtFlatNo.getText().toString();

        FlatsModelClass flats = new FlatsModelClass(-1, flatNo );

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

        long id = databaseQueryClass.insertFlats(flats, propertyId);

        if(id>0){
            flats.setFlatId(id);
            flatsCreateListener.onFlatsCreated(flats);
            getDialog().dismiss();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }

    }
}


