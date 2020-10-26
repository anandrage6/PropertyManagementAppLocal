package com.example.propertymanagementapplocal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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

    private Button updateSavebutton, updateCancelButton;

    private Uri imageUri;

    // permission constants
    private   static  final  int CAMERA_REQUEST_CODE = 100;
    private   static  final  int STORAGE_REQUEST_CODE = 101;

    //image pick constants
    private   static  final  int IMAGE_PICK_CAMERA_CODE = 102;
    private   static  final  int IMAGE_PICK_GALLERY_CODE = 103;

    //array of permissions
    private  String[] cameraPermissions;
    private  String[] storagePermissions;


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
        updateSavebutton = findViewById(R.id.updatePropertySaveBtn);
        updateCancelButton = findViewById(R.id.updateropertyCancelButton);
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
        imageUri = Uri.parse(mPropertyModelClass.getImage());
        imageButton.setImageURI(imageUri);

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



        updateSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyName = edtPropertyName.getText().toString();
                ownerName = edtOwnerName.getText().toString();
                address = edtAddress.getText().toString();
                city = edtCity.getText().toString();
                zipcode = edtZipCode.getText().toString();
                description = edtDescription.getText().toString();

                image = imageUri.toString();

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

        //cancel button

        updateCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(UpdateProperty.this);
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
                        Toast.makeText(UpdateProperty.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        cameraPermissions = new  String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //imageButton
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

    public void onBackPressed(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
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
                Toast.makeText(UpdateProperty.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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