package com.example.propertymanagementapplocal;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import static android.app.Activity.RESULT_OK;


public class Documents extends Fragment {
    private long refFlatId;
    private long refTenantId;
    FloatingActionButton btnadd;
    Button nxtBtn, prvBtn;
    final int REQUEST_EXTERNAL_STORAGE = 100;
    ImageView imageView;
    private  DatabaseQueryClass databaseQueryClass;    ;
    private TenantModelClass mtenantModelClass;
    String tenantName;
    private long tenantId;
    private static DocumentCreatedListner documentCreatedListner;
    ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 0;
    int position = 0;



    public Documents() {
    }


    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);

        imageUris = new ArrayList<>();

        imageView = view.findViewById(R.id.imageSwitcher);
        btnadd = view.findViewById(R.id.addbtn);
        nxtBtn = view.findViewById(R.id.nxtBtn);
        prvBtn = view.findViewById(R.id.prvBtn);

        databaseQueryClass = new DatabaseQueryClass(getContext());



        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_Documents: ==> ", String.valueOf(refFlatId));

        tenantId = getArguments().getLong("2");
        Log.e("tenantId in Documents ===== > ", String.valueOf(tenantId));


        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId in Documents ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }


/*
        imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1 = new ImageView(getContext());
                return imageView1;
            }
        });

 */



        //imageView.setImageURI(imageUris.get(0));

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position > imageUris.size() -1){
                    position++;
                    imageView.setImageURI(imageUris.get(position));
                }else{
                    Toast.makeText(getContext(), "No More Images",Toast.LENGTH_LONG).show();
                }

            }
        });

        prvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position > 0){
                    position--;
                    imageView.setImageURI(imageUris.get(position));
                }else{
                    Toast.makeText(getContext(), "No previous images", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageIntent();
            }
        });
       
        return view;

    }

    private void pickImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images(s)"), PICK_IMAGES_CODE);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGES_CODE){
            if(resultCode == RESULT_OK){
                if(data.getClipData() != null){

                    //picked multiple images
                    int count = data.getClipData().getItemCount(); //number of picked images
                    for(int i=0; i<count; i++){
                        //get image uri at specific index
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        Log.e("multiple Uri =============> ", String.valueOf(imageUri));
                        imageUris.add(imageUri); // add to list
                    }
                    //set first image uri to image switcher
                    imageView.setImageURI(imageUris.get(0));
                    position = 0;
                    //Log.e("multiple Uri =============> ", String.valueOf(imageUris.get(0)));
                }else{
                    //picked single image

                    Uri imageUri = data.getData();

                   // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                       //String picturePath = FileUtils.getPath(getContext(), imageUri);
                      // Uri PicUri = Uri.parse(picturePath);
                       //imageView.setImageURI(imageUri);
                    imageView.setImageResource(R.drawable.header);
                       Log.e("PicUri===> ", String.valueOf(imageUri));
                    //}



                    //imageUris.add(imageUri);
                    //set image uri to image switcher
                   // imageView.setImageURI(Uri.parse(picturePath));
                    position = 0;
                    Log.e("Single Uri =============> ", String.valueOf(imageUri));


                }
            }
        }
    }
}