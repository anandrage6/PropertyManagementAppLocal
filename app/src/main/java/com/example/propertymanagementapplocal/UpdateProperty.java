package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateProperty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static long propertyId;
    private static int propertyItemPosition;
    private static PropertyUpdateListener propertyUpdateListener;
    private PropertyModelClass mPropertyModelClass;
    private EditText edtPropertyName, edtOwnerName, edtAddress, edtCity, edtZipCode, edtDescription;
    private Spinner stateSpin, propertyTypeSpin;
    private ImageButton imageButton;
    private Toolbar toolbar;

    private Button updatebutton;

    private String propertyName = "", ownerName = "", address = "", city = "", zipcode = "", description = "", state = "", propertyType = "", image = "";

    ArrayList<String> cstates;
    ArrayAdapter<String> stateAdapter;

    ArrayList<String> cPropertyTypes;
    ArrayAdapter<String> propertyTypeAdapter;

    private DatabaseQueryClass databaseQueryClass;

    public UpdateProperty() {
        //required empty constructor
    }

    public static UpdateProperty newInstance(long id, int position, PropertyUpdateListener listener) {

        propertyId = id;
        propertyItemPosition = position;
        propertyUpdateListener = listener;

        UpdateProperty updateProperty = new UpdateProperty();
        //Bundle args = new Bundle();
        //args.putString("title", "Update Property information");
        //updateProperty.setArguments(args);

        //updateProperty.setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialogTheme);

        return updateProperty;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_property);

        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Property Management");
        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        edtPropertyName = findViewById(R.id.updatePropertyNameEditText);
        edtOwnerName = findViewById(R.id.updateOwnerNameEditText);
        edtAddress = findViewById(R.id.updateAddressEditText);
        edtCity = findViewById(R.id.updateCityEditText);
        edtZipCode = findViewById(R.id.updateZipCodeEditText);
        edtDescription = findViewById(R.id.updateDescriptionEditText);
        updatebutton = findViewById(R.id.updateSaveBtn);
        stateSpin = findViewById(R.id.updateStateSpin);
        propertyTypeSpin = findViewById(R.id.updatePropertyTypeSpin);
        imageButton = findViewById(R.id.updateImageButton);

        //String title = getArguments().getString(Config.TITLE);
        //getDialog().setTitle(title);

        mPropertyModelClass = databaseQueryClass.getPropertyById(propertyId);

        edtPropertyName.setText(mPropertyModelClass.getPropertyName());
        edtOwnerName.setText(mPropertyModelClass.getOwnerName());
        edtAddress.setText(mPropertyModelClass.getAddress());
        edtCity.setText(mPropertyModelClass.getCity());
        edtZipCode.setText(mPropertyModelClass.getZipCode());
        edtDescription.setText(mPropertyModelClass.getDescription());

        //image update get and set
        final String imageuri = mPropertyModelClass.getImage();
        imageButton.setImageURI(Uri.parse(imageuri));

        //state Spinner
        String cState = mPropertyModelClass.getState();
        cstates = new ArrayList<String>();
        cstates.add(cState);
        List<String> allStates =  Arrays.asList(getResources().getStringArray(R.array.States));
        stateAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cstates);
        stateSpin.setAdapter(stateAdapter);
        stateAdapter.addAll(allStates);
        stateSpin.setOnItemSelectedListener(this);

        //propertyType Spinner
        String cPropertyType = mPropertyModelClass.getPropertyType();
        cPropertyTypes = new ArrayList<>();
        cPropertyTypes.add(cPropertyType);
        List<String> allPropertyTypes = Arrays.asList(getResources().getStringArray(R.array.Property_Types));
        propertyTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cPropertyTypes);
        propertyTypeSpin.setAdapter(propertyTypeAdapter);
        propertyTypeAdapter.addAll(allPropertyTypes);
        propertyTypeSpin.setOnItemSelectedListener(this);



        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyName = edtPropertyName.getText().toString();
                ownerName = edtOwnerName.getText().toString();
                address = edtAddress.getText().toString();
                city = edtCity.getText().toString();
                zipcode = edtZipCode.getText().toString();
                description = edtDescription.getText().toString();
                image = imageuri.toString();

                mPropertyModelClass.setPropertyName(propertyName);
                mPropertyModelClass.setOwnerName(ownerName);
                mPropertyModelClass.setAddress(address);
                mPropertyModelClass.setCity(city);
                mPropertyModelClass.setZipCode(zipcode);
                mPropertyModelClass.setDescription(description);
                mPropertyModelClass.setState(state);
                mPropertyModelClass.setImage(image);

                long id = databaseQueryClass.updatePropertyInfo(mPropertyModelClass);
                if(id>0){
                    propertyUpdateListener.onPropertyInfoUpdated(mPropertyModelClass, propertyItemPosition);
                    //getDialog().dismiss();
                    Intent i = new Intent(UpdateProperty.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });

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