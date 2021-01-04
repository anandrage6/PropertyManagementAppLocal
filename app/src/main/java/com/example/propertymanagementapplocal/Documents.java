package com.example.propertymanagementapplocal;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static android.app.Activity.RESULT_OK;


public class Documents extends Fragment {
    private long refFlatId;
    private long refTenantId;
    FloatingActionButton btnadd;
    final int REQUEST_EXTERNAL_STORAGE = 100;
    ImageView imageView;
    Uri imageUri;
    private  DatabaseQueryClass databaseQueryClass;    ;
    private TenantModelClass mtenantModelClass;
    String tenantName;
    private long tenantId;
    private static DocumentCreatedListner documentCreatedListner;


    public Documents() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);

        imageView = view.findViewById(R.id.Documentimg);
        databaseQueryClass = new DatabaseQueryClass(getContext());

        refFlatId = getArguments().getLong("1");
        Log.d("flatRefFId_in_Documents: ==> ", String.valueOf(refFlatId));

        tenantId = getArguments().getLong("2");
        Log.e("tenantId in Payments ===== > ", String.valueOf(tenantId));


        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId on resume in Payments ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Floatind Add Button
        btnadd = view.findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                    return;
                } else {
                    launchGalleryIntent();
                }

            }
        });
        return view;

    }

    public void launchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    launchGalleryIntent();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXTERNAL_STORAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null) {

            final List<Bitmap> bitmaps = new ArrayList<>();
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                //multiple images selecetd
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    imageUri = clipData.getItemAt(i).getUri();
                    Log.d("URI", imageUri.toString());
                    try {
                        InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //single image selected
                Uri imageUri = data.getData();
                Log.d("URI", imageUri.toString());
                try {
                    InputStream inputStream = getActivity().getApplicationContext().getContentResolver(). openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if(!bitmaps.isEmpty()){
                    DocumentModelClass documentModelClass = new DocumentModelClass(-1, bitmaps);
                    long id = databaseQueryClass.insertDocument(documentModelClass, tenantId );
                    if (id>0){
                        documentModelClass.setId(id);
                        documentCreatedListner.OnDocumentCreated(documentModelClass);

                    }

                }

            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (final Bitmap b : bitmaps) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(b);
                            }
                        });

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}