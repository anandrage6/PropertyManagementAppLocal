package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateFlat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static long flatId;

    private static int flatItemPosition;

    private static FlatUpdateListener flatUpdateListener;

    private FlatsModelClass mflatsModelClass;

    private DatabaseQueryClass databaseQueryClass;


    TextView floor, flatNumber;
    Spinner flatFacing, noOfBedrooms;
    Button updateSave, updateCancel;
    String flaFacingStr, noOfBedroomsStr;

    private String strfloor = "", strfaltNumber = "", strFlatFacing = "", strNoOfBedrooms = "";

    ArrayList<String> flatFacingList;
    ArrayAdapter<String> flatFacingAdapter;

    ArrayList<String> noOfBedroomsList;
    ArrayAdapter<String> noOfBedroomsAdapter;


    //empty constructor
    public UpdateFlat() {
    }

    //new instance

    public static UpdateFlat newInstance(long id, int position, FlatUpdateListener listener) {

        flatId = id;
        flatItemPosition = position;
        flatUpdateListener = listener;

        UpdateFlat updateProperty = new UpdateFlat();
        return updateProperty;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flat);

        floor = findViewById(R.id.updateFloorEdttxt);
        flatNumber = findViewById(R.id.updateFlatNoEdttxt);
        flatFacing = findViewById(R.id.updateFlatfacingSpin);
        noOfBedrooms = findViewById(R.id.updateFlatnoofBedroomsSpin);
        updateSave = findViewById(R.id.updateFlatsaveBtn);
        updateCancel = findViewById(R.id.updateFlatCancelButton);


        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        mflatsModelClass = databaseQueryClass.getFlatsById(flatId);

        //getting values
        floor.setText(mflatsModelClass.getFloor());
        flatNumber.setText(mflatsModelClass.getFlaNo());

        //flatfacing
        final String strfaltFacing = mflatsModelClass.getFaltfacing();
        flatFacingList = new ArrayList<String>();
        flatFacingList.add(strfaltFacing);
        List<String> ListFlatFacing =  Arrays.asList(getResources().getStringArray(R.array.Flat_Facing));
        flatFacingAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, flatFacingList);
        flatFacing.setAdapter(flatFacingAdapter);
        flatFacingAdapter.addAll(ListFlatFacing);
        flatFacing.setOnItemSelectedListener(this);

        //no of bedrooms
        String strNoOfBedrooms = mflatsModelClass.getNoofbedrooms();
        noOfBedroomsList = new ArrayList<String>();
        noOfBedroomsList.add(strNoOfBedrooms);
        List<String> ListNoOfBedrooms =  Arrays.asList(getResources().getStringArray(R.array.Number_of_Bedrooms));
        noOfBedroomsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, noOfBedroomsList);
        noOfBedrooms.setAdapter(noOfBedroomsAdapter);
        noOfBedroomsAdapter.addAll(ListNoOfBedrooms);
        noOfBedrooms.setOnItemSelectedListener(this);

        //savebutton(updateButton)
        updateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strfloor = floor.getText().toString();
                strfaltNumber = flatNumber.getText().toString();

                //setting values
                mflatsModelClass.setFloor(strfloor);
                mflatsModelClass.setFlaNo(strfaltNumber);
                mflatsModelClass.setFaltfacing(flaFacingStr);
                mflatsModelClass.setNoofbedrooms(noOfBedroomsStr);

                long id = databaseQueryClass.updateFlatInfo(mflatsModelClass);
                if(id>0){
                    flatUpdateListener.onFlatInfoUpdated(mflatsModelClass, flatItemPosition);
                    //getDialog().dismiss();
                    //Intent i = new Intent(UpdateFlat.this, PropertyDetails.class);
                    //startActivity(i);
                    finish();
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == flatFacing) {
            flaFacingStr = adapterView.getItemAtPosition(i).toString();
        }else if(adapterView == noOfBedrooms){
            noOfBedroomsStr =  adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}