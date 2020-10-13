package com.example.propertymanagementapplocal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class AddProperty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //instance variables
    ImageButton imageBtn;
    EditText edtpropertyName, edtownerName, edtaddress, edtcity,  edtzipcode, edtdescription, image;
    Spinner statespinn, propertyTypeSpinn;
    Button btnsave, btnCancel;
    private Uri imageUri;
    //ProgressDialog mprogress;
    String state, propertyType;
    String imagetext;

    //Awesome Validation
    AwesomeValidation awesomeValidation;


    public  String propertyName, ownerName, address, city, zipcode, description;


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
        setContentView(R.layout.activity_add_property);

        //finding id's
        imageBtn = findViewById(R.id.imageButton);

        edtpropertyName = findViewById(R.id.propertyNameEditText);
        edtownerName = findViewById(R.id.ownerNameEditText);
        edtaddress = findViewById(R.id.addressEditText);
        edtcity = findViewById(R.id.cityEditText);

        edtzipcode = findViewById(R.id.zipCodeEditText);
        edtdescription = findViewById(R.id.descriptionEditText);
        btnsave = findViewById(R.id.saveBtn);
        btnCancel = findViewById(R.id.propertyCancelButton);

        //Awesome validations

        //Awesome
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(AddProperty.this, R.id.propertyNameEditText, "[a-zA-Z\\s]+", R.string.err_propertyName);
        awesomeValidation.addValidation(AddProperty.this, R.id.ownerNameEditText, "[a-zA-Z\\s]+", R.string.err_ownerName);
        awesomeValidation.addValidation(AddProperty.this, R.id.cityEditText, "[a-zA-Z\\s]+", R.string.err_city);
        awesomeValidation.addValidation(AddProperty.this, R.id.zipCodeEditText, "^[1-9][0-9]{5}$", R.string.err_zipCode);



        //spinner for States
        statespinn = findViewById(R.id.stateEditText);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this, R.array.States, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespinn.setAdapter(stateAdapter);
        statespinn.setOnItemSelectedListener(this);

        //spinner for Type
        propertyTypeSpinn = findViewById(R.id.propertyTypeSpinn);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.Property_Types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTypeSpinn.setAdapter(typeAdapter);
        propertyTypeSpinn.setOnItemSelectedListener(this);

        cameraPermissions = new  String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //image button
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        //Button save
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when click on save insert data into database
                getData();
            }
        });

        //Button cancel

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getData() {

        if(awesomeValidation.validate()) {
            propertyName = "" + edtpropertyName.getText().toString().trim();
            ownerName = "" + edtownerName.getText().toString().trim();
            address = "" + edtaddress.getText().toString().trim();
            city = "" + edtcity.getText().toString().trim();
            zipcode = "" + edtzipcode.getText().toString().trim();
            description = "" + edtdescription.getText().toString().trim();


            try {
                if (!imageUri.toString().isEmpty() || imageUri.toString().isEmpty()) {
                    imagetext = imageUri.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            PropertyModelClass model = new PropertyModelClass(-1, propertyType, propertyName, ownerName, address, city, state, zipcode, description, imagetext);
            DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);
            long id = databaseQueryClass.insertProperty(model);
            if (id > 0) {
                model.setId(id);
            }
            startActivity(new Intent(AddProperty.this, MainActivity.class));
            // Log.e("Record : ", String.valueOf(id));
        }

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
                    imageBtn.setImageURI(resultUri);
                }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_LONG).show();

                }



        }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == statespinn) {
            state = ""+adapterView.getItemAtPosition(i).toString();
        }else if(adapterView == propertyTypeSpinn) {
            //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();
            //Log.e("text",text);
            propertyType = ""+adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}