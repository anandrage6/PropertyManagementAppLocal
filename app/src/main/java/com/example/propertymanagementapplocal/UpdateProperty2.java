package com.example.propertymanagementapplocal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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

    private  int itemPosition;

    ArrayList<String> cstates;
    ArrayAdapter<String> stateAdapter;
    private Uri imageUri;

    ArrayList<String> cPropertyTypes;
    ArrayAdapter<String> propertyTypeAdapter;

    private DatabaseQueryClass databaseQueryClass;

    // permission constants
    private   static  final  int CAMERA_REQUEST_CODE = 100;
    private   static  final  int STORAGE_REQUEST_CODE = 101;

    //image pick constants
    private   static  final  int IMAGE_PICK_CAMERA_CODE = 102;
    private   static  final  int IMAGE_PICK_GALLERY_CODE = 103;

    //array of permissions
    private  String[] cameraPermissions;
    private  String[] storagePermissions;



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
       // itemPosition = Integer.parseInt(getIntent().getStringExtra("itemPosition"));

        databaseQueryClass = new DatabaseQueryClass(this);

        //set values

        edtPropertyName.setText(propertyName);
        edtOwnerName.setText(ownerName);
        edtAddress.setText(address);
        edtCity.setText(city);
        edtZipCode.setText(zipcode);
        edtDescription.setText(description);


        //image
        imageUri = Uri.parse(image);
        imageButton.setImageURI(imageUri);

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

        //update data(save)
        updateSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //str
                long id = propertyId;
                String strPropertyType = propertyType;
                String strPropertyName = edtPropertyName.getText().toString();
                String strOwnerName = edtOwnerName.getText().toString();
                String strAddress = edtAddress.getText().toString();
                String strCity = edtCity.getText().toString();
                String strState = state;
                String strZipCode = edtZipCode.getText().toString();
                String strDescription = edtDescription.getText().toString();
                String strImage = imageUri.toString();

                Boolean updatedata = databaseQueryClass.updateProperty2(id, strPropertyType, strPropertyName, strOwnerName, strAddress,
                        strCity, strState, strZipCode, strDescription, strImage);

                finish();
            }
        });


        //cancel button
        updateCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(UpdateProperty2.this);
                alertDialogBuilder.setTitle("Confirm Exit...!!");
                alertDialogBuilder.setMessage("Are you sure you want to exit");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UpdateProperty2.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        //image update
        cameraPermissions = new  String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
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

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select for Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if ( i == 0){
                    //if 0 then open the camera
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFormCamera();
                    }
                }
                else if(i == 1){
                    //if 1 then open the camera
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromStorage();
                    }
                }

            }
        });
        builder.create().show();
    }

    private void pickFromStorage() {
        //get image from gallery
        Intent galleryIntent =  new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);

    }

    private void pickFormCamera() {
        //get image from camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);

    }


    private  Boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void  requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }
    public void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                if(grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        pickFormCamera();
                    }else {
                        Toast.makeText(this, "Camera permission required!", Toast.LENGTH_LONG).show();
                    }

                }
            }
            break;
            case  STORAGE_REQUEST_CODE: {
                if(grantResults.length>0){
                    boolean storageAccepetd = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepetd){
                        pickFromStorage();

                    }else {
                        Toast.makeText(this, "Storage permission required!", Toast.LENGTH_LONG).show();
                    }

                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

            }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    imageButton.setImageURI(resultUri);
                }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_LONG).show();

                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}