package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateProperty2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edtPropertyName, edtOwnerName, edtAddress, edtCity, edtZipCode, edtDescription;
    private Spinner stateSpin, propertyTypeSpin;
    private ImageButton imageButton;

    private Button updateSavebutton, updateCancelButton;

    private long propertyId;
    private String propertyName = "", ownerName = "", address = "", city = "", zipcode = "", description = "", state = "", propertyType = "", image = "";

    ArrayList<String> cstates;
    ArrayAdapter<String> stateAdapter;

    ArrayList<String> cPropertyTypes;
    ArrayAdapter<String> propertyTypeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_property2);

        edtPropertyName = findViewById(R.id.updateProperty2NameEditText);
        edtOwnerName = findViewById(R.id.updateProperty2OwnerNameEditText);
        edtAddress = findViewById(R.id.updateProperty2AddressEditText);
        edtCity = findViewById(R.id.updateProperty2CityEditText);
        edtZipCode = findViewById(R.id.updateProperty2ZipCodeEditText);
        edtDescription = findViewById(R.id.updateProperty2DescriptionEditText);
        updateSavebutton = findViewById(R.id.updateProperty2SaveBtn);
        updateCancelButton = findViewById(R.id.updateroperty2CancelButton);
        stateSpin = findViewById(R.id.updateProperty2StateSpin);
        propertyTypeSpin = findViewById(R.id.updateProperty2TypeSpin);
        imageButton = findViewById(R.id.updateProperty2ImageButton);


        //getting values
        propertyId = getIntent().getLongExtra("PROPERTYID", -1);
        propertyType = getIntent().getStringExtra("PROPERTYTYPE");
        propertyName = getIntent().getStringExtra("PROPERTYNAME");
        ownerName = getIntent().getStringExtra("OWNERNAME");
        address = getIntent().getStringExtra("ADDRESS");
        city = getIntent().getStringExtra("CITY");
        state = getIntent().getStringExtra("STATE");
        zipcode = getIntent().getStringExtra("ZIPCODE");
        description = getIntent().getStringExtra("DESCRIPTION");
        image = getIntent().getStringExtra("IMAGE");

        //set values

        edtPropertyName.setText(propertyName);
        edtOwnerName.setText(ownerName);
        edtAddress.setText(address);
        edtCity.setText(city);
        edtZipCode.setText(zipcode);
        edtDescription.setText(description);


        //image
        final String imageuri = image;
        imageButton.setImageURI(Uri.parse(imageuri));

        //state Spinner
        String cState = state;
        cstates = new ArrayList<String>();
        cstates.add(cState);
        List<String> allStates =  Arrays.asList(getResources().getStringArray(R.array.States));
        stateAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cstates);
        stateSpin.setAdapter(stateAdapter);
        stateAdapter.addAll(allStates);
        stateSpin.setOnItemSelectedListener(this);

        //propertyType Spinner
        String cPropertyType = propertyType;
        cPropertyTypes = new ArrayList<>();
        cPropertyTypes.add(cPropertyType);
        List<String> allPropertyTypes = Arrays.asList(getResources().getStringArray(R.array.Property_Types));
        propertyTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cPropertyTypes);
        propertyTypeSpin.setAdapter(propertyTypeAdapter);
        propertyTypeAdapter.addAll(allPropertyTypes);
        propertyTypeSpin.setOnItemSelectedListener(this);








    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView == stateSpin) {
            state = adapterView.getItemAtPosition(i).toString();
        }else if(adapterView == propertyTypeSpin){
            propertyType =  adapterView.getItemAtPosition(i).toString();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}