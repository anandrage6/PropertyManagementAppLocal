package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyDetails extends AppCompatActivity implements FlatsCreateListener {
    TextView locationTv, ownerNameTv, propertyNameTv;
    ImageView imagetv;
    private long propertyId;
    String strLocationTv, strCity, strState, strZipCode, strownerNameTv, stringimage, totalAddress, strPropertyName, strPropertyType, strDescription;
    private Toolbar toolbar;
    ImageButton editBtn;
    int itemPosition;


    //floating add button
    //flats
    ImageButton flatAddBtn;

    //private TextView flatEmptyListTV;
    private TextView textViewEmptyList;

    PropertyModelClass propertyModelClass;
    private long refpropertyId;

    //visibility
    // private TextView emptylist;

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);


    private List<FlatsModelClass> flatsList = new ArrayList<>();

    //tenantList
    //private List<TenantModelClass> tenantList = new ArrayList<>();

    private RecyclerView flatsrecyclerView;
    private FlatsListRecyclerAdapter flatsListRecyclerAdapter;

    //ads
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        // banner ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        locationTv = findViewById(R.id.locationTextViewDetails);
        ownerNameTv = findViewById(R.id.ownernameTextViewDetails);
        imagetv = findViewById(R.id.imageViewDetails);
        propertyNameTv = findViewById(R.id.propertyNameTv);

        flatsrecyclerView = findViewById(R.id.flatsRecyclerViewid);


        //tool bar
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");


        //visibility of emptylist
        // linearLayout = findViewById(R.id.flatsemptyListHide);


        editBtn = findViewById(R.id.propertyEditBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Edit", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(PropertyDetails.this, UpdateProperty2.class);
                intent.putExtra("PROPERTYID", propertyId);
                intent.putExtra("IMAGE", stringimage);
                intent.putExtra("PROPERTYTYPE", strPropertyType);
                intent.putExtra("PROPERTYNAME", strPropertyName);
                intent.putExtra("OWNERNAME", strownerNameTv);
                intent.putExtra("ADDRESS", strLocationTv);
                intent.putExtra("CITY", strCity);
                intent.putExtra("STATE", strState);
                intent.putExtra("ZIPCODE", strZipCode);
                intent.putExtra("DESCRIPTION", strDescription);
                intent.putExtra("itemPosition", itemPosition);
                startActivity(intent);


            }
        });

        //
        refpropertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        Log.d("PropertyDetRefId : ==> ", String.valueOf(refpropertyId));

        flatsList.addAll(databaseQueryClass.getAllFlatsByPFId(refpropertyId));
        Log.d("FlatList : ==> ", String.valueOf(flatsList.size()));


        //flats part
        flatsListRecyclerAdapter = new FlatsListRecyclerAdapter(this, flatsList);
        flatsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        flatsrecyclerView.setAdapter(flatsListRecyclerAdapter);

        //flatsListRecyclerAdapter.notifyItemChanged(flatsList);
        //flatsListRecyclerAdapter.notifyDataSetChanged();


        //flats button click
        flatAddBtn = findViewById(R.id.flatAddbtn);
        flatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddFlats();

            }
        });


        //navigation method
        //String intentValue = getIntent().getStringExtra("intentPassed");
        //Log.e("intentValue : ==> ", intentValue);

        /*
        if(intentValue== null){
        }else{
            FlatsCreateListener flatsCreateListener = this;
            long propertyId = refpropertyId;
            Log.d("propertyIdResult ===> ", String.valueOf(propertyId));
            AddFlats addFlats = AddFlats.newInstance(propertyId, flatsCreateListener);
            addFlats.show(getSupportFragmentManager(), Config.CREATE_FLAT);
        }

         */

        textViewEmptyList = findViewById(R.id.flatsemptyListHide);
        //textViewEmptyList.setVisibility(View.VISIBLE);
        viewVisibility();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //retrive full details part
        propertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        propertyModelClass = databaseQueryClass.getPropertyById(propertyId);
        strLocationTv = propertyModelClass.getAddress();
//        strLocationTv = getIntent().getStringExtra(Config.COLUMN_PROPERTY_ADDRESS);
        strCity = propertyModelClass.getCity();
//        strCity = getIntent().getStringExtra(Config.COLUMN_PROPERTY_CITY);
        strState = propertyModelClass.getState();
