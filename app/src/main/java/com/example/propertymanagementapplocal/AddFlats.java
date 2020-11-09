package com.example.propertymanagementapplocal;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

public class AddFlats extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static long propertyId;
    private static FlatsCreateListener flatsCreateListener;


    EditText edtfloor, edtFlatNo;
    Spinner flatfacingSpinner, noofbedroomsSpinner;
    Button flatsave, flatCancel;

    String flatfacing, noofbedrooms;

    public String strflatNo;

    //default constructor
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

        edtfloor = view.findViewById(R.id.floorEdttxt);
        edtFlatNo = view.findViewById(R.id.flatNoEdttxt);
        flatfacingSpinner = view.findViewById(R.id.flatfacingSpin);
        noofbedroomsSpinner = view.findViewById(R.id.noofBedroomsSpin);
        flatsave = view.findViewById(R.id.flatsaveBtn);
        flatCancel = view.findViewById(R.id.flatCancelButton);

        //spinner part

        ArrayAdapter<CharSequence> flatfacingAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Flat_Facing, android.R.layout.simple_spinner_item);
        flatfacingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flatfacingSpinner.setAdapter(flatfacingAdapter);
        flatfacingSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> noofbedroomsAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Number_of_Bedrooms, android.R.layout.simple_spinner_item);
        noofbedroomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noofbedroomsSpinner.setAdapter(noofbedroomsAdapter);
        noofbedroomsSpinner.setOnItemSelectedListener(this);

        flatsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        flatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;


    }

    private void getData() {

        String floor = edtfloor.getText().toString();
        String flatNo = edtFlatNo.getText().toString();

        FlatsModelClass flats = new FlatsModelClass(-1, floor, flatNo, flatfacing, noofbedrooms);

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

        long id = databaseQueryClass.insertFlats(flats, propertyId);

        if (id > 0) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == flatfacingSpinner) {
            flatfacing = "" + adapterView.getItemAtPosition(i).toString();
        } else if (adapterView == noofbedroomsSpinner) {
            noofbedrooms = "" + adapterView.getItemAtPosition(i).toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}




