package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
/*
public class AddFlat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static long propertyId;
    private static FlatsCreateListener flatsCreateListener;


    EditText edtfloor, edtFlatNo ;
    Spinner flatfacingSpinner, noofbedroomsSpinner;
    Button flatsave;

    String flatfacing, noofbedrooms;

    public String strflatNo;

    public AddFlat() {
    }

    public static AddFlat newInstance(long id, FlatsCreateListener listener) {

        propertyId = id;
        flatsCreateListener = listener;
        AddFlat addFlats = new AddFlat();
        //addFlats.setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialogTheme);
        return addFlats;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flat);

        edtfloor = findViewById(R.id.floorEdttxt);
        edtFlatNo = findViewById(R.id.flatNoEdttxt);
        flatfacingSpinner = findViewById(R.id.flatfacingSpin);
        noofbedroomsSpinner = findViewById(R.id.noofBedroomsSpin);
        flatsave = findViewById(R.id.flatsave);

        //spinner part

        ArrayAdapter<CharSequence> flatfacingAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Flat_Facing, android.R.layout.simple_spinner_item);
        flatfacingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flatfacingSpinner.setAdapter(flatfacingAdapter);
        flatfacingSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> noofbedroomsAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Number_of_Bathrooms, android.R.layout.simple_spinner_item);
        noofbedroomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noofbedroomsSpinner.setAdapter(noofbedroomsAdapter);
        noofbedroomsSpinner.setOnItemSelectedListener(this);

        flatsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == flatfacingSpinner){
            flatfacing = ""+adapterView.getItemAtPosition(i).toString();
        }else if(adapterView == noofbedroomsSpinner){
            noofbedrooms = ""+adapterView.getItemAtPosition(i).toString();

        }

    }
    private void getData() {

        String floor = edtfloor.getText().toString();
        String flatNo = edtFlatNo.getText().toString();

        FlatsModelClass flats = new FlatsModelClass(-1, floor, flatNo, flatfacing, noofbedrooms );

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        long id = databaseQueryClass.insertFlats(flats, propertyId);

        if(id>0){
            flats.setFlatId(id);
            flatsCreateListener.onFlatsCreated(flats);
            //getDialog().dismiss();
            Intent i = new Intent(getApplicationContext(), );
            startActivity(i);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

 */