//        strState = getIntent().getStringExtra(Config.COLUMN_PROPERTY_STATE);
        strZipCode = propertyModelClass.getZipCode();
//        strZipCode = getIntent().getStringExtra(Config.COLUMN_PROPERTY_ZIPCODE);
        totalAddress = strLocationTv + ", " + strCity + " " + strState + " - " + strZipCode;
        strPropertyName = propertyModelClass.getPropertyName();
//        strPropertyName = getIntent().getStringExtra(Config.COLUMN_PROPERTY_PROPERTYNAME);
        strPropertyType = propertyModelClass.getPropertyType();
//        strPropertyType = getIntent().getStringExtra(Config.COLUMN_PROPERTY_PROPERTYTYPE);
        strDescription = propertyModelClass.getDescription();
//        strDescription = getIntent().getStringExtra(Config.COLUMN_PROPERTY_DESCRIPTION);
//        itemPosition = Integer.parseInt(getIntent().getStringExtra("itemPosition"));

        strownerNameTv = propertyModelClass.getOwnerName();
//        strownerNameTv = getIntent().getStringExtra(Config.COLUMN_PROPERTY_OWNERNAME);
        try {
            stringimage = propertyModelClass.getImage();
//            stringimage = getIntent().getStringExtra(Config.COLUMN_PROPERTY_IMAGE);
        } catch (Exception e) {

        }

        propertyModelClass = databaseQueryClass.getPropertyById(propertyId);
        propertyNameTv.setText(propertyModelClass.getPropertyName());
        locationTv.setText(totalAddress);
        ownerNameTv.setText(strownerNameTv);

        if (stringimage != null) {
            imagetv.setImageURI(Uri.parse(stringimage));
        }


        /*
        refpropertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        Log.d("PropertyDetRefId : ==> ", String.valueOf(refpropertyId));

        flatsList.addAll(databaseQueryClass.getAllFlatsByPFId(refpropertyId));
        Log.d("FlatList : ==> ", String.valueOf(flatsList.size()));

         */


        //flats part
        flatsListRecyclerAdapter = new FlatsListRecyclerAdapter(this, flatsList);
        flatsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        flatsrecyclerView.setAdapter(flatsListRecyclerAdapter);

        /*
        //display tenant name in flat card

        List<FlatsModelClass> flatsall = new ArrayList<>();
        for(FlatsModelClass f : flatsList){
             f.getFlatId();
            flatsall.addAll(Collections.singleton(f));
        }

        //TenantList
        tenantList.addAll(databaseQueryClass.getAllTenantsByFId(flatsall.size()));

        if(tenantList.size() > 0){

        }

         */

        //
        /*
        //flats button click
        flatAddBtn = findViewById(R.id.flatAddbtn);
        flatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddFlats();

            }
        });

         */

        //navigation method
        //String intentValue = getIntent().getStringExtra("intentPassed");
        //Log.e("intentValue : ==> ", intentValue);

        /*
        if(intentValue== null){
        }else{
            FlatsCreateListener flatsCreateListener = this;
            long propertyId = refpropertyId;
            Log.d("propertyIdResult ===> ", String.valueOf(propertyId));
            AddFlats addFlats = AddFlats.newInstance(propertyId, flatsCreateListener);
            addFlats.show(getSupportFragmentManager(), Config.CREATE_FLAT);
        }

         */

        //
        /*
        textViewEmptyList = findViewById(R.id.flatsemptyListHide);
        //textViewEmptyList.setVisibility(View.VISIBLE);
        viewVisibility();

        */
    }

    private void openActivityAddFlats() {
        AddFlats addFlats = AddFlats.newInstance(refpropertyId, this);
        addFlats.show(getSupportFragmentManager(), Config.CREATE_FLAT);
        //Intent i = new Intent(this,addFlats.getClass());
        //this.startActivity(i);

    }

    @Override
    public void onFlatsCreated(FlatsModelClass flat) {

        flatsList.add(flat);
        flatsListRecyclerAdapter.notifyDataSetChanged();

        //flats emptylist
        viewVisibility();


    }

    public void viewVisibility() {
        try {
            if (flatsList.isEmpty()) {
                textViewEmptyList.setVisibility(View.VISIBLE);
            } else {
                textViewEmptyList.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}