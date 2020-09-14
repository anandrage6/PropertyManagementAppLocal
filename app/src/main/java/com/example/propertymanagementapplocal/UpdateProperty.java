package com.example.propertymanagementapplocal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateProperty extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static long propertyId;
    private static int propertyItemPosition;
    private static PropertyUpdateListener propertyUpdateListener;
    private PropertyModelClass mPropertyModelClass;
    private EditText edtPropertyName, edtOwnerName, edtAddress, edtCity, edtZipCode, edtDescription;
    private Spinner stateSpin, propertyTypeSpin;
    private ImageButton imageButton;

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

        //updateProperty.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return updateProperty;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.update_property, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        edtPropertyName = view.findViewById(R.id.updatePropertyNameEditText);
        edtOwnerName = view.findViewById(R.id.updateOwnerNameEditText);
        edtAddress = view.findViewById(R.id.updateAddressEditText);
        edtCity = view.findViewById(R.id.updateCityEditText);
        edtZipCode = view.findViewById(R.id.updateZipCodeEditText);
        edtDescription = view.findViewById(R.id.updateDescriptionEditText);
        updatebutton = view.findViewById(R.id.updateSaveBtn);
        stateSpin = view.findViewById(R.id.updateStateSpin);
        propertyTypeSpin = view.findViewById(R.id.updatePropertyTypeSpin);
        imageButton = view.findViewById(R.id.updateImageButton);

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
        stateAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cstates);
        stateSpin.setAdapter(stateAdapter);
        stateAdapter.addAll(allStates);
        stateSpin.setOnItemSelectedListener(this);

        //propertyType Spinner
        String cPropertyType = mPropertyModelClass.getPropertyType();
        cPropertyTypes = new ArrayList<>();
        cPropertyTypes.add(cPropertyType);
        List<String> allPropertyTypes = Arrays.asList(getResources().getStringArray(R.array.Property_Types));
        propertyTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cPropertyTypes);
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
                    getDialog().dismiss();
                }

            }
        });

        return view;

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

